package com.fh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
/**
 * 
* 类名称：MyInterceptor.java
* 类描述： 
* @author Arial
* 作者单位： 
* 联系方式：
* 创建时间：2018年8月1日
* @version 1.6
 */
public class MyInterceptor extends HandlerInterceptorAdapter{
	protected Logger logger = Logger.getLogger(this.getClass());
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
			return true;
	}
	
}
