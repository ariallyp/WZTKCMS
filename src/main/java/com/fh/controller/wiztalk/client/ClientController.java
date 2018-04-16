package com.fh.controller.wiztalk.client;

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
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.DateIntervalTrigger;
import org.quartz.DateIntervalTrigger.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.wsdl.job.SynUser;
import com.fh.wsdl.service.SyncDbService;
import com.fh.util.Jurisdiction;
import com.fh.service.redis.IRedisUtilService;
import com.fh.service.wiztalk.client.ClientService;

/**
 * 类名称：ClientController 创建人：Arial 创建时间：2017-08-14
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController extends BaseController {

	String menuUrl = "client/list.do"; // 菜单地址(权限用)
	@Resource(name = "clientService")
	private ClientService clientService;
	@Resource(name = "syncDbService")
	private SyncDbService syncDbService;
	@Resource(name="redisUtilService")
	private IRedisUtilService redisUtilService;
	
	

	
	/**
	 * 去同步页面
	 */
	@RequestMapping(value = "/sync")
	public ModelAndView sync() {
		logBefore(logger, "去同步页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String WZTK_SYNC_MODEL=redisUtilService.get("WZTK_SYNC_MODEL");
			String WZTK_SYNC_TIME =redisUtilService.get("WZTK_SYNC_TIME");
			String SYNC_WZTK_PWD = redisUtilService.get("SYNC_WZTK_PWD");
			if (Tools.notEmpty(WZTK_SYNC_MODEL)) {
				pd.put("WZTK_SYNC_MODEL", WZTK_SYNC_MODEL);

			}
			if (Tools.notEmpty(WZTK_SYNC_TIME)) {
				pd.put("WZTK_SYNC_TIME", WZTK_SYNC_TIME);

			}
			if (Tools.notEmpty(SYNC_WZTK_PWD)) {
                pd.put("SYNC_WZTK_PWD", SYNC_WZTK_PWD);

            }
			mv.setViewName("wiztalk/client/clinet_sync_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	
	

	/**
	 * 及时同步
	 */
	@RequestMapping(value = "/start_sync")
	@ResponseBody
	public Map<String, String> clinetSync() {
		logBefore(logger, "同步Client");
		 // 校验权限
		StringBuffer result = new StringBuffer();
		Map<String, String> resultMap = new HashMap<String, String>();
		String message="";
		String type="手动";
		try {
			long beginMillis = System.currentTimeMillis();
			message =syncDbService.syncdbResult(type);
			long endMillis = System.currentTimeMillis();
			logger.info("调用数据库同步syncDbService.syncdb()方法一共用时：" + (endMillis - beginMillis) + "毫秒");
			
			//result.append(" 数据库同步一共用时：" + (endMillis - beginMillis) + "毫秒");
		
			resultMap.put("message", message+result.toString());

		} catch (Exception e) {

			logger.error(e.toString(), e);
			
			resultMap.put("message", e.toString());
		}

		return resultMap;
	}
/*	*//**
	 * 去同步页面
	 *//*
	@RequestMapping(value = "/result")
	public ModelAndView result() {
		logBefore(logger, "去同步页面");
		ModelAndView mv = this.getModelAndView();
		
		
			mv.setViewName("wiztalk/client/save_result");
			mv.addObject("msg", "edit");
		
		return mv;
	}*/
	
	
	
	@RequestMapping(value = "/hb", method = { RequestMethod.POST })
	public String sycnhb(HttpServletRequest request) throws Exception {
		String WZTK_SYNC_MODEL="WZTK_SYNC_MODEL";
		String WZTK_SYNC_TIME ="WZTK_SYNC_TIME";
		String HH = "00";
        String MM = "00";
        String SS = "00";
        String dayString ="00:00:00";
		if ("1".equals(request.getParameter("synp"))) {
			redisUtilService.set(WZTK_SYNC_MODEL, "周期同步");
			if ("d".equals(request.getParameter("synt"))) {
				
				dayString = request.getParameter("everyDay");
				if (Tools.notEmpty(dayString)) {
                    HH = dayString.split(":")[0];
                    MM = dayString.split(":")[1];
                    SS = dayString.split(":")[2]; 
                }
				StringBuffer sb = new StringBuffer();
				StringBuffer sbRedis = new StringBuffer();
				sb.append(SS + " ").append(MM + " ").append(HH + " * * ?");
				System.out.println(sb.toString());
				timedb(sb.toString());
				sbRedis.append("同步时间 : 每天 ").append(HH+":").append(MM+":").append(SS);
				redisUtilService.set(WZTK_SYNC_TIME, sbRedis.toString());
			} else if ("w".equals(request.getParameter("synt"))) {
				String w = request.getParameter("week");
				dayString = request.getParameter("weekDay");
				if (Tools.notEmpty(dayString)) {
                    HH = dayString.split(":")[0];
                    MM = dayString.split(":")[1];
                    SS = dayString.split(":")[2]; 
                }
				StringBuffer sb = new StringBuffer();
				StringBuffer sbRedis = new StringBuffer();
				sb.append(SS + " ").append(MM + " ").append(HH + " ? *  " + w);
				System.out.println(sb.toString());
				timedb(sb.toString());
				sbRedis.append("同步时间 : 每周 ").append(w+"  ").append(HH+":").append(MM+":").append(SS);
				String syncTime=request.getParameter("syncTime2");
				redisUtilService.set(WZTK_SYNC_TIME, sbRedis.toString());
			} else if ("y".equals(request.getParameter("synt"))) { 
				String mm = request.getParameter("month");
				dayString = request.getParameter("monthDay");
				if (Tools.notEmpty(dayString)) {
				    HH = dayString.split(":")[0];
	                MM = dayString.split(":")[1];
	                SS = dayString.split(":")[2]; 
                }
               
				StringBuffer sb = new StringBuffer();
				StringBuffer sbRedis = new StringBuffer();
				sb.append(SS + " ").append(MM + " ").append(HH + " " + mm + " * ?");
				System.out.println(sb.toString());
				timedb(sb.toString());
				sbRedis.append("同步时间 : 每月 ").append(mm+" 日  ").append(HH+":").append(MM+":").append(SS);
				redisUtilService.set(WZTK_SYNC_TIME, sbRedis.toString());
				
			}
		} else if ("2".equals(request.getParameter("synp"))) {
			redisUtilService.set(WZTK_SYNC_MODEL, "间隔性同步");
			String j = request.getParameter("jg");
			dayString = request.getParameter("jgDay");
			if (Tools.notEmpty(dayString)) {
                HH = dayString.split(":")[0];
                MM = dayString.split(":")[1];
                SS = dayString.split(":")[2]; 
            }
			
			StringBuffer sb = new StringBuffer();
			StringBuffer sbRedis = new StringBuffer();
			//sb.append(SS + " ").append(MM + " ").append(HH + " ? * *");
			//sb.append(SS+" ").append(MM+" ").append(HH+"/"+j+" * * * ?");
			sb.append("0 */"+j+" * * * ?");
			sbRedis.append("间隔时间为： ").append(j).append(" 分钟");
			System.out.println(sb.toString());
			timedb2(j);
			redisUtilService.set("SYNC_WZTK_PWD", request.getParameter("SYNC_WZTK_PWD"));
			redisUtilService.set(WZTK_SYNC_TIME, sbRedis.toString());
			

		} else {
			timedb("0 0 0 * * ?");
			
			redisUtilService.set(WZTK_SYNC_TIME, "每天十二点整");
		}

		return "redirect:/client/sync";
	}

	private void timedb(String str) throws Exception {

		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		try {

			scheduler.pauseTrigger("trigger", "ctrigger");
			scheduler.unscheduleJob("trigger", "ctrigger");
			scheduler.deleteJob("synUser", "group1");

			JobDetail jobDetail = new JobDetail("synUser", "group1", SynUser.class);

			CronTrigger trigger = new CronTrigger("trigger", "ctrigger");

			CronExpression cronExpression = new CronExpression(str);

			trigger.setCronExpression(cronExpression);

			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.start();

		} catch (SchedulerException e) {

			e.printStackTrace();
		}
	}
	
	private void timedb2(String str) throws Exception {
	    int length = Integer.parseInt(str);
	    SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        try {

            scheduler.pauseTrigger("trigger", "ctrigger");
            scheduler.unscheduleJob("trigger", "ctrigger");
            scheduler.deleteJob("synUser", "group1");

            JobDetail jobDetail = new JobDetail("synUser", "group1", SynUser.class);

            DateIntervalTrigger trigger = new DateIntervalTrigger("trigger", "ctrigger", IntervalUnit.MINUTE, length);


            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();

        } catch (SchedulerException e) {

            e.printStackTrace();
        }
    }

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增Client");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CLIENT_ID", this.get32UUID()); // 主键
		pd.put("DEVICE_ID", ""); // device_id
		pd.put("LATEST_LOGIN_TIME", Tools.date2Str(new Date())); // latest_login_time
		pd.put("CREATED", Tools.date2Str(new Date())); // created
		pd.put("UPDATED", Tools.date2Str(new Date())); // updated
		clientService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		logBefore(logger, "删除Client");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			clientService.delete(pd);
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
		logBefore(logger, "修改Client");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATED", Tools.date2Str(new Date())); // updated
		clientService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "列表Client");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String NAME = pd.getString("NAME");
			if (!Tools.isEmpty(NAME)) {
				NAME = NAME.trim();
				pd.put("NAME", NAME);
			}
			String lastLoginEnd = pd.getString("lastLoginEnd");
			if (!Tools.isEmpty(lastLoginEnd)) {
				lastLoginEnd = lastLoginEnd +" 23:59:59";
				pd.put("lastLoginEnd", lastLoginEnd);
			}
			page.setPd(pd);
			List<PageData> varList = clientService.list(page); // 列出Client列表
			mv.setViewName("wiztalk/client/client_list");
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
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
		logBefore(logger, "去新增Client页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/client/client_edit");
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
		logBefore(logger, "去修改Client页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = clientService.findById(pd); // 根据ID读取
			mv.setViewName("wiztalk/client/client_edit");
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
		logBefore(logger, "批量删除Client");
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
				clientService.deleteAll(ArrayDATA_IDS);
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

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/editBatch")
	@ResponseBody
	public Object editBatch() {
		logBefore(logger, "批量绑定");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		String msg="";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for (int i = 0; i < ArrayDATA_IDS.length; i++) {
					String CLIENT_ID = ArrayDATA_IDS[i];
					pd.put("CLIENT_ID", CLIENT_ID);
					clientService.edit(pd);
				}
				pd.put("msg", "ok");
				msg="ok";
			} else {
				pd.put("msg", "no");
				msg="no";
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		map.put("message", msg);
		return AppUtil.returnObject(pd, map);
	}

	/*
	 * 导出到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		logBefore(logger, "导出Client到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户名"); // 1
			titles.add("类型"); // 2
			titles.add("device_id"); // 3
			titles.add("设备名"); // 4
			titles.add("绑定状态"); // 5
			titles.add("latest_login_time"); // 6
			titles.add("created"); // 7
			titles.add("updated"); // 8
			dataMap.put("titles", titles);
			List<PageData> varOList = clientService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("USER_ID")); // 1
				vpd.put("var2", varOList.get(i).getString("TYPE")); // 2
				vpd.put("var3", varOList.get(i).getString("DEVICE_ID")); // 3
				vpd.put("var4", varOList.get(i).getString("DEVICE_NAME")); // 4
				vpd.put("var5", varOList.get(i).getString("BINDSTATUS")); // 5
				vpd.put("var6", varOList.get(i).getString("LATEST_LOGIN_TIME")); // 6
				vpd.put("var7", varOList.get(i).getString("CREATED")); // 7
				vpd.put("var8", varOList.get(i).getString("UPDATED")); // 8
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
