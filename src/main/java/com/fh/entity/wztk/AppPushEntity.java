package com.fh.entity.wztk;

import java.util.List;

public class AppPushEntity {
	public BaseRequestEntity baseRequest;
	public String content;
	public Long expire;
	public int msgType;
	public ObjectContentEntity objectContent;
	public  List<String> sessions ;
	public String statusId;
	
	List<String> toUserNames;
	
	


	public List<String> getToUserNames() {
		return toUserNames;
	}

	public void setToUserNames(List<String> toUserNames) {
		this.toUserNames = toUserNames;
	}

	public BaseRequestEntity getBaseRequest() {
		return baseRequest;
	}

	public void setBaseRequest(BaseRequestEntity baseRequest) {
		this.baseRequest = baseRequest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}






	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}



	public ObjectContentEntity getObjectContent() {
		return objectContent;
	}

	public void setObjectContent(ObjectContentEntity objectContent) {
		this.objectContent = objectContent;
	}

	public List<String> getSessions() {
		return sessions;
	}

	public void setSessions(List<String> sessions) {
		this.sessions = sessions;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	
	
	
}
