package com.fh.controller.wiztalk.organization;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.fh.entity.manager.Rent;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.fh.util.Jurisdiction;
import com.fh.service.wiztalk.organization.OrganizationService;
import com.fh.service.wiztalk.tenant.TenantService;
import com.fh.service.wiztalk.wizusers.WizUsersService;

/** 
 * 类名称：OrganizationController
 * 创建人：Arial 
 * 创建时间：2017-08-03
 */
@Controller
@RequestMapping(value="/organization")
public class OrganizationController extends BaseController {
	
	String menuUrl = "organization/list.do"; //菜单地址(权限用)
	@Resource(name="organizationService")
	private OrganizationService organizationService;
	
	@Resource(name="tenantService")
	private TenantService tenantService;
	@Resource(name="wizusersService")
	private WizUsersService wizusersService;
	
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
			if(organizationService.findByUName(pd) != null){
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
		logBefore(logger, "新增Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ORGANIZATION_ID", this.get32UUID());	//主键
		pd.put("LOCATION", "");
		pd.put("SORT", "1");
		organizationService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		//mv.setViewName("wiztalk/organization/ztree");

		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(){
		logBefore(logger, "删除Organization");
		String errInfo = "";
		Map<String,String> map = new HashMap<String,String>();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){ //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List orgUserList_u = organizationService.listAllUsersByPId(pd);
			List orgParentList = organizationService.checkParent(pd.getString("ORGANIZATION_ID"));
			if (orgUserList_u.size()>0||orgParentList.size()>0) {
			    errInfo="false";
            }else {
                organizationService.delete(pd); 
                errInfo="success";
            }
			
		} catch(Exception e){
		    errInfo = "false";
			logger.error(e.toString(), e);
		}
		
	}
		map.put("result", errInfo);
        return AppUtil.returnObject(new PageData(), map);
	}
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("LOCATION", "");
		pd.put("SORT", "1");
		organizationService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");

		return mv;
	}
	
	public JSONArray genZtreeList(List<PageData> zTreeList,PageData pd,String type) throws Exception{
		
		 JSONArray jsonArray=new JSONArray();  
        
			for (PageData organization : zTreeList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", organization.getString("ORGANIZATION_ID"));
				jsonObject.put("pid", organization.getString("PARENT_ID"));
				jsonObject.put("name", organization.getString("ORG_NAME"));

				// 判断所选择节点是否是父节点，如果是设置isParent属性为true,不是设置为false
			/*	List subtreeList = new ArrayList();
				subtreeList = organizationService.checkParent(organization.getString("ORGANIZATION_ID"));*/
				//判读ORG的父类 统一设置为true，这样可以选择并创建它为父类。
				/*if (subtreeList.size() > 0) {
					jsonObject.put("isParent", "true");
				} else {
					jsonObject.put("isParent", "false");
				}
				//如果是users用到ORG list那么所有的org要设为父类。
				if ("USERS".equals(type)) {
					jsonObject.put("isParent", "true");
				}*/
				jsonObject.put("isParent", "true");
				jsonObject.put("open", "false");
				jsonArray.add(jsonObject);
			}
		
		return jsonArray;
		
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/ztreeAppAlow")
	public ModelAndView ztreeAppAlow(Page page){
		logBefore(logger, "列表Organization");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//RENT_NAME
			String parentId=pd.getString("PARENT_ID");
			String ORG_NAME = pd.getString("ORG_NAME");

			if(null != ORG_NAME && !"".equals(ORG_NAME)){
				ORG_NAME = ORG_NAME.trim();
				pd.put("ORG_NAME", ORG_NAME);
			}
			page.setPd(pd);
			List<PageData>	varList = wizusersService.datalistPageByAppAlow(page);		//列出Organization列表
			//列出ORG列表
			List<PageData>	zTreeList = organizationService.listAll(pd);	//列出ZTREE数据。
	        JSONArray jsonArray=new JSONArray();  
	        jsonArray= this.genZtreeList(zTreeList,pd,"USERS");
	        String treeNodes=	jsonArray.toString();
			mv.addObject("treeNodes", treeNodes);
			mv.setViewName("wiztalk/organization/appAlow_list_ztree");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/ztreeUser")
	public ModelAndView ztreeUser(Page page){
		logBefore(logger, "列表Organization");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//RENT_NAME
			String parentId=pd.getString("PARENT_ID");
			String ORG_NAME = pd.getString("ORG_NAME");

			if(null != ORG_NAME && !"".equals(ORG_NAME)){
				ORG_NAME = ORG_NAME.trim();
				pd.put("ORG_NAME", ORG_NAME);
			}
			page.setPd(pd);
			List<PageData>	varList = organizationService.list(page);	//列出Organization列表
			List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			//列出ORG列表
			List<PageData>	zTreeList = organizationService.listAll(pd);	//列出ZTREE数据。
	        JSONArray jsonArray=new JSONArray();  
	        jsonArray= this.genZtreeList(zTreeList,pd,"USERS");
	        String treeNodes=	jsonArray.toString();
			mv.addObject("treeNodes", treeNodes);
			mv.setViewName("wiztalk/organization/orgUser_list_ztree");
			mv.addObject("varList", varList);
			mv.addObject("rentList", rentList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/ztree")
	public ModelAndView ztree(Page page){
		logBefore(logger, "列表Organization");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//RENT_NAME
			String parentId=pd.getString("PARENT_ID");
			String ORG_NAME = pd.getString("ORG_NAME");

			if(null != ORG_NAME && !"".equals(ORG_NAME)){
				ORG_NAME = ORG_NAME.trim();
				pd.put("ORG_NAME", ORG_NAME);
			}
			page.setPd(pd);
			List<PageData>	varList = organizationService.list(page);	//列出Organization列表
			List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			//列出ORG列表
			List<PageData>	zTreeList = organizationService.listAll(pd);	//列出ZTREE数据。
	        JSONArray jsonArray=new JSONArray();  
	        jsonArray= this.genZtreeList(zTreeList,pd,"ORGS");
	        String treeNodes=	jsonArray.toString();
			mv.addObject("treeNodes", treeNodes);
			mv.setViewName("wiztalk/organization/org_list_ztree");
			mv.addObject("varList", varList);
			mv.addObject("rentList", rentList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Organization");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//RENT_NAME
			String TENANT_ID="";
			String ORG_NAME = pd.getString("ORG_NAME");
			String parentId=pd.getString("PARENT_ID");
			if(null != ORG_NAME && !"".equals(ORG_NAME)){
				ORG_NAME = ORG_NAME.trim();
				pd.put("ORG_NAME", ORG_NAME);
			}else {
				if (Tools.isEmpty(parentId)) {
					pd.put("PARENT_ID", "");
				}else {
					pd.put("PARENT_ID", parentId);
					
				}
				
			}
			
			page.setPd(pd);
			List<PageData>	varList = organizationService.orgRentlistPage(page);	//列出Organization列表
			List rentList = tenantService.listRentByPID(pd);//列出所有租户的供下拉框选择。
			mv.setViewName("wiztalk/organization/organization_list");
			mv.addObject("varList", varList);
			mv.addObject("rentList", rentList);
			mv.addObject("PARENT_ID",parentId);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Organization页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String TENANT_ID="";
		List rentList = null ;
		try {
			PageData pDataTid=  organizationService.findTenantByPId(pd);//获取当前机构的tenant
			if (pDataTid!=null) {
				TENANT_ID =pDataTid.getString("TENANT_ID");
				pd.put("TENANT_ID", TENANT_ID);
				 rentList = tenantService.listRentByPID(pd);//列出所有租户的供下拉框选择。
			}else {
				 rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。

			}
			List<PageData>	varList = organizationService.listAll(pd);	//列出Organization列表
			mv.addObject("rentList", rentList);
			//pd.put("PARENT_ID", parentId);
			mv.addObject("varList", varList);
			mv.setViewName("wiztalk/organization/organization_edit");
			mv.addObject("msg", "save");
			
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
		logBefore(logger, "去修改Organization页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = organizationService.findById(pd);	//根据ID读取
			List<PageData>	varList = organizationService.listAll(pd);	//列出Organization列表
			List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			mv.addObject("rentList", rentList);
			mv.addObject("varList", varList);
			mv.setViewName("wiztalk/organization/organization_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
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
		logBefore(logger, "批量删除Organization");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				organizationService.deleteAll(ArrayDATA_IDS);
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
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Organization到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("名称");	//1
			titles.add("简称");	//2
			titles.add("父类");	//3
			titles.add("位置");	//4
			titles.add("租户");	//5
			titles.add("排序");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = organizationService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("SHORT_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("PARENT_ID"));	//3
				vpd.put("var4", varOList.get(i).getString("LOCATION"));	//4
				vpd.put("var5", varOList.get(i).getString("TENANT_ID"));	//5
				vpd.put("var6", varOList.get(i).get("SORT").toString());	//6
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
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
