package fgh.listener;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * 
 * <b>系统名称：</b>自定义监听器<br>
 * <b>模块名称：</b><br>
 * <b>中文类名：</b><br>
 * <b>概要说明：</b><br>
 * 
 * @author fgh
 * @since 2016年7月9日上午9:27:49
 */
public class MQListener implements MessageListenerConcurrently {

	Logger logger = Logger.getLogger(MQListener.class);
	
	// 传入业务参数
	private int id;
	private String proName;
	private String url;
	private String businessKey;

	public MQListener(int id, String proName, String url, String businessKey) {
		super();
		this.id = id;
		this.proName = proName;
		this.url = url;
		this.businessKey = businessKey;
	}


	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

		try {
			for (MessageExt msg : msgs) {
				String topic = msg.getTopic();
				String msgBody = new String(msg.getBody(), "utf-8");
				String tags = msg.getTags();
				logger.info("收到消息： topic[" +topic+"],tags["+tags+"],msg["+msgBody+"]" );
//				String originMsgId = msg.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
				//1 使用httpclient 发送请求到指定的系统中(url)
				
				//if response return ConsumeConcurrentlyStatus.RECONSUME_LATER
				
				//响应成功 记录操作日志 失败 记录日志 
				//....日志
				
			}

		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getProName() {
		return proName;
	}


	public void setProName(String proName) {
		this.proName = proName;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getBusinessKey() {
		return businessKey;
	}


	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	
}
