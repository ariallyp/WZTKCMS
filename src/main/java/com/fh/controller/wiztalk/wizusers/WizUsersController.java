package com.fh.controller.wiztalk.wizusers;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.wztk.AppPushEntity;
import com.fh.entity.wztk.BaseRequestEntity;
import com.fh.entity.wztk.BodyEntity;
import com.fh.entity.wztk.HeadEntity;
import com.fh.entity.wztk.ObjectContentEntity;
import com.fh.service.wiztalk.organization.OrganizationService;
import com.fh.service.wiztalk.orguser.OrgUserService;
import com.fh.service.wiztalk.tenant.TenantService;
import com.fh.service.wiztalk.wizapp.WizAppService;
import com.fh.service.wiztalk.wizusers.WizUsersService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.google.gson.Gson;
import com.skysea.pushing.api.MessagePublisher;
import com.skysea.pushing.api.PushInfrastructure;
import com.skysea.pushing.xmpp.XMPPPushInfrastructure;

/** 
 * 类名称：WizUsersController
 * 创建人：Arial 
 * 创建时间：2017-08-03
 */
@Controller
@RequestMapping(value="/wizusers")

public class WizUsersController extends BaseController {
	
	String menuUrl = "wizusers/list.do"; //菜单地址(权限用)
	@Resource(name="wizusersService")
	private WizUsersService wizusersService;
	@Resource(name="tenantService")
	private TenantService tenantService;
	@Resource(name="orguserService")
	private OrgUserService orguserService;
	@Resource(name="wizappService")
	private WizAppService wizappService;
	@Resource(name="organizationService")
	private OrganizationService organizationService;
	
	
	
