package fgh.mq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;

import fgh.listener.MQListener;

/**
 * 
 * <b>系统名称：</b>自己封装的MQConsumer<br>
 * <b>模块名称：</b><br>
 * <b>中文类名：</b><br>
 * <b>概要说明：</b><br>
 * @author fgh
 * @since 2016年7月9日上午10:51:59
 */
public class MQConsumer extends DefaultMQPushConsumer{

	private String consumerId;

	public MQConsumer(String consumerId, String groupName, String namesrvAddr) {
		super(groupName);
		this.setNamesrvAddr(namesrvAddr);
		this.consumerId = consumerId;
	}

	public void registerMessageListener(List<MQListener> mqListeners){
		for(MQListener listener:mqListeners){
//			this.registerMessageListener(mqListeners);
			this.registerMessageListener(listener);
		}
	}
	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	
	
	
}
