package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 问题处理Entity
 * @author huanglei
 * @version 2017-11-16
 */
public class OaDispose extends DataEntity<OaDispose> {

	private static final long serialVersionUID = 1L;
	private String description;		// 问题
	private User user;		// 处理人
	private String name;		//对应处理人名
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String problemstate;		// 问题状态
	private String backresults;		// 回访结果
	private Date complete;		// 完成时间
	private String remark;		// 备注
	private String problemtype;		// 问题类型
	private String plan;		// 解决方案
	private String cause;		// 问题原因
	private String declaretype;  //关联问题申报，对应问题类型
	private String declare;
    private String declareId;   //问题id
	private String user_id;   //用户id
	private String ids;       //用于补充问题处理 解决方案时所用的
	private OaIssueReturn oaIssueReturn;

	public OaIssueReturn getOaIssueReturn() {
		return oaIssueReturn;
	}

	public void setOaIssueReturn(OaIssueReturn oaIssueReturn) {
		this.oaIssueReturn = oaIssueReturn;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public OaDispose() {
		super();
	}

	public OaDispose(String id){
		super(id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProblemstate() {
		return problemstate;
	}

	public void setProblemstate(String problemstate) {
		this.problemstate = problemstate;
	}

	public String getBackresults() {
		return backresults;
	}

	public void setBackresults(String backresults) {
		this.backresults = backresults;
	}

	public Date getComplete() {
		return complete;
	}

	public void setComplete(Date complete) {
		this.complete = complete;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProblemtype() {
		return problemtype;
	}

	public void setProblemtype(String problemtype) {
		this.problemtype = problemtype;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getDeclaretype() {
		return declaretype;
	}

	public void setDeclaretype(String declaretype) {
		this.declaretype = declaretype;
	}

	public String getDeclare() {
		return declare;
	}

	public void setDeclare(String declare) {
		this.declare = declare;
	}

	public String getDeclareId() {
		return declareId;
	}

	public void setDeclareId(String declareId) {
		this.declareId = declareId;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}