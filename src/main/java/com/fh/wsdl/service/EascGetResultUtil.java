package com.fh.wsdl.service;

import top.wiz.common.easc.EascAppHelper;
import top.wiz.common.easc.EascAuthHelper;
import top.wiz.common.easc.model.EascOrg;
import top.wiz.common.easc.model.EascOrgUser;
import top.wiz.common.easc.model.EascUser;
import top.wiz.common.easc.model.dto.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fh.entity.wztk.EascParam;
import com.fh.service.redis.IRedisUtilService;
import com.fh.util.Logger;
import com.fh.util.MD5;
import com.fh.util.SerializeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class EascGetResultUtil {

	private static Logger LOG = Logger.getLogger(EascGetResultUtil.class);


	public static Result getOrgListFromEasc(EascParam eascParam) {
		Result result = EascAppHelper.getAllOrg(eascParam.domain, eascParam.port, eascParam.appId,
				eascParam.getUserId());
		
		
		return result;

	}

	public static Result getUserListFromEasc(EascParam eascParam) {

		Result result = EascAppHelper.getAppUser(eascParam.domain, eascParam.port, eascParam.appId,
				eascParam.getUserId());
		
		return result;

	}

	public static Result getOrgUserListFromEasc(EascParam eascParam) {
		Result result = EascAppHelper.getAllOrgUser(eascParam.domain, eascParam.port, eascParam.appId,
				eascParam.getUserId());
		
		return result;

	}

	public static void main(String[] args) {
		String domain = "http://easc.wizplant.online";
		String appId = "98988264-5ce6-4723-bcb7-71a56b825687";
		// String token=EascGetResultUtil.getUid(domain, "2002", appId);
		EascParam eascParam = new EascParam();
		eascParam.setAppId(appId);
		eascParam.setDomain(domain);
		eascParam.setPort("2002");
		eascParam.setUserName("MOY4915");
		eascParam.setPwd("25F9E794323B453885F5181F1B624D0B");
		eascParam.setUserId("bc4ba096-ac22-4f68-b43d-9499679ede7a");
	/*	Result result = EascAuthHelper.login(domain, eascParam.port, eascParam.userName, eascParam.pwd, appId);
		Object data = result.getData();
			JSONObject jsonObject = JSONObject.fromObject(data);
		String 	userId = jsonObject.getString("userId");
			eascParam.setUserId(userId);*/
		
		//List<EascOrg> list =EascGetResultUtil.getOrgListFromEasc(eascParam);
		//Result result = EascAuthHelper.login(domain, eascParam.port, eascParam.userName, eascParam.pwd, appId);
		
		Result result =EascAuthHelper.ssoLoginByUserId(domain, eascParam.port, "1480483a-33e0-4f2a-8d30-23cc9a103ace", appId);

		System.err.println(result);
		// "2002", appId, token);

	}
}
