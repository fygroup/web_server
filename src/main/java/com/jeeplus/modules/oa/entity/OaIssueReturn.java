/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 运维工单问题申报Entity
 * @author huanglei
 * @version 2017-11-14
 */
public class OaIssueReturn extends DataEntity<OaIssueReturn> {
	
	private static final long serialVersionUID = 1L;
	private String description;	// 问题描述
	private String place;		// 申报地点
	private String detailplace; // 详细地址
	private String problemstate;// 问题状态
	private String ediid;		// 申报方式
	private Date date;		    // 申报时间
	private String declaretype;	// 申报类型
	private User user;		    // 当前用户，获取用户id
	private String name;        // 申报人名
	private String applicant;   // 系统告警-申请人
	private String problemClass;// 问题等级
	private Date dateStart;     // 查询开始时间
	private Date dateEnd;       // 查询结束时间
	private MailList mailList;  // 通讯录
	private String conductorid;	// 处理人id
	private String opsname;		// 处理人名
	private Date finishTime;//工单结束时间

	private ResourceException exception;

	public MailList getMailList() {
		return mailList;
	}

	public void setMailList(MailList mailList) {
		this.mailList = mailList;
	}

	public OaIssueReturn() {
		super();
	}

	public OaIssueReturn(String id){
		super(id);
	}


	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getProblemClass() {
		return problemClass;
	}

	public void setProblemClass(String problemClass) {
		this.problemClass = problemClass;
	}

	public ResourceException getException() {
		return exception;
	}

	public void setException(ResourceException exception) {
		this.exception = exception;
	}

	@Length(min=0, max=255, message="问题描述长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="申报地点长度必须介于 0 和 255 之间")
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDetailplace() {
		return detailplace;
	}

	public void setDetailplace(String detailplace) {
		this.detailplace = detailplace;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getProblemstate() {
		return problemstate;
	}

	public void setProblemstate(String problemstate) {
		this.problemstate = problemstate;
	}

	public String getEdiid() {
		return ediid;
	}

	public void setEdiid(String ediid) {
		this.ediid = ediid;
	}

	public String getDeclaretype() {
		return declaretype;
	}

	public void setDeclaretype(String declaretype) {
		this.declaretype = declaretype;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConductorid() {
		return conductorid;
	}

	public void setConductorid(String conductorid) {
		this.conductorid = conductorid;
	}

	public String getOpsname() {
		return opsname;
	}

	public void setOpsname(String opsname) {
		this.opsname = opsname;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
}