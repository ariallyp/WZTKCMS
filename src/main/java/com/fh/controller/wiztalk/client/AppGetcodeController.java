package com.fh.controller.wiztalk.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.wztk.EascParam;
import com.fh.entity.wztk.SysConfig;
import com.fh.entity.wztk.UserEntity;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.orguser.OrgUserService;
import com.fh.service.wiztalk.wizusers.WizUsersService;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.R;
import com.fh.util.Tools;

import top.wiz.common.easc.EascAuthHelper;
import top.wiz.common.easc.model.dto.Result;

/**
 * 会员-接口类
 * 
 * 相关参数协议： 00 请求失败 01 请求成功 02 返回空值 03 请求协议参数不完整 04 用户名或密码错误 05 FKEY验证失败
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/appcode")
public class AppGetcodeController extends BaseController {

	@Value("${server.code}")
	private String serverCode;

	@Resource(name = "redisUtilService")
	private IRedisUtilService redisUtilService;
	@Resource(name = "wizusersService")
	private WizUsersService wizusersService;
	@Resource(name = "orguserService")
	private OrgUserService orguserService;

	@RequestMapping(value = "/getCode")
	@ResponseBody
	public ModelAndView getCode() {
		logBefore(logger, "根据用户名获取授权码");
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("wiztalk/client/code");
		String code = "";
		String name = pd.getString("name");

		if (!StringUtils.isEmpty(name)) {
			code = this.EncoderData(serverCode);
			mv.addObject("code", code.trim());
			return mv;
		} else {
			return mv;
		}

	}

	@RequestMapping(value = "/loginEasc/{userId}", method = { RequestMethod.GET })
	@ResponseBody
	public Object loginEasc(@PathVariable("userId") String userId, HttpServletRequest request) {

		Result result = new Result();
		Boolean conn = redisUtilService.exists("WZTK_easc_eascParam_map");
		if (conn == null || conn == false) {
			result.setRetcode(1000);
			result.setRetmsg("redis 获取easc 参数为空 ");
			return result;
		}
		List<String> rsmap = redisUtilService.hmget("WZTK_easc_eascParam_map", "appId", "domain", "port", "userName",
				"pwd", "tenantId", "userId");
		EascParam eascParam = new EascParam(rsmap.get(0), rsmap.get(1), rsmap.get(2), rsmap.get(3), rsmap.get(4),
				rsmap.get(5), rsmap.get(6));
		String domain = eascParam.getDomain();
		String appId = eascParam.getAppId();
		String port = eascParam.getPort();
		result = EascAuthHelper.ssoLoginByUserId(domain, port, userId, appId);

		return result;

	}

	/**
	 * 保存
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveUser", method = { RequestMethod.POST })
	@ResponseBody
	public R SaveUser(@RequestBody UserEntity userEntity) {
		PageData pd = new PageData();
		pd = this.getPageData();
		String NICKNAME = userEntity.getNickname();
		String tenantId =getValueFromParamJson("WZTK_param_current_tenantId");
		String orgId=getValueFromParamJson("WZTK_param_current_orgId");
		if (StringUtils.isEmpty(tenantId)) {
			return R.error(3000, "后台服务没有设置 tenantId ，请登录wiztalk后台设置 ！");
		}	
		
		if (StringUtils.isEmpty(orgId)) {
			return R.error(3000, "后台服务没有设置 orgId ，请登录wiztalk后台设置 ！");
		}
		if (StringUtils.isEmpty(userEntity.getName())) {
			return R.error(3000, "用户名不能为空！");
		}
		if (StringUtils.isEmpty(userEntity.getNickname())) {
			return R.error(3000, "Nickname不能为空！");
		}
		
		if (StringUtils.isEmpty(userEntity.getPassword())) {
			return R.error(3000, "密码不能为空！");
		}
		Map  map = new HashMap<>();
		if (Tools.notEmpty(NICKNAME)) {
			pd.put("NAME_QUANPIN",userEntity.getName());

		}
		pd.put("NAME", userEntity.getName());
		pd.put("NAME_PY", userEntity.getName());
		pd.put("CREATED", Tools.date2Str(new Date())); // created
		pd.put("UPDATED", Tools.date2Str(new Date())); // updated//主键
		pd.put("PASSWORD", MD5.md5(userEntity.getPassword()));
		pd.put("STATUS", "0");
		pd.put("RAND", "0");
		pd.put("LEVEL", "0");
		pd.put("SORT", "0");
		pd.put("NICKNAME", userEntity.getNickname());
		pd.put("AVATAR", userEntity.getAvatar());
		pd.put("EMAIL", userEntity.getEmail());
		pd.put("MOBILE", userEntity.getMobile());
		pd.put("TENANT_ID", tenantId);
		pd.put("TEL", "");
		pd.put("AREA", "");

		pd.put("ORG_ID", orgId);// 机构
	
		try {
			if (wizusersService.findByUName(pd).size() > 0) {
				return R.error(3000, "用户名已经被占用，请选择其它用户名！");
			} else {
				pd.put("WIZUSERS_ID", this.get32UUID());
				wizusersService.save(pd);				
				// save org_user
				String USER_ID = pd.getString("WIZUSERS_ID");
				userEntity.setId(USER_ID);
				
				userEntity.setTenantId(tenantId);
				userEntity.setStatus(0);
				map.put("user", userEntity);
				pd.put("USER_ID", USER_ID);
				pd.put("ID", this.get32UUID());
				orguserService.save(pd);
			}
		} catch (Exception e) {
			return R.error(2000, e.getMessage());
		}

		return R.ok(map);
	}
	
	
	/**
	 * 保存
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/editUser", method = { RequestMethod.POST })
	@ResponseBody
	public R editUser(@RequestBody UserEntity userEntity) {
		PageData pd = new PageData();
		
		
		String tenantId =getValueFromParamJson("WZTK_param_current_tenantId");
		String orgId=getValueFromParamJson("WZTK_param_current_orgId");
		if (StringUtils.isEmpty(tenantId)) {
			return R.error(3000, "后台服务没有设置 tenantId ，请登录wiztalk后台设置 ！");
		}	
		
		if (StringUtils.isEmpty(orgId)) {
			return R.error(3000, "后台服务没有设置 orgId ，请登录wiztalk后台设置 ！");
		}
			
		if (StringUtils.isEmpty(userEntity.getId())) {
			return R.error(3000, "返回的user Id 不能为空！");
		}
		if (StringUtils.isEmpty(userEntity.getName())) {
			return R.error(3000, "用户名不能为空！");
		}
		if (StringUtils.isEmpty(userEntity.getNickname())) {
			return R.error(3000, "Nickname不能为空！");
		}
		
		if (StringUtils.isEmpty(userEntity.getPassword())) {
			return R.error(3000, "密码不能为空！");
		}
		
		pd.put("NAME", userEntity.getName());
		pd.put("NAME_PY", userEntity.getName());
		pd.put("CREATED", Tools.date2Str(new Date())); // created
		pd.put("UPDATED", Tools.date2Str(new Date())); // updated//主键
		pd.put("PASSWORD", MD5.md5(userEntity.getPassword()));
		pd.put("STATUS", "0");
		pd.put("RAND", "0");
		pd.put("LEVEL", "0");
		pd.put("SORT", "0");
		pd.put("NICKNAME", userEntity.getNickname());
		pd.put("AVATAR", userEntity.getAvatar());
		pd.put("EMAIL", userEntity.getEmail());
		pd.put("MOBILE", userEntity.getMobile());
		pd.put("TENANT_ID", tenantId);
		pd.put("TEL", "");
		pd.put("AREA", "");
		pd.put("WIZUSERS_ID", userEntity.getId());
		pd.put("ORG_ID", orgId);// 机构		
		Map  map = new HashMap<>();
		
		try {
			if (wizusersService.findById(pd).isEmpty()) {
				return R.error(3000, "没有找到该用户，请确保你的id是否正确！");
			}else if (wizusersService.findByUNameID(pd).size()>1) {
				return R.error(3000, "用户名已经被占用，请选择其它用户名！");
			} else {
				wizusersService.edit(pd);
				userEntity.setTenantId(tenantId);
				userEntity.setStatus(0);
				map.put("user", userEntity);
			}
		} catch (Exception e) {
			return R.error(2000, e.getMessage());
		}

		return R.ok(map);
	}
	
	
	@RequestMapping(value = "/deleteUser/{userId}", method = { RequestMethod.GET })
	@ResponseBody
    public R delete(@PathVariable("userId") String userId) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WIZUSERS_ID", userId);
		pd=wizusersService.findById(pd);
		if (!StringUtils.isEmpty(pd)) {
			wizusersService.delete(pd);
			String USER_ID = pd.getString("WIZUSERS_ID");
			pd.put("USER_ID", USER_ID);
			orguserService.delete(pd);
		}else {
			return R.error(3000, "该用户不存在！");
		}
		
		
		
        return R.ok();
    }
	
	@RequestMapping(value = "/getUserByID/{userId}", method = { RequestMethod.GET })
	@ResponseBody
    public R getUserByID(@PathVariable("userId") String userId) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WIZUSERS_ID", userId);
		pd=wizusersService.findById(pd);
		if (!StringUtils.isEmpty(pd)) {
			UserEntity userEntity = new UserEntity<>();	
			userEntity.setName(pd.getString("NAME"));
			userEntity.setNickname( pd.getString("NICKNAME"));
			userEntity.setId(pd.getString("WIZUSERS_ID"));
			userEntity.setMobile(pd.getString("MOBILE"));
			userEntity.setAvatar("");
			userEntity.setEmail(pd.getString("EMAIL"));
			userEntity.setStatus(0);
			userEntity.setTenantId( pd.getString("TENANT_ID"));
			Map  map = new HashMap<>();
			map.put("user", userEntity);
			 return R.ok(map);
			
		}else {
			return R.error(3000, "该用户不存在！");
		}
       
    }
	

	private static String EncoderData(String code) {
		String v = code;
		Random r = new Random(System.currentTimeMillis());
		int index = r.nextInt(2);
		String[] dataList = new String[3];
		for (int i = 0; i < 2; i++) {
			if (index == i) {
				dataList[i] = v;

			} else {
				dataList[i] = r.nextInt(90) + 10 + "";

			}
		}
		dataList[2] = "0" + index;
		String value = "";
		for (int i = 0; i < dataList.length; i++) {
			value += dataList[i];
		}
		return value;
	}

	
	
	public String getValueFromParamJson (String key){
		
		Boolean conn = redisUtilService.exists("wiztalk_sys_config");
		if (conn == null || conn == false) {
			
			return "";
		}
		List<SysConfig> syslist = new ArrayList<>();
		String sysValue = redisUtilService.get("wiztalk_sys_config"); 
		syslist = JSONObject.parseArray(sysValue, SysConfig.class);
		String value ="";
		for (SysConfig sysConfig2 : syslist) {
			String field2=sysConfig2.getField();
			if (field2.equals(key)) {
				value=sysConfig2.getValue();
				break;
			}
		}		
		return value;
	}
	
}
