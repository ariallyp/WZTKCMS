package com.fh.wsdl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fh.entity.wztk.EascParam;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.organization.OrganizationService;
import com.fh.service.wiztalk.orguser.OrgUserService;
import com.fh.service.wiztalk.synclog.SyncLogService;
import com.fh.service.wiztalk.tenant.TenantService;
import com.fh.service.wiztalk.wizusers.WizUsersService;
import com.fh.util.Logger;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.fh.wsdl.entity.SeUser;
import com.fh.wsdl.util.WCFUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import top.wiz.common.easc.EascAuthHelper;
import top.wiz.common.easc.model.EascOrg;
import top.wiz.common.easc.model.EascOrgUser;
import top.wiz.common.easc.model.EascUser;
import top.wiz.common.easc.model.dto.Result;

@Service("syncDbService")
public class SyncDbService {

	protected Logger LOG = Logger.getLogger(this.getClass());

	@Autowired
	private OrgUserService orgUserService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private WizUsersService usersService;
	@Autowired
	private IRedisUtilService redisUtilService;
	@Autowired
	private SyncLogService synclogService;
	@Autowired
	private WizUsersService wizusersService;
	@Autowired
	private TenantService tenantService;
	private String errorMessage = "";

	public void syncdb() throws Exception {
	}

	public String syncdbResult(String type) throws Exception {
		LOG.info("同步用户数据开始：");
		long beginSynOrgMillis = System.currentTimeMillis();
		PageData pd = new PageData();
		pd.put("SYNCLOG_ID", UuidUtil.get32UUID()); // 主键
		StringBuffer sb = new StringBuffer();
		EascParam eascParam =null;
		if (redisUtilService.exists("WZTK_easc_eascParam_map")) {
			List<String> rsmap =redisUtilService.hmget("WZTK_easc_eascParam_map","appId","domain","port","userName","pwd","tenantId","userId");
			eascParam=new EascParam(rsmap.get(0), rsmap.get(1), rsmap.get(2), rsmap.get(3), rsmap.get(4), rsmap.get(5),rsmap.get(6));
			}
		String SYNC_WZTK_PWD = redisUtilService.get("SYNC_WZTK_PWD");

		pd.put("APPID", eascParam.getAppId());
		pd.put("WSDLSITE", eascParam.getDomain());
		pd.put("TYPE", type);
		
		sb.append("同步成功 :" + "\n");
		if ("YES".equals(SYNC_WZTK_PWD)) {
			long begingMillis = System.currentTimeMillis();
			LOG.info("开始同步Org表的毫秒数为：" + beginSynOrgMillis);
			//errorMessage = this.syncUserPwd();
			long endMillis = System.currentTimeMillis();
			sb.append("同步 用户密码 一共用时：" + (endMillis - begingMillis) + "毫秒");
		} else {

			errorMessage = this.synEascData();

			long endSynOrgMillis = System.currentTimeMillis();
			LOG.info("Easc数据表同步完成时的毫秒数为：" + endSynOrgMillis);
			LOG.info("同步Easc数据表一共用时：" + (endSynOrgMillis - beginSynOrgMillis) + "毫秒");
			sb.append("同步Easc数据表一共用时：" + (endSynOrgMillis - beginSynOrgMillis) + "毫秒" + "\n");
			

		}
		pd.put("CREATED", Tools.date2Str(new Date()));
		if (!errorMessage.isEmpty()) {
			pd.put("RESULT", errorMessage);
			synclogService.save(pd);
			return "同步失败： " + errorMessage;

		} else {
			pd.put("RESULT", sb.toString());
			synclogService.save(pd);
			return sb.toString();
		}

	}

	private String synEascData() throws Exception {
		int i=0;
		EascParam eascParam = this.getEascParam();
		 errorMessage=this.syncOrg(eascParam);
		 if (errorMessage.isEmpty()) {
			 errorMessage =errorMessage+this.syncUser(eascParam);
			 if (errorMessage.isEmpty()) {
				 errorMessage = errorMessage + this.syncUserOrg(eascParam);
			}
		}
		 if (!errorMessage.isEmpty()) {
			 i++;
					List<String> rsmap =redisUtilService.hmget("WZTK_easc_eascParam_map","appId","domain","port","userName","pwd","tenantId","userId");
					eascParam=new EascParam(rsmap.get(0), rsmap.get(1), rsmap.get(2), rsmap.get(3), rsmap.get(4), rsmap.get(5),rsmap.get(6));
					Map<String, String> map = new HashMap<String, String>();
					 map.put("appId", eascParam.getAppId());
					 map.put("domain", eascParam.getDomain());
					 map.put("port", eascParam.getPort());
					 map.put("userName", eascParam.getUserName());
					 map.put("pwd", eascParam.getPwd());
					 map.put("tenantId", rsmap.get(5));
					 map.put("userId", "");		
					 redisUtilService.hmset("WZTK_easc_eascParam_map", map);
					 Thread.sleep(3000);
					 if (i<3) {
						 this.synEascData() ;//重新回调同步方法
					}else {
						return errorMessage+"  "+i;
					}
					
					
		 }
				
		
		return errorMessage;

	}

