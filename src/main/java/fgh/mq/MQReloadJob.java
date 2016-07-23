package fgh.mq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.exception.MQClientException;

import fgh.entity.Subscribe;
import fgh.listener.MQListener;

@Component("mqReloadJob")
public class MQReloadJob {

	Logger logger = Logger.getLogger(MQReloadJob.class);
	
	//key consumerId value:updateTime
	private static Map<String,String> updateMQSubscribeMap = new ConcurrentHashMap<String,String>();
	
	private SubscribeService subscribeService;

	public SubscribeService getSubscribeService() {
		return subscribeService;
	}

	@Autowired
	public void setSubscribeService(SubscribeService subscribeService) {
		this.subscribeService = subscribeService;
	}
	
	@Scheduled(initialDelay=5000,fixedDelay=3000)
	public void reloadJob(){
		logger.info("系统开始轮询数据库...");
		List<MQSubscribe> list = this.subscribeService.findAllSubscribe();
		
		for(Subscribe subscribe:list){
			int id = subscribe.getId();
			String proName = subscribe.getProName();
			//consumerId 自定义规则
			String consumerId = proName +"_"+id;
			String url = subscribe.getUrl();
			String topic = subscribe.getTopic();
			String tag = subscribe.getTag();
			String groupName =subscribe.getGro();
			String businessKey = subscribe.getBusinessKey();
			String status = subscribe.getStatus();
			String updateTime = DateFormatUtils.format(subscribe.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
			
			//配置参数
			Map<String, String> options = new HashMap<String,String>();
			String consumeFromWhere = subscribe.getConsumeFromWhere();
			options.put("consumeFromWhere", consumeFromWhere);
			String consumeThreadMin = subscribe.getConsumeThreadMin();
			options.put("cosumeTreadMin", consumeThreadMin);
			String consumeTheadMax = subscribe.getConsumeTheadMax();
			options.put("consumeTheadMax", consumeTheadMax);
			String pullThresholdForQueue = subscribe.getPullThresholdForQueue();
			options.put("pullThresoldForQueue", pullThresholdForQueue);
			String consumeMessageBatchMaxSize = subscribe.getConsumeMessageBatchMaxSize();
			options.put("consumeMessageBatchMaxSize", consumeMessageBatchMaxSize);
			String pullBatchSize = subscribe.getPullBatchSize();
			options.put("pullBatchSize", pullBatchSize);
			String pullInterval = subscribe.getPullInterval();
			options.put("pullInterval", pullInterval);

			String mqUpdateTime = updateMQSubscribeMap.get(consumerId);
			//如果对比 结果为Nulll 或者 两者不相等
			if(mqUpdateTime==null ||!mqUpdateTime.equals(updateTime)){
				//如果两者不相等 并且状态为status=0,说明consumer已经停用
				if(!updateTime.equals(mqUpdateTime) && "0".equals(status)){
					//停止服务
					MQConsumeFactory.getInstance().stopCosumer(consumerId);
				}else{
					//停止服务
					MQConsumeFactory.getInstance().stopCosumer(consumerId);
					
					//创建Consuemr
					MQListener mqListener = new MQListener(id, proName, url, businessKey);
					MQConsumer  mqConsumer = MQConsumeFactory.getInstance().createConsumer(consumerId, groupName,
							"192.168.1.201:9876", topic, tag, mqListener, options);
					
					try {
						mqConsumer.start();
					} catch (MQClientException e) {
						logger.error("启动consumer失败,consumerId["+mqConsumer.getConsumerId()+"]",e);
					}
					
					//存储已经存在的订阅关系
					updateMQSubscribeMap.put(consumerId, updateTime);
					logger.info("已经注册了服务: "+topic+",updateTime:"+updateTime);
				}
			}
			
		}
	}
	
}
