package com.fh.controller.wiztalk.publish;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

@Controller
@RequestMapping("/publish")
public class PublishController extends BaseController{
	
	/**
	 * 去新增页面
	 */
	public ModelAndView goAdd(){
		logBefore(logger, "去下载页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("wiztalk/publish/download");
			
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	

}
