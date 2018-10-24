/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 进出记录Entity
 * @author huanglei
 * @version 2017-11-10
 */
public class OaOutRecords extends DataEntity<OaOutRecords> {
	
	private static final long serialVersionUID = 1L;
	private String processInstanceId;		// 流程实例编号
	private String reason;		// 进入事由
	private String units;		// 部门单位
	private String name;		// 人员名称
	private Date entryTime;		// 进入时间
	private Date departureTime;		// 离开时间
	private Date applyTime;		// 申请时间
	private Date realityEntryTime;		// 实际进入时间
	private Date realityDepartureTime;		// 实际离开时间



	private Date entryTimeStart;//查询进入开始时间
	private Date entryTimeEnd; //查询进入结束时间
	
	public OaOutRecords() {
		super();
	}

	public OaOutRecords(String id){
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
}