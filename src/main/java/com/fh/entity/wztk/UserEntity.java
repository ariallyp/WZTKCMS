package com.fh.entity.wztk;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;


public class UserEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public UserEntity() {
		
	}
	
	public UserEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * id
	 */
	
	
	private String id;
	
	/**
	 * 用户名
	 */
				
	private String name;
	
	/**
	 * 昵称
	 */
				
	private String nickname;
	
	/**
	 * 图标
	 */
						
	private String avatar;
	
	/**
	 * 状态
	 */
						
	private Integer status;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 租户
	 */
						
	private String tenantId;
	
	/**
	 * 邮件
	 */
				
	
	private String email;
	
	/**
	 * 电话
	 */
				
	
	private String mobile;
	
				
	
	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：用户名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：用户名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：图标
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * 获取：图标
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：租户
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮件
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：电话
	 */
	public String getMobile() {
		return mobile;
	}

}