	/**
	 * 判断邮箱是否存在
	 */
	@RequestMapping(value="/getOrglist")
	@ResponseBody
	public Object getOrglist(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(wizusersService.findByUE(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/saveAppAlow")
	public ModelAndView saveAppAlow() throws Exception{
		logBefore(logger, "新增WizUsers应用");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		String APPID =pd.getString("APP_ID");
		String type = pd.getString("type");
		pd.put("APPID", APPID);
		if(Tools.notEmpty(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				pd.put("ALOW_ID", ArrayDATA_IDS[i]);
				String UID=ArrayDATA_IDS[i];
				pd.put("UID", UID);
				if(wizusersService.findByUserApp(pd) == null){
					pd.put("ID", this.get32UUID());
					pd.put("TYPE", type);
					wizusersService.saveUserApp(pd);
					
				}
			}

		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(wizusersService.findByUName(pd).size()>0){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 判断邮箱是否存在
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(wizusersService.findByUE(pd) .size()>0){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增WizUsers");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ORG_ID = pd.getString("ORG_ID");
		String NICKNAME=pd.getString("NICKNAME");
		if (Tools.notEmpty(NICKNAME)) {
			pd.put("NAME_QUANPIN",pd.getString("NAME"));

		}
		pd.put("NAME_PY", pd.getString("NAME"));
		if(null != ORG_ID && !"".equals(ORG_ID)){
			ORG_ID = ORG_ID.trim();
			pd.put("ORG_ID", ORG_ID);
		}
		pd.put("WIZUSERS_ID", this.get32UUID());
		pd.put("CREATED", Tools.date2Str(new Date()));	//created
		pd.put("UPDATED", Tools.date2Str(new Date()));	//updated//主键
		pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")).toUpperCase());
		pd.put("STATUS", "0");
		pd.put("RAND", "0");
		pd.put("LEVEL", "0");
		pd.put("SORT", "0");
		wizusersService.save(pd);
		//save org_user 
		String USER_ID = pd.getString("WIZUSERS_ID");
		pd.put("USER_ID", USER_ID);
		pd.put("ID", this.get32UUID());
		orguserService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除WizUsers");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wizusersService.delete(pd);
			String USER_ID = pd.getString("WIZUSERS_ID");
			pd.put("USER_ID", USER_ID);
			orguserService.delete(pd);//同时删除 orguser 信息。
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改WizUsers");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATED", Tools.date2Str(new Date()));
		pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")).toUpperCase());
		wizusersService.edit(pd);
		orguserService.editByUserID(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/editAppAlow")
	public ModelAndView editAppAlow() throws Exception{
		logBefore(logger, "修改editAppAlow");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		wizusersService.editAppAlow(pd);;
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表WizUsers");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String ORG_ID = pd.getString("ORG_ID");
			String NAME = pd.getString("NAME");
			if(null != ORG_ID && !"".equals(ORG_ID)){
				ORG_ID = ORG_ID.trim();
				pd.put("ORG_ID", ORG_ID);
			}
			if(!Tools.isEmpty(NAME)){
				NAME = NAME.trim();
				pd.put("NAME", NAME);
			}
			String lastLoginEnd = pd.getString("lastLoginEnd");
			if (!Tools.isEmpty(lastLoginEnd)) {
				lastLoginEnd = lastLoginEnd +" 23:59:59";
				pd.put("lastLoginEnd", lastLoginEnd);
			}
			page.setPd(pd);
			List<PageData>	varList = wizusersService.datalistPageByOrgUser(page);	
			mv.setViewName("wiztalk/wizusers/wizusers_list");
			List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			mv.addObject("varList", varList);
			mv.addObject("rentList", rentList);
			mv.addObject("pd", pd);
			mv.addObject("ORG_ID",ORG_ID);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/listOrgUser")
	public ModelAndView listByOrgUser(Page page){
		logBefore(logger, "列表WizUsers");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String ORG_ID = pd.getString("ORG_ID");
			String parentId=pd.getString("PARENT_ID");
			if(null != ORG_ID && !"".equals(ORG_ID)){
				ORG_ID = ORG_ID.trim();
				pd.put("ORG_ID", ORG_ID);
			}
			String lastLoginEnd = pd.getString("lastLoginEnd");
			if (!Tools.isEmpty(lastLoginEnd)) {
				lastLoginEnd = lastLoginEnd +" 23:59:59";
				pd.put("lastLoginEnd", lastLoginEnd);
			}
			page.setPd(pd);
			List<PageData>	varList = wizusersService.datalistPageByOrgUser(page);	//列出WizUsers列表
			mv.setViewName("wiztalk/wizusers/wizusers_list");
			List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			mv.addObject("varList", varList);
			mv.addObject("rentList", rentList);
			mv.addObject("pd", pd);
			mv.addObject("ORG_ID",ORG_ID);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/listAppAlow")
	public ModelAndView listAppAlow(Page page){
		logBefore(logger, "列表WizUsers");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String ORG_ID = pd.getString("ORG_ID");
			String parentId=pd.getString("PARENT_ID");
			if(null != ORG_ID && !"".equals(ORG_ID)){
				ORG_ID = ORG_ID.trim();
				pd.put("ORG_ID", ORG_ID);//暂时不加这个限制
			}
		
			page.setPd(pd);
			List<PageData>	varList = wizusersService.datalistPageByAppAlow(page);	//列出WizUsers列表
			mv.setViewName("wiztalk/wizusers/appAlow_list");
		//	List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			List rentList = wizappService.listAllAPPS();//列出所有租户的供下拉框选择。
            mv.addObject("rentList", rentList);
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("ORG_ID",ORG_ID);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	
	/**
	 * 去新增应用页面
	 */
	@RequestMapping(value="/goAddAPP")
	public ModelAndView goAddAPP(){
		logBefore(logger, "去修改WizUsers页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/wizusers/addAPP");
			List rentList = wizappService.listAllAPPS();//列出所有租户的供下拉框选择。
			String DATA_IDS = pd.getString("DATA_IDS");
			pd.put("DATA_IDS", DATA_IDS);
			mv.addObject("rentList", rentList);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改WizUsers页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
		    String ORG_ID = pd.getString("ORG_ID");
		    String   TENANT_NAME ="";
		    pd = wizusersService.findById(pd);
		    PageData tIdpd = tenantService.findById(pd);
		    TENANT_NAME=tIdpd.getString("NAME");
            if(null != ORG_ID && !"".equals(ORG_ID)){
                   //根据ID读取,必须要放在前面，要不后面就会被替换掉。
                ORG_ID = ORG_ID.trim();
                pd.put("PARENT_ID", ORG_ID);//调用以前org通过获取父类的方法获取租户ID；
                PageData pDataTid=  organizationService.findTenantByPId(pd);//获取当前机构的租户的PD;   
              

                pd.put("ORGANIZATION_ID", ORG_ID);//
                PageData pdOrg=  organizationService.findById(pd);//获取当前机构的PD;
                String   ORG_NAME =pdOrg.getString("ORG_NAME");//租户名字
                pd.put("ORG_NAME", ORG_NAME);
                pd.put("ORG_ID", ORG_ID);
            }
            pd.put("TENANT_NAME", TENANT_NAME);
			mv.setViewName("wiztalk/wizusers/wizusers_edit");
			//List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			List orgList = organizationService.listAll(pd);//列出所有租户的供下拉框选择。
			
			mv.addObject("orgList", orgList);
			//mv.addObject("rentList", rentList);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEditAppAlow")
	public ModelAndView goEditAppAlow(){
		logBefore(logger, "去修改AppAlow页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = wizusersService.findByAppAlowId(pd);	//根据ID读取
			mv.setViewName("wiztalk/wizusers/appAlow_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/addAPP")
	public ModelAndView addAPP(){
		logBefore(logger, "去修改addAPP页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				pd.put("DATA_IDS", DATA_IDS);
			}
			mv.setViewName("wiztalk/wizusers/addAPP");
			List rentList = wizappService.listAllAPPS();//列出所有应用列表供下拉框选择。
			mv.addObject("rentList", rentList);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增WizUsers页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
		    String ORG_ID = pd.getString("ORG_ID");
	        if(null != ORG_ID && !"".equals(ORG_ID)){
	            ORG_ID = ORG_ID.trim();
	            pd.put("PARENT_ID", ORG_ID);//调用以前org通过获取父类的方法获取租户ID；
	            PageData pDataTid=  organizationService.findTenantByPId(pd);//获取当前机构的租户的PD;   
	            String   TENANT_ID =pDataTid.getString("TENANT_ID");//租户ID
	            String   TENANT_NAME =pDataTid.getString("TENANT_NAME");//租户名字 
	            pd.put("TENANT_ID", TENANT_ID);
	            pd.put("TENANT_NAME", TENANT_NAME);

                pd.put("ORGANIZATION_ID", ORG_ID);//
                PageData pdOrg=  organizationService.findById(pd);//获取当前机构的PD;
                String   ORG_NAME =pdOrg.getString("ORG_NAME");//租户名字
                pd.put("ORG_NAME", ORG_NAME);
                pd.put("ORG_ID", ORG_ID);
	        }
	        List orgList = organizationService.listAll(pd);//列出所有租户的供下拉框选择。
			mv.setViewName("wiztalk/wizusers/wizusers_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("orgList", orgList);
			mv.addObject("ORG_ID", ORG_ID);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除WizUsers");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				wizusersService.deleteAll(ArrayDATA_IDS);
				orguserService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/editAlowAppAll")
	@ResponseBody
	public Object editAlowAppAll() {
		logBefore(logger, "批量更新AlowApp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			String TYPE=pd.getString("TYPE");
			pd.put("TYPE", TYPE);
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for (int i = 0; i < ArrayDATA_IDS.length; i++) {
					pd.put("ALOW_ID", ArrayDATA_IDS[i]);
					wizusersService.editAppAlow(pd);
				}

				
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	@RequestMapping(value="/pushAppMsg")
	@ResponseBody
	 public void pushAppMsg(String appid, String appName, String body, String
			 title, int MessageType,
			 List<String> toUsers) throws Exception {
			
		
			
			 HashMap<String, String> arti = new HashMap<String, String>();
			
			 // baseRequest
			BaseRequestEntity baseRequest = new BaseRequestEntity();
			 baseRequest.setDeviceID("");
			 baseRequest.setToken(appid);
			 baseRequest.setUid("");
			
			 List<BodyEntity> bodys = new ArrayList<BodyEntity>();
			
			 BodyEntity bodyEntity1 = new BodyEntity();
			 bodyEntity1.setContent(body);
			 bodys.add(bodyEntity1);
			
			 HeadEntity headEntity1 = new HeadEntity();
			 headEntity1.setContent(title);
			 headEntity1.setPubTime(System.currentTimeMillis());
			
			ObjectContentEntity objectContent = new ObjectContentEntity();
			 objectContent.setBody(bodys);
			 objectContent.setAppId(appid);
			 objectContent.setHead(headEntity1);
			
			 List<String> sessions = new ArrayList<String>();
			 sessions.add("all");
			
			 AppPushEntity entity = new AppPushEntity();
			 entity.setBaseRequest(baseRequest);
			 entity.setContent(title);
			 entity.setExpire(3600000L);
			 entity.setMsgType(MessageType);
			 entity.setObjectContent(objectContent);
			 entity.setSessions(sessions);
			 entity.setStatusId("9999");
			
			 List<String> touserNames = new ArrayList<String>();
			 List<String> touserIds = new ArrayList<String>();
			/* for (String name : toUsers) {
			 String id = sendGet("http:localhost/app/client/device/getUserIdByName/"
			 + name);
			 if (!id.equals("")) {
			 touserNames.add(id + "@user");
			 touserIds.add(id);
			 }
			 }*/
			
			 entity.setToUserNames(touserNames);
			
			 arti.put("app", new Gson().toJson(entity));
			 for (String uid : touserIds) {
			
			 }
			
			 
			 PushInfrastructure factory = new XMPPPushInfrastructure("skysea.com", "http://192.168.50.101:9090/plugins/push/packet");
		        
		        /** 获得事件发布器(可以单例保存) **/
		        MessagePublisher messagePublisher = factory.getMessagePublisher();
		        messagePublisher.publish("58db3e70-ef6d-481d-95af-4790f6a883b9","", arti);
			 }
	
	
	
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
