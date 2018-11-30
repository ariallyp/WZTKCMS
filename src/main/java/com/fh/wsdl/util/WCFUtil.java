package com.fh.wsdl.util;

import java.util.List;


import com.fh.wsdl.entity.SeOrganization;
import com.fh.wsdl.entity.SeUser;
import com.fh.wsdl.entity.SeUserOrganization;
import com.fh.wsdl.wcf.AppRequest;
import com.fh.wsdl.wcf.AppResponse;
import com.fh.wsdl.wcf.IApplicationService;
import com.fh.wsdl.wcf.ObjectFactory;
import com.fh.wsdl.wcf.WcfApplicationService;




public class WCFUtil {/*

	
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	*//**
	 * 获取wcf接口中SeOrganization表中的数据
	 * @return List<SeOrganization>
	 * @throws Exception 
	 * @throws JsonParseException 
	 *//*
	public static List<SeOrganization>getSeOrganizations(String appId,String methodName) throws  Exception{
		ObjectFactory objectFactory=new ObjectFactory();
		WcfApplicationService wcfApplicationService=new WcfApplicationService();
		IApplicationService iApplicationService=wcfApplicationService.getBasicHttpBindingIApplicationService();
		
		AppRequest request=objectFactory.createAppRequest();
		request.setAppId(objectFactory.createAppRequestAppId(appId));
		request.setData(null);
		request.setMethodName(objectFactory.createAppRequestMethodName(methodName));
		
		AppResponse appResponse=iApplicationService.getAppinfo(request);	
		
		JsonFactory jsonFactory=new JsonFactory();
		JsonParser jsonParser=jsonFactory.createParser(appResponse.getData().getValue().toString());
		List<SeOrganization>seOrganizations=MAPPER.readValue(jsonParser
				, MAPPER.getTypeFactory().constructParametricType(List.class, SeOrganization.class));
		return seOrganizations;
	}
	
	*//**
	 * 获取wcf接口中SeUser表中的数据
	 * @return List<SeUser>
	 * @throws Exception 
	 * @throws JsonParseException 
	 *//*
	public static List<SeUser>getSeUers(String appId,String methodName) throws Exception{
		ObjectFactory objectFactory=new ObjectFactory();
		WcfApplicationService wcfApplicationService=new WcfApplicationService();
		IApplicationService iApplicationService=wcfApplicationService.getBasicHttpBindingIApplicationService();
		
		AppRequest request=objectFactory.createAppRequest();
		request.setAppId(objectFactory.createAppRequestAppId(appId));
		request.setData(null);
		request.setMethodName(objectFactory.createAppRequestMethodName(methodName));
		
		AppResponse appResponse=iApplicationService.getAppinfo(request);	
				
		JsonFactory jsonFactory=new JsonFactory();
		JsonParser jsonParser=jsonFactory.createParser(appResponse.getData().getValue().toString());
		List<SeUser>seUsers=MAPPER.readValue(jsonParser
				, MAPPER.getTypeFactory().constructParametricType(List.class, SeUser.class));
		return seUsers;
	}
	
	*//**
	 * 获取wcf接口中SeUserOrganization表中的数据
	 * @return List<SeUserOrganization>
	 * @throws Exception 
	 * @throws JsonParseException 
	 *//*
	public static List<SeUserOrganization>getSeUserOrganizations(String appId,String methodName) throws  Exception{
		ObjectFactory objectFactory=new ObjectFactory();
		WcfApplicationService wcfApplicationService=new WcfApplicationService();
		IApplicationService iApplicationService=wcfApplicationService.getBasicHttpBindingIApplicationService();
		
		AppRequest request=objectFactory.createAppRequest();
		request.setAppId(objectFactory.createAppRequestAppId(appId));
		request.setData(null);
		request.setMethodName(objectFactory.createAppRequestMethodName(methodName));
		
		AppResponse appResponse=iApplicationService.getAppinfo(request);	
		
		JsonFactory jsonFactory=new JsonFactory();
		JsonParser jsonParser=jsonFactory.createParser(appResponse.getData().getValue().toString());
		List<SeUserOrganization>seUserOrganizations=MAPPER.readValue(jsonParser
				, MAPPER.getTypeFactory().constructParametricType(List.class, SeUserOrganization.class));
		return seUserOrganizations;
	}
	
*/}
