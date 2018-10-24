/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 进出记录Entity
 * @author huanglei
 * @version 2017-11-10
 */
public class Turnover extends ActEntity<Turnover> {

	private static final long serialVersionUID = 1L;
	private String processInstanceId; // 流程实例编号
	private String reason;		// 进入事由
	private String units;		// 部门单位
	private String name;		// 人员名称
	private Date entryTime;		// 进入时间
	private Date departureTime;		// 离开时间
	private Date applyTime;		// 申请时间
	private Date realityEntryTime;		// 实际进入时间
	private Date realityDepartureTime;		// 实际离开时间


	private String ids;
	private Date entryTimeStart;//查询进入开始时间
	private Date entryTimeEnd; //查询进入结束时间
	private String comment;


	//-- 临时属性 --//
	// 流程任务
	private Task task;
	private Map<String, Object> variables;
	// 运行中的流程实例
	private ProcessInstance processInstance;
	// 历史的流程实例
	private HistoricProcessInstance historicProcessInstance;
	// 流程定义
	private ProcessDefinition processDefinition;


	public Turnover() {
		super();
	}

	public Turnover(String id){
		super(id);
	}


	@Length(min=0, max=64, message="流程实例编号长度必须介于 0 和 64 之间")
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	@Length(min=0, max=255, message="进入事由长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=255, message="部门单位长度必须介于 0 和 255 之间")
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	@Length(min=0, max=255, message="人员名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}



	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealityEntryTime() {
		return realityEntryTime;
	}

	public void setRealityEntryTime(Date realityEntryTime) {
		this.realityEntryTime = realityEntryTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealityDepartureTime() {
		return realityDepartureTime;
	}

	public void setRealityDepartureTime(Date realityDepartureTime) {
		this.realityDepartureTime = realityDepartureTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getEntryTimeStart() {
		return entryTimeStart;
	}

	public void setEntryTimeStart(Date entryTimeStart) {
		this.entryTimeStart = entryTimeStart;
	}

	public Date getEntryTimeEnd() {
		return entryTimeEnd;
	}

	public void setEntryTimeEnd(Date entryTimeEnd) {
		this.entryTimeEnd = entryTimeEnd;
	}

	public void setUser(User user) {
		this.createBy = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public String getIds() {
		List<String> idList = Lists.newArrayList();
		if (StringUtils.isNotBlank(ids)){
			String ss = ids.trim().replace("　", ",").replace(" ",",").replace("，", ",").replace("'", "");
			for(String s : ss.split(",")) {
//				if(s.matches("\\d*")) {
				idList.add("'"+s+"'");
//				}
			}
		}
		return StringUtils.join(idList, ",");
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}



}