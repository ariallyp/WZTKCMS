package com.fh.controller.wiztalk.param;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.wztk.SysConfig;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Jurisdiction;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.param.ParamService;

/** 
 * 类名称：ParamController
 * 创建人：FH 
 * 创建时间：2018-10-19
 */
@Controller
@RequestMapping(value="/param")
public class ParamController extends BaseController {
	
	String menuUrl = "param/list.do"; //菜单地址(权限用)
	@Resource(name="paramService")
	private ParamService paramService;
	@Resource(name = "redisUtilService")
	private IRedisUtilService redisUtilService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Param");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<SysConfig> syslist = new ArrayList<>();
		String field = pd.getString("field");
		String value = pd.getString("value");
		String remark = pd.getString("remark");
		SysConfig sysConfig = new SysConfig(field, value,remark);
		String sysValue = redisUtilService.get("wiztalk_sys_config");
		if (!StringUtils.isEmpty(sysValue)) {
			syslist = JSONObject.parseArray(sysValue, SysConfig.class);

		}
		syslist.add(sysConfig);
		if (!syslist.isEmpty()) {
			syslist=this.removeDupliById(syslist);
			String jString = JSON.toJSONString(syslist);
			redisUtilService.set("wiztalk_sys_config", jString);
			mv.addObject("msg","success");
		}else {
			mv.addObject("msg","error");
		}
		
		
		
		mv.setViewName("save_result");
		return mv;
	}
	
	 public static List<SysConfig> removeDupliById(List<SysConfig> persons) {
	        Set<SysConfig> personSet = new TreeSet<>((o1, o2) -> o1.getField().compareTo(o2.getField()));
	        personSet.addAll(persons);

	        return new ArrayList<>(personSet);
	    }


	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Param");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<SysConfig> syslist = new ArrayList<>();			
			String field = pd.getString("field");
			String sysValue = redisUtilService.get("wiztalk_sys_config");
			 syslist = JSONObject.parseArray(sysValue, SysConfig.class);
			for (SysConfig sysConfig2 : syslist) {
				String field2=sysConfig2.getField();
				if (field2.equals(field)) {
					syslist.remove(sysConfig2);
					break;
				}
			}
			
			String jString = JSON.toJSONString(syslist);
			redisUtilService.set("wiztalk_sys_config", jString);
			pd.put("msg", "ok");
			pd.put("currentPage", "1");
			pd.put("nextPage", "");
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
		logBefore(logger, "修改Param");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<SysConfig> syslist = new ArrayList<>();
		String field = pd.getString("field");
		String value = pd.getString("value");
		String remark = pd.getString("remark");
		SysConfig sysConfig = new SysConfig(field, value,remark);
		String sysValue = redisUtilService.get("wiztalk_sys_config");
		syslist = JSONObject.parseArray(sysValue, SysConfig.class);
		for (SysConfig sysConfig2 : syslist) {
			String field2=sysConfig2.getField();
			if (field2.equals(field)) {
				syslist.remove(sysConfig2);
				break;
			}
		}
		syslist.add(sysConfig);
		if (!syslist.isEmpty()) {
			syslist=this.removeDupliById(syslist);
			String jString = JSON.toJSONString(syslist);
			redisUtilService.set("wiztalk_sys_config", jString);
			mv.addObject("msg","success");
		}else {
			mv.addObject("msg","error");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Param");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			String value = redisUtilService.get("wiztalk_sys_config");
			List<SysConfig> varList = JSONObject.parseArray(value, SysConfig.class);
			
			mv.setViewName("wiztalk/param/param_list");
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
		logBefore(logger, "去新增Param页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/param/param_edit");
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
		logBefore(logger, "去修改Param页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String field = pd.getString("field");
			String value = pd.getString("value");
			String remark = pd.getString("remark");
			pd.put("field", field);
			pd.put("value", value);
			pd.put("remark", remark);
			mv.setViewName("wiztalk/param/param_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
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
