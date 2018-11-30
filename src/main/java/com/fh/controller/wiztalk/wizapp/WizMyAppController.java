package com.fh.controller.wiztalk.wizapp;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.controller.wiztalk.util.WzFileUpload;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Jurisdiction;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.tenant.TenantService;
import com.fh.service.wiztalk.wizapp.WizAppService;

/** 
 * 类名称：WizmyAppController
 * 创建人：Arial 
 * 创建时间：2018-08-13
 */
@Controller
@RequestMapping(value="/myapp")
public class WizMyAppController extends BaseController {
	
	String menuUrl = "myapp/list.do"; //菜单地址(权限用)
	@Resource(name="wizappService")
	private WizAppService wizappService;
	@Resource(name="tenantService")
	private TenantService tenantService;
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
     * 去上传页面
     */
    @RequestMapping(value="/goAddFile")
    public ModelAndView goAddFile(){
        logBefore(logger, "去新增Pictures页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("wiztalk/myapp/pictures_add");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
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
			if(wizappService.findByUName(pd) != null){
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
		logBefore(logger, "新增WizApp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
		pd.put("WIZAPP_ID", this.get32UUID());	//主键
		pd.put("STATUS", "2");	//STATUS
		pd.put("SORT", "0");	//sort
		pd.put("LEVEL", "0");	//LEVEL
		pd.put("CREATED", Tools.date2Str(new Date()));	//created
		pd.put("UPDATED", Tools.date2Str(new Date()));	//updated
		pd.put("FOLLOW", USERNAME);	//follow
		wizappService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除WizApp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wizappService.delete(pd);
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
		logBefore(logger, "修改WizApp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATED", Tools.date2Str(new Date()));
		wizappService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表WizApp");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
		
		
		try{
			pd = this.getPageData();
			pd.put("USERNAME", USERNAME);
			String NAME=pd.getString("NAME");
			if (!Tools.isEmpty(NAME)) {
				NAME=NAME.trim();
				pd.put("NAME", NAME);
			}
		
			page.setPd(pd);
			List<PageData>	varList = wizappService.listMyApp(page);	//列出WizApp列表
			List rentList = tenantService.listAllappERRents();//列出所有租户的供下拉框选择。
			mv.setViewName("wiztalk/myapp/myapp_list");
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
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增myApp页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/myapp/myapp_edit");
			pd.put("TENANT_ID", "FA1AEC96A1AC4414A31C9CB53D6F39A5");
			List rentList = tenantService.listAllByTid(pd);//列出所有租户的供下拉框选择。
			mv.addObject("rentList", rentList);
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
		logBefore(logger, "去修改myApp页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = wizappService.findById(pd);	//根据ID读取
			mv.setViewName("wiztalk/myapp/myapp_edit");
			pd.put("TENANT_ID", "FA1AEC96A1AC4414A31C9CB53D6F39A5");
			List rentList = tenantService.listAllByTid(pd);//列出所有租户的供下拉框选择。
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
		logBefore(logger, "批量删除myApp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				wizappService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出myApp到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("名称");	//1
			titles.add("token");	//2
			titles.add("TYPE");	//3
			titles.add("STATUS");	//4
			titles.add("sort");	//5
			titles.add("LEVEL");	//6
			titles.add("avatar");	//7
			titles.add("tenant_id");	//8
			titles.add("created");	//9
			titles.add("updated");	//10
			titles.add("name_py");	//11
			titles.add("name_quanpin");	//12
			titles.add("description");	//13
			titles.add("follow");	//14
			dataMap.put("titles", titles);
			List<PageData> varOList = wizappService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("TOKEN"));	//2
				vpd.put("var3", varOList.get(i).getString("TYPE"));	//3
				vpd.put("var4", varOList.get(i).get("STATUS").toString());	//4
				vpd.put("var5", varOList.get(i).get("SORT").toString());	//5
				vpd.put("var6", varOList.get(i).get("LEVEL").toString());	//6
				vpd.put("var7", varOList.get(i).getString("AVATAR"));	//7
				vpd.put("var8", varOList.get(i).getString("TENANT_ID"));	//8
				vpd.put("var9", varOList.get(i).getString("CREATED"));	//9
				vpd.put("var10", varOList.get(i).getString("UPDATED"));	//10
				vpd.put("var11", varOList.get(i).getString("NAME_PY"));	//11
				vpd.put("var12", varOList.get(i).getString("NAME_QUANPIN"));	//12
				vpd.put("var13", varOList.get(i).getString("DESCRIPTION"));	//13
				vpd.put("var14", varOList.get(i).getString("FOLLOW"));	//14
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