	private String syncOrg(EascParam eascParam)  {
		try {
		
			Result result= EascGetResultUtil.getOrgListFromEasc(eascParam);
			Object data = result.getData();
			Integer retcode =result.getRetcode();
			
			
			if (retcode!=0) {
				LOG.info("获取easc org 信息："+ result.getRetmsg());
				return result.getRetmsg();
			}
			List<EascOrg> eascOrgs = new ArrayList<>();
			if (retcode==0) {
				JSONArray array = JSONArray.fromObject(data);
				eascOrgs = JSONArray.toList(array, new EascOrg(), new JsonConfig());		
			}
			String tenantId =eascParam.getTenantId();
			PageData pdTid = new PageData();
			pdTid.put("TENANT_ID", tenantId);	//主键
			
			PageData pdNew =tenantService.findById(pdTid);
			if (pdNew.isEmpty()) {
				pdTid.put("CUSTOMER_ID", "");	//客户
				pdTid.put("CREATED", Tools.date2Str(new Date()));	//created
				pdTid.put("UPDATED", Tools.date2Str(new Date()));	//updated
				tenantService.save(pdTid);
				
			}
		
		
			
			PageData pd = new PageData();
			if (!eascOrgs.isEmpty()) {
				for (EascOrg eascOrg : eascOrgs) {
					{
						
						String parentId= eascOrg.getParentId();
						if (parentId.equalsIgnoreCase("00000000-0000-0000-0000-000000000000")) {
							parentId="";
						}
						pd.put("ORGANIZATION_ID", eascOrg.getId());
						pd.put("ORG_NAME", eascOrg.getName());
						pd.put("PARENT_ID", parentId);
						pd.put("SORT", eascOrg.getSeqNo());
						pd.put("LOCATION", "");
						pd.put("SHORT_NAME", "");
						pd.put("TENANT_ID", eascParam.getTenantId());
						PageData o = organizationService.findById(pd);
						if (null != o) {
							organizationService.edit(pd);
						} else {
							organizationService.save(pd);
						}
					
					}
					errorMessage = "";
				}
			} else {
				errorMessage = "获取easc org 数据失败！";
				LOG.error("获取easc org 数据失败！");
			}

		} catch (Exception e) {
			errorMessage = e.toString();
			LOG.error(e.getMessage());
		}
		return errorMessage;
	}

	private String syncUser(EascParam eascParam)  {

		try {
			Result result = EascGetResultUtil.getUserListFromEasc(eascParam);
			Object data = result.getData();
			Integer retcode =result.getRetcode();
			if (retcode!=0) {
				LOG.info("获取easc user 信息："+ result.getRetmsg());
				return result.getRetmsg();
			}
			List<EascUser> userList = new ArrayList<>();
			if (retcode==0) {
			JSONArray array = JSONArray.fromObject(data);
			userList = JSONArray.toList(array, new EascUser(), new JsonConfig());
			}
			if (!userList.isEmpty()) {

				for (EascUser seUser : userList) {
					// UserAO
						String name=seUser.getName();
						PageData pd = new PageData();
						pd.put("WIZUSERS_ID", seUser.getId());
						pd.put("EMAIL", seUser.getEmail());
						pd.put("NAME", seUser.getName());
						pd.put("NICKNAME", seUser.getTrueName());
						pd.put("LEVEL", "0");
						pd.put("RAND", "0");
						pd.put("STATUS", "0");
						pd.put("NAME_QUANPIN", seUser.getName());
						pd.put("TENANT_ID", eascParam.tenantId);
						pd.put("CREATED", seUser.getCreateTime()); // created
						pd.put("UPDATED", Tools.date2Str(new Date()));

						PageData u = usersService.findById(pd);
						if (null != u) {
							pd.put("MOBILE", u.getString("MOBILE"));
							pd.put("AVATAR", u.getString("AVATAR"));
							pd.put("TEL", u.getString("TEL"));
							pd.put("AREA", u.getString("AREA"));
							pd.put("PASSWORD",u.get("PASSWORD"));
							pd.put("WIZUSERS_ID", u.getString("WIZUSERS_ID"));
							usersService.edit(pd);

						} else {
							pd.put("MOBILE", "");
							pd.put("PASSWORD", MD5.md5("123456789"));
							if(wizusersService.findByUName(pd).size()<=0){
								usersService.save(pd);
							}
							

						}
					}
				
				errorMessage = "";
			} else {
				errorMessage = "获取easc user 数据失败！";
				LOG.error("获取easc user 数据失败！");
			}

		} catch (Exception e) {
			errorMessage = e.toString();
			LOG.error(e.getMessage());
		}
		return errorMessage;
	}

