package com.fh.wsdl.job;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.fh.wsdl.service.SyncDbService;


public class SynUser extends QuartzJobBean {
    
   
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			SyncDbService syncDbService = new SyncDbService();
			WebApplicationContext context1 = ContextLoader.getCurrentWebApplicationContext();
			 syncDbService = (SyncDbService) context1.getBean("syncDbService");
			String type="自动";
			syncDbService.syncdbResult(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
