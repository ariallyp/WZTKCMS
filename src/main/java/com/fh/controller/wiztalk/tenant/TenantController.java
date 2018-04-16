package com.fh.controller.wiztalk.tenant;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.logging.Log;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Jurisdiction;
import com.fh.service.wiztalk.tenant.TenantService;

/** 
 * 类名称：TenantController
 * 创建人：Arial 
 * 创建时间：2017-08-02
 */
@Controller
@RequestMapping(value="/tenant")
public class TenantController extends BaseController {
	
	String menuUrl = "tenant/list.do"; //菜单地址(权限用)
	@Resource(name="tenantService")
	private TenantService tenantService;
	
	
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
			if(tenantService.findByUName(pd) != null){
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
		logBefore(logger, "新增Tenant");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TENANT_ID", this.get32UUID());	//主键
		pd.put("CUSTOMER_ID", "");	//客户
		pd.put("CREATED", Tools.date2Str(new Date()));	//created
		pd.put("UPDATED", Tools.date2Str(new Date()));	//updated
		tenantService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(){
		logBefore(logger, "删除Tenant");
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "";
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){//校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			List tenantList_z = tenantService.listAllOrgsByPId(pd);		//列出此部门的所有机构
			List tenantList_u = tenantService.listAllUsersByPId(pd);		//列出此部门的所有机构下的用户
			if (tenantList_z.size()>0||tenantList_u.size()>0) {
				errInfo = "false";
				
			}else {
				tenantService.delete(pd);
				errInfo = "success";
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
		logBefore(logger, "修改Tenant");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATED", Tools.date2Str(new Date()));
		tenantService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logger.info("TEST");
		logBefore(logger, "列表Tenant");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String tenant_name = pd.getString("tenant_name");
			String lastLoginEnd = pd.getString("lastLoginEnd");
			if(!Tools.isEmpty(tenant_name)){
				tenant_name = tenant_name.trim();
				pd.put("NAME", tenant_name);
			}
			
			if (!Tools.isEmpty(lastLoginEnd)) {
				lastLoginEnd = lastLoginEnd +" 23:59:59";
				pd.put("lastLoginEnd", lastLoginEnd);
			}
			page.setPd(pd);
			List<PageData>	varList = tenantService.list(page);	//列出Tenant列表
			mv.setViewName("wiztalk/tenant/tenant_list");
			mv.addObject("varList", varList);
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
		logBefore(logger, "去新增Tenant页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/tenant/tenant_edit");
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
		logBefore(logger, "去修改Tenant页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = tenantService.findById(pd);	//根据ID读取
			mv.setViewName("wiztalk/tenant/tenant_edit");
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
		logBefore(logger, "批量删除Tenant");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				tenantService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Tenant到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("机构名称");	//1
			titles.add("代码");	//2
			titles.add("状态");	//3
			titles.add("客户");	//4
			titles.add("created");	//5
			titles.add("updated");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = tenantService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("CODE"));	//2
				vpd.put("var3", varOList.get(i).get("STATUS").toString());	//3
				vpd.put("var4", varOList.get(i).getString("CUSTOMER_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("CREATED"));	//5
				vpd.put("var6", varOList.get(i).getString("UPDATED"));	//6
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
