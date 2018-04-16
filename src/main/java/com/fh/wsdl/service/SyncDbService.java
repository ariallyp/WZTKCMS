package com.fh.wsdl.service;

import java.util.Date;
import java.util.List;
import oracle.net.aso.p;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.organization.OrganizationService;
import com.fh.service.wiztalk.orguser.OrgUserService;
import com.fh.service.wiztalk.synclog.SyncLogService;
import com.fh.service.wiztalk.tenant.TenantService;
import com.fh.service.wiztalk.wizusers.WizUsersService;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.fh.wsdl.entity.SeOrganization;
import com.fh.wsdl.entity.SeUser;
import com.fh.wsdl.entity.SeUserOrganization;
import com.fh.wsdl.util.WCFUtil;


@Service("syncDbService")
public class SyncDbService {

	
	protected Logger LOG = Logger.getLogger(this.getClass());
	@Autowired
	private TenantService tenantService;
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
	private String errorMessage=null;
	
	public void syncdb() throws Exception {}
	
	public String syncdbResult(String type) throws Exception {
		LOG.info("同步用户数据开始：");
		long beginSynOrgMillis = System.currentTimeMillis();
		PageData pd = new PageData();
		pd.put("SYNCLOG_ID", UuidUtil.get32UUID());	//主键
		StringBuffer sb = new StringBuffer();
		String wsdlsite =redisUtilService.get("WZTK_easc_wsdlsite");
		String appwsdl =redisUtilService.get("WZTK_easc_appwsdl");
		String appId=redisUtilService.get("WZTK_easc_appId");
	    String SYNC_WZTK_PWD = redisUtilService.get("SYNC_WZTK_PWD");

		pd.put("APPID", appId);
		pd.put("WSDLSITE", wsdlsite);
		pd.put("TYPE", type);
		wsdlLoactionCache.getInstance().setWsdlsite(wsdlsite);
		wsdlLoactionCache.getInstance().setAppwsdl(appwsdl);
		sb.append("同步成功 :"+"\n");
		if ("YES".equals(SYNC_WZTK_PWD)) {
		    long begingMillis = System.currentTimeMillis();
	        LOG.info("开始同步Org表的毫秒数为：" + beginSynOrgMillis);
	        errorMessage=this.syncUserPwd();
	        long endMillis = System.currentTimeMillis();
	        sb.append("同步 用户密码 一共用时：" + (endMillis - begingMillis) + "毫秒");
        }else {
            
        
		long begingMillis = System.currentTimeMillis();
		LOG.info("开始同步Org表的毫秒数为：" + beginSynOrgMillis);
		errorMessage=this.syncOrg();
		long endSynOrgMillis = System.currentTimeMillis();
		LOG.info("Org表同步完成时的毫秒数为：" + endSynOrgMillis);
		LOG.info("同步Org表一共用时：" + (endSynOrgMillis - beginSynOrgMillis) + "毫秒");
		sb.append("同步Org表一共用时：" + (endSynOrgMillis - beginSynOrgMillis) + "毫秒"+"\n");
		long beginSynUserMillis = System.currentTimeMillis();
		LOG.info("开始同步User表的毫秒数为：" + beginSynUserMillis);

		errorMessage=this.syncUser();
		
		long endSynUserMillis = System.currentTimeMillis();
		LOG.info("User表同步完成时的毫秒数为：" + endSynUserMillis);
		LOG.info("同步User表一共用时：" + (endSynUserMillis - beginSynUserMillis) + "毫秒");
		sb.append("同步User表一共用时：" + (endSynUserMillis - beginSynUserMillis) + "毫秒"+"\n");

		long beginSynOrgUserMillis = System.currentTimeMillis();
		LOG.info("开始同步OrgUser表的毫秒数为：" + beginSynOrgUserMillis);

		errorMessage=this.syncUserOrg();
		long endSynOrgUserMillis = System.currentTimeMillis();
		LOG.info("OrgUser表同步完成时的毫秒数为：" + endSynOrgUserMillis);
		LOG.info("同步OrgUser表一共用时：" + (endSynOrgUserMillis - beginSynOrgUserMillis) + "毫秒"+"\n");
		long endMillis = System.currentTimeMillis();
		sb.append("同步一共用时：" + (endMillis - begingMillis) + "毫秒");
		
        }
		pd.put("CREATED", Tools.date2Str(new Date()));
		if (!Tools.isEmpty(errorMessage)) {
			pd.put("RESULT", errorMessage);
			synclogService.save(pd);
			return "同步失败： "+errorMessage;
			
		}else {
			pd.put("RESULT", sb.toString());
			synclogService.save(pd);
			return sb.toString();
		}
       
	}

