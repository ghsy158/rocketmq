package fgh.mq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

import fgh.listener.MQListener;

/**
 * 
 * <b>系统名称：</b><br>
 * <b>模块名称：</b><br>
 * <b>中文类名：</b><br>
 * <b>概要说明：</b><br>
 * 
 * @author fgh
 * @since 2016年7月8日下午11:08:53
 */
public class MQConsumeFactory {

	private static class SingletonHolder {
		static final MQConsumeFactory instance = new MQConsumeFactory();
	}

	public static MQConsumeFactory getInstance() {
		return SingletonHolder.instance;
	}

	private static Map<String, MQConsumer> consumers = new ConcurrentHashMap<String, MQConsumer>();

	public MQConsumer createConsumer(String consumerId, String groupName, String namesrvAddr, String topic, String tag,
			MQListener mqListener, Map<String, String> options) {
		if (consumers.get(consumerId) != null) {
			return consumers.get(consumerId);
		}

		try {
			// 设置Consumer实例，订阅 注册监听 配置参数 最后 装入集合
			MQConsumer consumer = new MQConsumer(consumerId, groupName, namesrvAddr);
			consumer.subscribe(topic, tag);
			consumer.registerMessageListener(mqListener);
			// 设置消费者其他参数
			// Consumer启动后，默认从什么位置开始消费，默认CONSUME_FROM_LAST_OFFEST
			String consumeFromWhere = options.get("consumeFromWhere");
			/** 消费线程池最小数量 默认10 **/
			String consumeThreadMin = options.get("consumeThreadMin");
			/** 消费线程池最大数量 默认20 **/
			String consumeTheadMax = options.get("consumeTheadMax");
			/** 拉消息本地队列缓存消息最大数 默认1000 **/
			String pullThresholdForQueue = options.get("pullThresholdForQueue");
			/** 批量消费 一次消费多少条消息 默认1条 **/
			String consumeMessageBatchMaxSize = options.get("consumeMessageBatchMaxSize");
			/** 批量拉消息 一次最多拉多少条 默认32条 **/
			String pullBatchSize = options.get("pullBatchSize");
			/** 消息拉取线程 每隔多久拉取一次 默认0 **/
			String pullInterval = options.get("pullInterval");

			if (StringUtils.isNotBlank(consumeFromWhere)) {
				if (StringUtils.equals(consumeFromWhere, "CONSUME_FROM_LAST_OFFEST")) {
					consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
				} else if (StringUtils.equals(consumeFromWhere, "CONSUME_FROM_FIRST_OFFEST")) {
					consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
				}
			}

			if (StringUtils.isNotBlank(consumeThreadMin)) {
				consumer.setConsumeThreadMin(Integer.parseInt(consumeThreadMin));
			}
			if (StringUtils.isNotBlank(consumeTheadMax)) {
				consumer.setConsumeThreadMin(Integer.parseInt(consumeTheadMax));
			}
			if (StringUtils.isNotBlank(pullThresholdForQueue)) {
				consumer.setPullThresholdForQueue(Integer.parseInt(pullThresholdForQueue));
			}
			if (StringUtils.isNotBlank(consumeMessageBatchMaxSize)) {
				consumer.setConsumeMessageBatchMaxSize(Integer.parseInt(consumeMessageBatchMaxSize));
			}
			if (StringUtils.isNotBlank(pullBatchSize)) {
				consumer.setPullBatchSize(Integer.parseInt(pullBatchSize));
			}
			if (StringUtils.isNotBlank(pullInterval)) {
				consumer.setPullInterval(Integer.parseInt(pullInterval));
			}
			consumers.put(consumerId, consumer);
			return consumer;
		} catch (MQClientException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	public void stopCosumer(String consumerId) {
		if (consumers.get(consumerId) != null) {
			consumers.get(consumerId).shutdown();
			consumers.remove(consumerId);
		}
	}

	public void stopConsumers() {
		for (String consumerId : consumers.keySet()) {
			consumers.get(consumerId).shutdown();
		}
		consumers.clear();
	}

}
