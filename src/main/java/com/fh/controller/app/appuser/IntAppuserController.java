package com.fh.controller.app.appuser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.system.appuser.AppuserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.Tools;


/**
  * 会员-接口类 
  *    
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appuser")
public class IntAppuserController extends BaseController {
    
	@Resource(name="appuserService")
	private AppuserService appuserService;
	
	@Value("${server.code}")  
	private String serverCode; 
	
	/**
	 * 根据用户名获取会员信息
	 */
	@RequestMapping(value="/getAppuserByUm")
	@ResponseBody
	public Object getAppuserByUsernmae(){
		logBefore(logger, "根据用户名获取会员信息");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		
		try{
			if(Tools.checkKey("USERNAME", pd.getString("FKEY"))){	//检验请求key值是否合法
				if(AppUtil.checkParam("getAppuserByUsernmae", pd)){	//检查参数
					pd = appuserService.findByUId(pd);
					
					map.put("pd", pd);
					result = (null == pd) ?  "02" :  "01";
					
				}else {
					result = "03";
				}
			}else{
				result = "05";
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@RequestMapping(value="/getCode")
	@ResponseBody
	public ModelAndView getCode(){
		logBefore(logger, "根据用户名获取授权码");
		ModelAndView mv = this.getModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("wiztalk/client/code");
		String code = "";
		String name =pd.getString("name");
		
		if (!StringUtils.isEmpty(name)) {
			code=this.EncoderData(serverCode);
			mv.addObject("code",code);
			return mv;
		}else {
			return mv;
		}
		
		
		
	}
	
	private static String EncoderData(String code) {
		String v=code;
		Random r=new Random(System.currentTimeMillis()); 
		  int index=r.nextInt(2);
		  String[] dataList=new String[3];
		  for(int i=0;i<2;i++) {
			  if(index==i) {
				  dataList[i]=v;

			  }
			  else {
				  dataList[i]=r.nextInt(90)+10+"";

			  }
		  }
		  dataList[2]="0"+index;
		  String value="";
		  for(int i=0;i<dataList.length;i++) {
			  value+=dataList[i];
		  }
		  return value;
	}

	
	public static void main(String[] args) {
		//String  aa = IntAppuserController.EncoderData("01");
		Random r=new Random(System.currentTimeMillis()); 
		String cc =r.nextInt(100)+"";
		System.err.println(cc);
	}
}
	
 