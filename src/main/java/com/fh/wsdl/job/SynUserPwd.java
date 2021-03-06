package com.fh.wsdl.job;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.fh.wsdl.service.SyncDbService;


public class SynUserPwd extends QuartzJobBean {
   
   
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			WebApplicationContext context1 = ContextLoader.getCurrentWebApplicationContext();
			SyncDbService syncDbService = (SyncDbService) context1.getBean("syncDbService");
			String type="自动";
			syncDbService.syncdbResult(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
