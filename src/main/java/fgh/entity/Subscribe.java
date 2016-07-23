package fgh.entity;

import java.util.Date;

public class Subscribe {

	/** 每一个消费类型的唯一ID **/
	private Integer id;

	/** 当前所在系统(子项目) **/
	private String proName;

	/** 子系统的数据接口 **/
	private String url;

	/** 当前系统订阅的主题 **/
	private String topic;

	/** 子主题 **/
	private String tag;

	/** 所属组 **/
	private String gro;

	/** 开始 消费端的配置属性 根据消费端服务器的能力进行配置 **/
	private String consumeFromWhere;

	private String consumeThreadMin;

	private String consumeTheadMax;

	private String pullThresholdForQueue;

	private String consumeMessageBatchMaxSize;

	private String pullBatchSize;

	private String pullInterval;

	/** 结束 **/

	private String businessKey;

	private String status;

	private String createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getGro() {
		return gro;
	}

	public void setGro(String gro) {
		this.gro = gro;
	}

	public String getConsumeFromWhere() {
		return consumeFromWhere;
	}

	public void setConsumeFromWhere(String consumeFromWhere) {
		this.consumeFromWhere = consumeFromWhere;
	}

	public String getConsumeThreadMin() {
		return consumeThreadMin;
	}

	public void setConsumeThreadMin(String consumeThreadMin) {
		this.consumeThreadMin = consumeThreadMin;
	}

	public String getConsumeTheadMax() {
		return consumeTheadMax;
	}

	public void setConsumeTheadMax(String consumeTheadMax) {
		this.consumeTheadMax = consumeTheadMax;
	}

	public String getPullThresholdForQueue() {
		return pullThresholdForQueue;
	}

	public void setPullThresholdForQueue(String pullThresholdForQueue) {
		this.pullThresholdForQueue = pullThresholdForQueue;
	}

	public String getConsumeMessageBatchMaxSize() {
		return consumeMessageBatchMaxSize;
	}

	public void setConsumeMessageBatchMaxSize(String consumeMessageBatchMaxSize) {
		this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
	}

	public String getPullBatchSize() {
		return pullBatchSize;
	}

	public void setPullBatchSize(String pullBatchSize) {
		this.pullBatchSize = pullBatchSize;
	}

	public String getPullInterval() {
		return pullInterval;
	}

	public void setPullInterval(String pullInterval) {
		this.pullInterval = pullInterval;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
