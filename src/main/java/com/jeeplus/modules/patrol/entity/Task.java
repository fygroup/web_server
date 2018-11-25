package com.jeeplus.modules.patrol.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Bean;

import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;

/**
 * 巡检任务工作类
 * @author   huanglei
 * @version 2018/11/12
 * @email 616754909@qq.com
 */
@DisallowConcurrentExecution  
public abstract class Task implements Job {
	 
	/**
	 * 系统通知bean
	 * @return
	 */
	@Bean
	public SystemInfoSocketHandler systemInfoSocketHandler() {
	        return new SystemInfoSocketHandler();
	}
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Patrol patrol = (Patrol)context.getMergedJobDataMap().get("patrol");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
        
        if(patrol.getIsInfo().equals("1")){
        	
        	systemInfoSocketHandler().sendMessageToUser("admin", "任务名称 = [" + patrol.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行");
        }else if(patrol.getIsInfo().equals("2")){
        	
        	systemInfoSocketHandler().sendMessageToAllUsers("任务名称 = [" + patrol.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行");
        }




        String id = patrol.getId();
        //巡检类型 i=patrol.getCheckType()=0,1,2 服务器、网络设备、安全设备
		// 巡检内容 j=patrol.getCheckContent()=[0,1,2] 通断、CPU利用率、内存利用率
        run(patrol.getCheckType(),patrol.getCheckContent(),patrol.getId());
       
	    //System.out.println("任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行");
    }

	public abstract void run(String i,String j,String patrolId);
}