	private String syncOrg() throws Exception {
		
		String appId=redisUtilService.get("WZTK_easc_appId");
		String getAllOrganizationMethodName =redisUtilService.get("WZTK_easc_getAllOrganizationMethodName");
		String isSyncTenantId =redisUtilService.get("WZTK_easc_isSyncTenantId");
		String tenantIdOrgId =redisUtilService.get("WZTK_easc_tenantIdOrgId");
		try {
			List<SeOrganization> seOrganizations = WCFUtil.getSeOrganizations(appId, getAllOrganizationMethodName);
			for (SeOrganization seOrganization : seOrganizations) {
				if (isSyncTenantId.equals("true") && seOrganization.getId().equals(tenantIdOrgId)) {
					if (seOrganization.getIsDeleted()) {
						//删除租户
						PageData pd = new PageData();
						pd.put("TENANT_ID", seOrganization.getId());
						tenantService.delete(pd);
						pd.put("ORG_ID", seOrganization.getId());
						orgUserService.deleteByOrgId(pd);
						
					} else {
						PageData pd = new PageData();
						//Tenant
						pd.put("TENANT_ID", seOrganization.getId());
						pd.put("NAME", seOrganization.getName());
						pd.put("CODE", "1");
						pd.put("STATUS", "0");
						pd.put("CUSTOMER_ID", "2");
						pd.put("CREATED", Tools.date2Str(new Date()));	//created
						pd.put("UPDATED", Tools.date2Str(new Date()));

						PageData o = tenantService.findById(pd);
						if (null != o) {
							tenantService.edit(pd);
						} else {
							tenantService.save(pd);
						}

					}

				} else if (!seOrganization.getId().equals(tenantIdOrgId)) {
					if (seOrganization.getIsDeleted()) {
						PageData pd = new PageData();
						pd.put("ORGANIZATION_ID", seOrganization.getId());
						organizationService.delete(pd);
						pd.put("ORG_ID", seOrganization.getId());
						orgUserService.deleteByOrgId(pd);
					} else {
						//if (isCurrentTenantChildren(seOrganizations, seOrganization.getParentId(), tenantIdOrgId)) {
							//OrgAO 
							PageData pd = new PageData();
							pd.put("ORGANIZATION_ID", seOrganization.getId());
							pd.put("ORG_NAME", seOrganization.getName());
							pd.put("PARENT_ID", seOrganization.getParentId());
							pd.put("SHORT_NAME", seOrganization.getCode());
							pd.put("SORT", seOrganization.getSeqNo());
							pd.put("LOCATION", "");
							pd.put("TENANT_ID", tenantIdOrgId);
							PageData o = organizationService.findById(pd);
							if (null != o) {
								organizationService.edit(pd);
							} else {
								organizationService.save(pd);
							}
						//}
					}
				}
			}
		} catch (Exception e) {
			errorMessage=e.toString();
			LOG.error(e.getMessage());
		}
		return null;
	}

	private boolean isCurrentTenantChildren(List<SeOrganization> seOrganizations, String parentId, String tenantId) {

		if (parentId.endsWith(tenantId)) {
			return true;
		}
		for (SeOrganization seOrganization : seOrganizations) {
			if (seOrganization.getId().equals(parentId)) {
				
				return isCurrentTenantChildren(seOrganizations, seOrganization.getParentId(), tenantId);
			} else {
				return true;
			}
		}

		return false;
	}

	private String syncUser() throws Exception {
		String appId=redisUtilService.get("WZTK_easc_appId");
		String getAllSeUserMethodName =redisUtilService.get("WZTK_easc_getAllSeUserMethodName");
		String tenantIdOrgId =redisUtilService.get("WZTK_easc_tenantIdOrgId");
		try {
			List<SeUser> seUsers = WCFUtil.getSeUers(appId, getAllSeUserMethodName);
			for (SeUser seUser : seUsers) {

				if (seUser.getIsDeleted()) {
					PageData pd = new PageData();
					pd.put("WIZUSERS_ID", seUser.getId());
					usersService.delete(pd);
					pd.put("USER_ID", seUser.getId());
					usersService.deleteByUserId(pd);
					usersService.deleteByUserIdUU(pd);
					usersService.deleteByUserIdAPPU(pd);
					organizationService.delete(pd);
					
				} else {
					//UserAO 
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
					long l = Long.parseLong(seUser.getCreateDate().substring(6, 19));
					pd.put("CREATED", Tools.date2Str(new Date(l)));	//created
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
						
					} else {
						pd.put("MOBILE", "");
						pd.put("PASSWORD", seUser.getPassword());
						usersService.save(pd);
						
					}
				}

			}
		} catch (Exception e) {
			errorMessage=e.toString();
			LOG.error(e.getMessage());
		}
		return  errorMessage;
	}

	private String syncUserOrg() throws Exception {
		String appId=redisUtilService.get("WZTK_easc_appId");
		
		String getAllUserOrgInfoMethodName =redisUtilService.get("WZTK_easc_getAllUserOrgInfoMethodName");
		
		try {
			List<SeUserOrganization> seUserOrganizations = WCFUtil.getSeUserOrganizations(appId,
					getAllUserOrgInfoMethodName);

			for (SeUserOrganization seUserOrganization : seUserOrganizations) {
				PageData pd = new PageData();
				pd.put("ORGANIZATION_ID", seUserOrganization.getOrganizationId());
				PageData o = organizationService.findById(pd);
				if (null != o) {
					
					pd.put("ID", seUserOrganization.getId());
					pd.put("USER_ID", seUserOrganization.getUserId());
					pd.put("ORG_ID", seUserOrganization.getOrganizationId());
					pd.put("SORT", "0");
					
					PageData ou = orgUserService.findById(pd);
					if (null != ou) {
						orgUserService.edit(pd);
					} else {
						orgUserService.save(pd);					
						}
				}

			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			errorMessage=e.toString();
		}
		return errorMessage;
	}
	
	   private String syncUserPwd() throws Exception {
	        String appId=redisUtilService.get("WZTK_easc_appId");
	        String getAllSeUserMethodName =redisUtilService.get("WZTK_easc_getAllSeUserMethodName");
	        String tenantIdOrgId =redisUtilService.get("WZTK_easc_tenantIdOrgId");
	        try {
	            List<SeUser> seUsers = WCFUtil.getSeUers(appId, getAllSeUserMethodName);
	            for (SeUser seUser : seUsers) {

	                    //UserAO 
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
	                    long l = Long.parseLong(seUser.getCreateDate().substring(6, 19));
	                    pd.put("CREATED", Tools.date2Str(new Date(l))); //created
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
	            errorMessage=e.toString();
	            LOG.error(e.getMessage());
	        }
	        return  errorMessage;
	    }
}
