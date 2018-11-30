package com.fh.controller.wiztalk.wiztalkconfig;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.wztk.EascParam;
import com.fh.entity.wztk.SysConfig;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.wiztalkconfig.WIZTALKConfigService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SerializeUtil;
import com.fh.util.Tools;

import oracle.net.aso.l;

/**
 * 类名称：WIZTALKConfigController 创建人：Arial 创建时间：2017-08-24
 */
@Controller
@RequestMapping(value = "/wiztalkconfig")
public class WIZTALKConfigController extends BaseController {

	String menuUrl = "wiztalkconfig/list.do"; // 菜单地址(权限用)
	@Resource(name = "wiztalkconfigService")
	private WIZTALKConfigService wiztalkconfigService;
	@Resource(name = "redisUtilService")
	private IRedisUtilService redisUtilService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/listSys")
	public ModelAndView listSys(Page page) {
		logBefore(logger, "列表WIZTALKConfig");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
			String value = redisUtilService.get("wiztalk_sys_config");
			List<SysConfig> varList = JSONObject.parseArray(value, SysConfig.class);
			mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_listSys");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/saveSys")
	public ModelAndView saveSys(HttpServletRequest request) throws Exception {
		logBefore(logger, "新增WIZTALKConfig");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		// //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String field = pd.getString("field");
		String value = pd.getString("value");
		SysConfig sysConfig = new SysConfig(field, value);
		String sysValue = redisUtilService.get("wiztalk_sys_config");
		List<SysConfig> syslist = JSONObject.parseArray(sysValue, SysConfig.class);

		syslist.add(sysConfig);
		String jString = JSON.toJSONString(syslist);
		redisUtilService.set("wiztalk_sys_config", jString);

		mv.addObject("msg", "success");
		String valueList = redisUtilService.get("wiztalk_sys_config");
		List<SysConfig> varList = JSONObject.parseArray(valueList, SysConfig.class);
		mv.addObject("varList", varList);
		mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_listSys");
		return mv;
	}
	
