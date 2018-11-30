package com.fh.entity.wztk;

import java.util.List;

public class ObjectContentEntity {

	public BodyEntity bodyEntity;
	public HeadEntity head;
	

	public String appId;
	
	public List<BodyEntity> body;
	
	
	public BodyEntity getBodyEntity() {
		return bodyEntity;
	}
	public void setBodyEntity(BodyEntity bodyEntity) {
		this.bodyEntity = bodyEntity;
	}


	public HeadEntity getHead() {
		return head;
	}
	public void setHead(HeadEntity head) {
		this.head = head;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public List<BodyEntity> getBody() {
		return body;
	}
	public void setBody(List<BodyEntity> body) {
		this.body = body;
	}


	
	
	


}
