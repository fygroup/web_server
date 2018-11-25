/**
 * Copyright &copy; 2015-2020 <a href="http://www.clutek.org/">clutek</a> All rights reserved.
 */
package com.jeeplus.modules.patrol.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.patrol.entity.Patrol;
import com.jeeplus.modules.monitor.mapper.ScheduleJobMapper;
import com.jeeplus.modules.patrol.mapper.PatrolMapper;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 智能巡检任务 Service
 * @author huanglei
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class PatrolService extends CrudService<PatrolMapper, Patrol> {
	

	@Autowired
	private Scheduler scheduler;

	public Patrol get(String id) {
		return super.get(id);
	}
	
	public List<Patrol> findList(Patrol patrol) {
		return super.findList(patrol);
	}
	
	public Page<Patrol> findPage(Page<Patrol> page, Patrol patrol) {
		return super.findPage(page, patrol);
	}
	
	@Transactional(readOnly = false)
	public void save(Patrol patrol) {
		if (!patrol.getIsNewRecord()) {
			Patrol t = this.get(patrol.getId());
			JobKey key = new JobKey(t.getName(), t.getGroup());
			try {
				scheduler.deleteJob(key);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		this.add(patrol);
		super.save(patrol);
	}
	
	
	@Transactional(readOnly = false)
	private void add(Patrol patrol){
		Class job = null;
		try {
			job = Class.forName(patrol.getClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(patrol.getName(), patrol.getGroup())
				.build();
		jobDetail.getJobDataMap().put("patrol", patrol);

		// 表达式调度构建器（可判断创建SimpleScheduleBuilder）
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patrol.getCronExpression());

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(patrol.getName(), patrol.getGroup()).withSchedule(scheduleBuilder).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			JobKey key = new JobKey(patrol.getName(), patrol.getGroup());
			if(patrol.getStatus().equals("0")){
				scheduler.pauseJob(key);
 			}else{
				scheduler.resumeJob(key);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	
	}
	
	@Transactional(readOnly = false)
	public void delete(Patrol patrol) {

		JobKey key = new JobKey(patrol.getName(), patrol.getGroup());
		try {
			scheduler.deleteJob(key);
			super.delete(patrol);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	/**
	 * 获取所有JobDetail
	 * @return 结果集合
	 */
	public List<JobDetail> getJobs() {
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			List<JobDetail> jobDetails = new ArrayList<JobDetail>();
			for (JobKey key : jobKeys) {
				jobDetails.add(scheduler.getJobDetail(key));
			}
			return jobDetails;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有计划中的任务
	 * @return 结果集合
	 */
	public List<Patrol> getAllScheduleJob(){
		List<Patrol> scheduleJobList=new ArrayList<Patrol>();;
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		try {
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
			    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			    for (Trigger trigger : triggers) {
					Patrol patrol = new Patrol();
					patrol.setName(jobKey.getName());
					patrol.setGroup(jobKey.getGroup());
			        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					patrol.setStatus(triggerState.name());
			        //获取要执行的定时任务类名
			        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
					patrol.setClassName(jobDetail.getJobClass().getName());
				    //判断trigger
				    if (trigger instanceof SimpleTrigger) {
						SimpleTrigger simple = (SimpleTrigger) trigger;
						patrol.setCronExpression("重复次数:"+ (simple.getRepeatCount() == -1 ?
								"无限" : simple.getRepeatCount()) +",重复间隔:"+(simple.getRepeatInterval()/1000L));
						patrol.setDescription(simple.getDescription());
					}
					if (trigger instanceof CronTrigger) {
						CronTrigger cron = (CronTrigger) trigger;
						patrol.setCronExpression(cron.getCronExpression());
						patrol.setDescription(cron.getDescription()==null?("触发器:" + trigger.getKey()):cron.getDescription());
					}
			        scheduleJobList.add(patrol);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleJobList;
	}
	
	/**
	 * 获取所有运行中的任务
	 * @return 结果集合
	 */
	public List<Patrol> getAllRuningScheduleJob(){
		List<Patrol> scheduleJobList=null;
		try {
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			scheduleJobList = new ArrayList<Patrol>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				Patrol patrol = new Patrol();
			    JobDetail jobDetail = executingJob.getJobDetail();
			    JobKey jobKey = jobDetail.getKey();
			    Trigger trigger = executingJob.getTrigger();
			    patrol.setName(jobKey.getName());
				patrol.setGroup(jobKey.getGroup());
			    //scheduleJob.setDescription("触发器:" + trigger.getKey());
			    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				patrol.setStatus(triggerState.name());
			    //获取要执行的定时任务类名
				patrol.setClassName(jobDetail.getJobClass().getName());
			    //判断trigger
			    if (trigger instanceof SimpleTrigger) {
					SimpleTrigger simple = (SimpleTrigger) trigger;
					patrol.setCronExpression("重复次数:"+ (simple.getRepeatCount() == -1 ?
							"无限" : simple.getRepeatCount()) +",重复间隔:"+(simple.getRepeatInterval()/1000L));
					patrol.setDescription(simple.getDescription());
				}
				if (trigger instanceof CronTrigger) {
					CronTrigger cron = (CronTrigger) trigger;
					patrol.setCronExpression(cron.getCronExpression());
					patrol.setDescription(cron.getDescription());
				}
			    scheduleJobList.add(patrol);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return scheduleJobList;
	}
	
	/**
	 * 获取所有的触发器
	 * @return 结果集合
	 */
	public List<Patrol> getTriggersInfo(){
		try {
			GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();
			Set<TriggerKey> Keys = scheduler.getTriggerKeys(matcher);
			List<Patrol> triggers = new ArrayList<Patrol>();
			
			for (TriggerKey key : Keys) {
				Trigger trigger = scheduler.getTrigger(key);
				Patrol patrol = new Patrol();
				patrol.setName(trigger.getJobKey().getName());
				patrol.setGroup(trigger.getJobKey().getGroup());
				patrol.setStatus(scheduler.getTriggerState(key)+"");
				if (trigger instanceof SimpleTrigger) {
					SimpleTrigger simple = (SimpleTrigger) trigger;
					patrol.setCronExpression("重复次数:"+ (simple.getRepeatCount() == -1 ?
							"无限" : simple.getRepeatCount()) +",重复间隔:"+(simple.getRepeatInterval()/1000L));
					patrol.setDescription(simple.getDescription());
				}
				if (trigger instanceof CronTrigger) {
					CronTrigger cron = (CronTrigger) trigger;
					patrol.setCronExpression(cron.getCronExpression());
					patrol.setDescription(cron.getDescription());
				}
				triggers.add(patrol);
			}
			return triggers;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 暂停任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	@Transactional(readOnly = false)
	public void stopJob(Patrol patrol){
		
		JobKey key = new JobKey(patrol.getName(), patrol.getGroup());
		try {
			scheduler.pauseJob(key);
			patrol.setStatus("0");
			super.save(patrol);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 恢复任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	@Transactional(readOnly = false)
	public void restartJob(Patrol patrol){
		JobKey key = new JobKey(patrol.getName(), patrol.getGroup());
		try {
			scheduler.resumeJob(key);
			patrol.setStatus("1");
			super.save(patrol);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 立马执行一次任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	@Transactional(readOnly = false)
	public void startNowJob(Patrol patrol){
		JobKey key = new JobKey(patrol.getName(), patrol.getGroup());
		try {
			scheduler.triggerJob(key);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 修改触发器时间
	 * @param name 任务名
	 * @param group 任务组
	 * @param cron cron表达式
	 */
	@Transactional(readOnly = false)
	public void modifyTrigger(String name,String group,String cron){
		try {  
            TriggerKey key = TriggerKey.triggerKey(name, group);  
            //Trigger trigger = scheduler.getTrigger(key);  
              
            CronTrigger newTrigger = (CronTrigger) TriggerBuilder.newTrigger()  
                    .withIdentity(key)  
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))  
                    .build();  
            scheduler.rescheduleJob(key, newTrigger);  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
		
	}
	
	/**
	 * 暂停调度器
	 */
	@Transactional(readOnly = false)
	public void stopScheduler(){
		 try {
			if (!scheduler.isInStandbyMode()) {
				scheduler.standby();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	
}