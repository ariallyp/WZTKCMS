package com.fh.controller.wiztalk.clientversion;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.wiztalk.util.WzFileUpload;
import com.fh.entity.Page;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.clientversion.ClientVersionService;
import com.fh.service.wiztalk.wizapp.WizAppService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;

/** 
 * 类名称：ClientVersionController
 * 创建人：Arial 
 * 创建时间：2017-08-11
 */
@Controller
@RequestMapping(value="/clientversion")
public class ClientVersionController extends BaseController {
	
	String menuUrl = "clientversion/list.do"; //菜单地址(权限用)
	@Resource(name="clientversionService")
	private ClientVersionService clientversionService;
	
	@Resource(name="wizappService")
	private WizAppService wizappService;
	@Resource(name="redisUtilService")
	private IRedisUtilService redisUtilService;
	
	  
	
	
	
	/**
	 * 上传
	 */
	@RequestMapping(value="/saveFile")
	@ResponseBody
	public ModelAndView saveFile(
			@RequestParam(required=false) MultipartFile file
			) throws Exception{
		logBefore(logger, "新增Pictures");
		ModelAndView mv = this.getModelAndView();
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "";
		String filenameString =file.getOriginalFilename();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			if (null != file && !file.isEmpty()) {
				String filePath=redisUtilService.get("WZTK_UPLOAD_PLUG_PATH");//这里的路径是设置在接口设置中
				filePath = filePath+"plugins/";//插件上传路径。
				logger.info("上传路径为："+filePath);
				WzFileUpload.fileUpUpload(file, filePath, filenameString);	
				errInfo="上传成功";//执行上传
			}else{
				logger.error("上传失败");
				errInfo="上传失败";
			}
			
		}
		mv.addObject("msg",errInfo);
		mv.setViewName("save_result_alert");
		return mv;
		
		
	}
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增ClientVersion");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CLIENTVERSION_ID", this.get32UUID());	//主键
		pd.put("CREATED", Tools.date2Str(new Date()));	//created
		pd.put("UPDATED", Tools.date2Str(new Date()));	//updated
		clientversionService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ClientVersion");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			clientversionService.delete(pd);
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
		logBefore(logger, "修改ClientVersion");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATED", Tools.date2Str(new Date()));
		clientversionService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表ClientVersion");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = clientversionService.list(page);	//列出ClientVersion列表
			mv.setViewName("wiztalk/clientversion/clientversion_list");
			mv.addObject("varList", varList);
			List rentList = wizappService.listAllAPPS();//列出所有租户的供下拉框选择。
			mv.addObject("rentList", rentList);
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
		logBefore(logger, "去新增ClientVersion页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/clientversion/clientversion_edit");
			mv.addObject("msg", "save");
			List rentList = wizappService.listAllAPPS();//列出所有租户的供下拉框选择。
			mv.addObject("rentList", rentList);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAddFile")
	public ModelAndView goAddFile(){
		logBefore(logger, "去新增Pictures页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/clientversion/pictures_add");
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
		logBefore(logger, "去修改ClientVersion页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = clientversionService.findById(pd);	//根据ID读取
			mv.setViewName("wiztalk/clientversion/clientversion_edit");
			List rentList = wizappService.listAllAPPS();//列出所有租户的供下拉框选择。
			mv.addObject("rentList", rentList);
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
		logBefore(logger, "批量删除ClientVersion");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				clientversionService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出ClientVersion到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("TYPE");	//1
			titles.add("ver_code");	//2
			titles.add("ver_name");	//3
			titles.add("description");	//4
			titles.add("外网地址");	//5
			titles.add("内网地址");	//6
			titles.add("file_name");	//7
			titles.add("created");	//8
			titles.add("updated");	//9
			dataMap.put("titles", titles);
			List<PageData> varOList = clientversionService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TYPE"));	//1
				vpd.put("var2", varOList.get(i).get("VER_CODE").toString());	//2
				vpd.put("var3", varOList.get(i).getString("VER_NAME"));	//3
				vpd.put("var4", varOList.get(i).getString("VER_DESCRIPTION"));	//4
				vpd.put("var5", varOList.get(i).getString("DOWNLOAD_URL"));	//5
				vpd.put("var6", varOList.get(i).getString("LAN_URL"));	//6
				vpd.put("var7", varOList.get(i).getString("FILE_NAME"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATED"));	//8
				vpd.put("var9", varOList.get(i).getString("UPDATED"));	//9
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
