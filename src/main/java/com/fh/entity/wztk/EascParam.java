package com.fh.entity.wztk;

import java.io.Serializable;

public class EascParam implements Serializable{
	
	public EascParam() {
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5845832282752160496L;
	public String appId;
	public String domain;
	public String port;
	public String userName;
	public String pwd;
	public String tenantId;
	public String userId;
	public String token;
	
	public EascParam(String appId, String domain, String port, String userName, String pwd, String tenantId) {
		super();
		this.appId = appId;
		this.domain = domain;
		this.port = port;
		this.userName = userName;
		this.pwd = pwd;
		this.tenantId = tenantId;
	}
	
	
	public EascParam(String appId, String domain, String port, String userName, String pwd, String tenantId,
			String userId) {
		super();
		this.appId = appId;
		this.domain = domain;
		this.port = port;
		this.userName = userName;
		this.pwd = pwd;
		this.tenantId = tenantId;
		this.userId = userId;
	}


	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	

	
}