	private String syncUserOrg(EascParam eascParam) throws Exception {

		try {
			Result result = EascGetResultUtil.getOrgUserListFromEasc(eascParam);
			Object data = result.getData();
			Integer retcode =result.getRetcode();
			if (retcode!=0) {
				LOG.info("获取easc orgUser 信息："+ result.getRetmsg());
				return result.getRetmsg();
			}
			List<EascOrgUser> orgUserList = new ArrayList<>();
			if (retcode==0) {
			JSONArray array = JSONArray.fromObject(data);
			orgUserList = JSONArray.toList(array, new EascOrgUser(), new JsonConfig());
			}
			if (!orgUserList.isEmpty()) {
				for (EascOrgUser eascOrgUser : orgUserList) {
					PageData pd = new PageData();
					pd.put("ORG_ID", eascOrgUser.getOrgId());
					pd.put("USER_ID", eascOrgUser.getUserId());
					pd.put("SORT", "0");
					List<PageData> list = orgUserService.findByOrgIDAndUid(pd);
					if (!list.isEmpty()) {
						PageData o = list.get(0);
						pd.put("ID", o.get("ID"));
						pd.put("USER_ID", eascOrgUser.getUserId());
						pd.put("ORG_ID", eascOrgUser.getOrgId());
						orgUserService.edit(pd);

					} else {
						pd.put("ID", UuidUtil.get32UUID());
						orgUserService.save(pd);
					}
					errorMessage = "";
				}

			} else {
				errorMessage = "获取easc orguser 数据失败！";
				LOG.error("获取easc orguser 数据失败！");
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
			errorMessage = e.toString();
		}
		return errorMessage;
	}



	public EascParam getEascParam() {
		EascParam eascParam = new EascParam();
		if (redisUtilService.exists("WZTK_easc_eascParam_map")) {
			List<String> rsmap =redisUtilService.hmget("WZTK_easc_eascParam_map","appId","domain","port","userName","pwd","tenantId","userId");
			eascParam=new EascParam(rsmap.get(0), rsmap.get(1), rsmap.get(2), rsmap.get(3), rsmap.get(4), rsmap.get(5),rsmap.get(6));
			String userId = eascParam.getUserId();
			if (StringUtils.isEmpty(userId)) {
				String appId = eascParam.getAppId();
				String domain = eascParam.getDomain();
				String port = eascParam.getPort();
				String userName = eascParam.getUserName();
				String pwd = eascParam.getPwd();
				pwd = MD5.md5(pwd);
				Result result = EascAuthHelper.login(domain, port, userName, pwd, appId);
				Integer retcode = result.getRetcode();
				Object data = result.getData();
				if (retcode == 0) {
					JSONObject jsonObject = JSONObject.fromObject(data);
					userId = jsonObject.getString("userId");
					eascParam.setUserId(userId);
					Map<String, String> map = new HashMap<String, String>();
					 map.put("appId", appId);
					 map.put("domain", domain);
					 map.put("port", port);
					 map.put("userName", userName);
					 map.put("pwd", eascParam.getPwd());
					 map.put("tenantId", rsmap.get(5));
					 map.put("userId", userId);		
					 redisUtilService.hmset("WZTK_easc_eascParam_map", map);
				}

			}
		}

		return eascParam;
	}
	
	
	
	
/*	private String syncUserPwd() throws Exception {
		String appId = redisUtilService.get("WZTK_easc_appId");
		String getAllSeUserMethodName = redisUtilService.get("WZTK_easc_getAllSeUserMethodName");
		String tenantIdOrgId = redisUtilService.get("WZTK_easc_tenantIdOrgId");
		try {
			List<SeUser> seUsers = WCFUtil.getSeUers(appId, getAllSeUserMethodName);
			for (SeUser seUser : seUsers) {

				// UserAO
				PageData pd = new PageData();
				pd.put("WIZUSERS_ID", seUser.getId());
				pd.put("EMAIL", seUser.getEmail());
				pd.put("NAME", seUser.getName());
				pd.put("NICKNAME", seUser.getTrueName());
				pd.put("LEVEL", "0");
				pd.put("RAND", "0");
				pd.put("STATUS", "0");
				pd.put("NAME_QUANPIN", seUser.getName());
				pd.put("TENANT_ID", tenantIdOrgId);

				pd.put("CREATED", seUser.getCreateDate()); // created
				pd.put("UPDATED", Tools.date2Str(new Date()));

				PageData u = usersService.findById(pd);
				if (null != u) {

					pd.put("MOBILE", u.getString("MOBILE"));
					pd.put("AVATAR", u.getString("AVATAR"));
					pd.put("PASSWORD", seUser.getPassword());
					pd.put("TEL", u.getString("TEL"));
					pd.put("AREA", u.getString("AREA"));
					pd.put("WIZUSERS_ID", u.getString("WIZUSERS_ID"));
					usersService.edit(pd);

				}

			}
		} catch (Exception e) {
			errorMessage = e.toString();
			LOG.error(e.getMessage());
		}
		return errorMessage;
	}*/
}