	@RequestMapping(value = "/deleteConfig")
	public ModelAndView deleteConfig(PrintWriter out) {
		logBefore(logger, "删除WIZTALKConfig");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String field = pd.getString("field");
			String sysValue = redisUtilService.get("wiztalk_sys_config");
			List<SysConfig> syslist = JSONObject.parseArray(sysValue, SysConfig.class);
			for (SysConfig sysConfig2 : syslist) {
				String field2=sysConfig2.getField();
				if (field2.equals(field)) {
					syslist.remove(sysConfig2);
					break;
				}
			}
			
			String jString = JSON.toJSONString(syslist);
			redisUtilService.set("wiztalk_sys_config", jString);

			mv.addObject("msg", "success");
			String valueList = redisUtilService.get("wiztalk_sys_config");
			List<SysConfig> varList = JSONObject.parseArray(valueList, SysConfig.class);
			mv.addObject("varList", varList);
			mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_listSys");
			
		} catch (Exception e) {
			out.write("删除失败！");
			logger.error(e.toString(), e);
		}
		return mv;

	}
	/**
	 * 新增
	 */
	@RequestMapping(value = "/editConfig")
	public ModelAndView editConfig(HttpServletRequest request) throws Exception {
		logBefore(logger, "新增WIZTALKConfig");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		// //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String field = pd.getString("field");
		String value = pd.getString("value");
		SysConfig sysConfig = new SysConfig(field, value);
		String sysValue = redisUtilService.get("wiztalk_sys_config");
		List<SysConfig> syslist = JSONObject.parseArray(sysValue, SysConfig.class);
		for (SysConfig sysConfig2 : syslist) {
			String field2=sysConfig2.getField();
			if (field2.equals(field)) {
				syslist.remove(sysConfig2);
			}
		}
		syslist.add(sysConfig);
		String jString = JSON.toJSONString(syslist);
		redisUtilService.set("wiztalk_sys_config", jString);

		mv.addObject("msg", "success");
		String valueList = redisUtilService.get("wiztalk_sys_config");
		List<SysConfig> varList = JSONObject.parseArray(valueList, SysConfig.class);
		mv.addObject("varList", varList);
		mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_listSys");
		return mv;
	}
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/saveSynconfig")
	public String saveSynconfig(HttpServletRequest request) throws Exception {
		logBefore(logger, "新增同步配置");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String appId = request.getParameter("appId");
		String userId = request.getParameter("userId");
		String domain = request.getParameter("domain");
		String port = request.getParameter("port");
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("eascPwd");

		List<String> list = new ArrayList<>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", appId);
		map.put("domain", domain);
		map.put("port", port);
		map.put("userName", userName);
		map.put("pwd", pwd);
		map.put("tenantId", request.getParameter("tenantId"));
		map.put("userId", userId);
		redisUtilService.hmset("WZTK_easc_eascParam_map", map);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return "redirect:/wiztalkconfig/setSync";
	}

	/**
	 * 去同步页面
	 */
	@RequestMapping(value = "/setSync")
	public ModelAndView setSyncEasc() {
		logBefore(logger, "去设置同步页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			EascParam eascParam = new EascParam();
			if (redisUtilService.exists("WZTK_easc_eascParam_map")) {
				List<String> rsmap = redisUtilService.hmget("WZTK_easc_eascParam_map", "appId", "domain", "port",
						"userName", "pwd", "tenantId", "userId");
				eascParam = new EascParam(rsmap.get(0), rsmap.get(1), rsmap.get(2), rsmap.get(3), rsmap.get(4),
						rsmap.get(5), rsmap.get(6));
			}

			mv.setViewName("wiztalk/wiztalkconfig/setSync");
			mv.addObject("eascParam", eascParam);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去同步页面
	 */
	@RequestMapping(value = "/appConfig")
	public ModelAndView sync() {
		logBefore(logger, "去设置接口页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String OPEN_CLOSE_CHAT_KEY = redisUtilService.get("open_close_chat_key");
			String OPEN_CLOSE_SKIN_KEY = redisUtilService.get("open_close_skin_key");
			String WZTK_UPLOAD_PLUG_PATH = redisUtilService.get("WZTK_UPLOAD_PLUG_PATH");
			String WZTK_UPLOAD_FILE_PATH = redisUtilService.get("WZTK_UPLOAD_FILE_PATH");
			String WZTK_LOGIN_TIMES_TIMESLIMTS = redisUtilService.get("WZTK_LOGIN_TIMES_TIMESLIMTS");
			String WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE = redisUtilService.get("WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE");
			if (Tools.notEmpty(OPEN_CLOSE_CHAT_KEY)) {
				pd.put("OPEN_CLOSE_CHAT_KEY", OPEN_CLOSE_CHAT_KEY);

			}
			if (Tools.notEmpty(OPEN_CLOSE_SKIN_KEY)) {
				pd.put("OPEN_CLOSE_SKIN_KEY", OPEN_CLOSE_SKIN_KEY);

			}
			if (Tools.notEmpty(WZTK_UPLOAD_PLUG_PATH)) {
				pd.put("WZTK_UPLOAD_PLUG_PATH", WZTK_UPLOAD_PLUG_PATH);

			}
			if (Tools.notEmpty(WZTK_UPLOAD_FILE_PATH)) {
				pd.put("WZTK_UPLOAD_FILE_PATH", WZTK_UPLOAD_FILE_PATH);

			}
			if (Tools.notEmpty(WZTK_LOGIN_TIMES_TIMESLIMTS)) {
				pd.put("WZTK_LOGIN_TIMES_TIMESLIMTS", WZTK_LOGIN_TIMES_TIMESLIMTS);

			}
			if (Tools.notEmpty(WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE)) {
				pd.put("WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE", WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE);

			}
			mv.setViewName("wiztalk/wiztalkconfig/appConfig");

			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/saveConfig")
	public String saveConfig(HttpServletRequest request) throws Exception {
		logBefore(logger, "新增WIZTALKConfig");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String OPEN_CLOSE_CHAT_KEY = request.getParameter("OPEN_CLOSE_CHAT_KEY");
		String OPEN_CLOSE_SKIN_KEY = request.getParameter("OPEN_CLOSE_SKIN_KEY");
		String WZTK_UPLOAD_PLUG_PATH = request.getParameter("WZTK_UPLOAD_PLUG_PATH");
		String WZTK_UPLOAD_FILE_PATH = request.getParameter("WZTK_UPLOAD_FILE_PATH");
		String WZTK_LOGIN_TIMES_TIMESLIMTS = request.getParameter("WZTK_LOGIN_TIMES_TIMESLIMTS");
		String WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE = request.getParameter("WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE");
		if (Tools.notEmpty(OPEN_CLOSE_CHAT_KEY)) {
			redisUtilService.set("open_close_chat_key", OPEN_CLOSE_CHAT_KEY);

		}
		if (Tools.notEmpty(OPEN_CLOSE_SKIN_KEY)) {
			redisUtilService.set("open_close_skin_key", OPEN_CLOSE_SKIN_KEY);

		}
		if (Tools.notEmpty(WZTK_UPLOAD_PLUG_PATH)) {
			redisUtilService.set("WZTK_UPLOAD_PLUG_PATH", WZTK_UPLOAD_PLUG_PATH);

		}
		if (Tools.notEmpty(WZTK_UPLOAD_FILE_PATH)) {
			redisUtilService.set("WZTK_UPLOAD_FILE_PATH", WZTK_UPLOAD_FILE_PATH);

		}
		if (Tools.notEmpty(WZTK_LOGIN_TIMES_TIMESLIMTS)) {
			redisUtilService.set("WZTK_LOGIN_TIMES_TIMESLIMTS", WZTK_LOGIN_TIMES_TIMESLIMTS);

		}
		if (Tools.notEmpty(WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE)) {
			redisUtilService.set("WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE", WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE);

		}
		mv.addObject("msg", "保存成功！");
		// mv.setViewName("save_result_alert");

		// return mv;
		return "redirect:/wiztalkconfig/appConfig";
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增WIZTALKConfig");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WIZTALKCONFIG_ID", this.get32UUID()); // 主键
		wiztalkconfigService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	//
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		logBefore(logger, "删除WIZTALKConfig");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			wiztalkconfigService.delete(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "修改WIZTALKConfig");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		wiztalkconfigService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "列表WIZTALKConfig");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> varList = wiztalkconfigService.list(page); // 列出WIZTALKConfig列表
			mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAddConfig")
	public ModelAndView goAddConfig() {
		logBefore(logger, "去新增WIZTALKConfig页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/wiztalkconfig/config_edit");
			mv.addObject("msg", "saveSys");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/goEditConfig")
	public ModelAndView goEditConfig() {
		logBefore(logger, "去修改WIZTALKConfig页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String field=pd.getString("field");
			mv.setViewName("wiztalk/wiztalkconfig/config_edit");
			mv.addObject("msg", "editConfig");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
		logBefore(logger, "去新增WIZTALKConfig页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_edit");
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
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() {
		logBefore(logger, "去修改WIZTALKConfig页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = wiztalkconfigService.findById(pd); // 根据ID读取
			mv.setViewName("wiztalk/wiztalkconfig/wiztalkconfig_edit");
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
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除WIZTALKConfig");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				wiztalkconfigService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			} else {
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
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		logBefore(logger, "导出WIZTALKConfig到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("应用名"); // 1
			titles.add("应用服务器"); // 2
			titles.add("聊天服务器"); // 3
			titles.add("文件服务器"); // 4
			titles.add("HOST_PORTIN"); // 5
			titles.add("SIGN_PORTIN"); // 6
			titles.add("REQUST_PORTIN"); // 7
			titles.add("内外网"); // 8
			titles.add("目标代码"); // 9
			titles.add("点巡检URL"); // 10
			dataMap.put("titles", titles);
			List<PageData> varOList = wiztalkconfigService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("APPNAME")); // 1
				vpd.put("var2", varOList.get(i).getString("XMPP_HOST_NAME")); // 2
				vpd.put("var3", varOList.get(i).getString("XMPP_HOST_IP")); // 3
				vpd.put("var4", varOList.get(i).getString("XMPP_FILE_SERVER_IP")); // 4
				vpd.put("var5", varOList.get(i).getString("XMPP_HOST_PORTIN")); // 5
				vpd.put("var6", varOList.get(i).getString("XMPP_SIGN_PORTIN")); // 6
				vpd.put("var7", varOList.get(i).getString("XMPP_REQUST_PORTIN")); // 7
				vpd.put("var8", varOList.get(i).getString("TYPE")); // 8
				vpd.put("var9", varOList.get(i).get("TARGET").toString()); // 9
				vpd.put("var10", varOList.get(i).getString("POINTINSPECTION_URL")); // 10
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv, dataMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
