/**
 * Copyright &copy; 2015-2020 <a href="http://www.clutek.org/">clutek</a> All rights reserved.
 */
package com.jeeplus.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;


/**
 * 通知通告记录Entity
 * @author clutek
 * @version 2017-05-16
 */
public class OaNotifyRecord extends DataEntity<OaNotifyRecord> {
	
	private static final long serialVersionUID = 1L;
	private OaNotify oaNotify;		// 通知通告ID
	private User user;		// 接受人
	private String readFlag;		// 阅读标记（0：未读；1：已读）
	private Date readDate;		// 阅读时间

	private String oa_notify_id;  //派单通知
	private String user_id;       //派单用户

	private List<OaNotifyRecord> oaNotifyRecordList = Lists.newArrayList();

	public OaNotifyRecord() {
		super();
	}

	public OaNotifyRecord(String id){
		super(id);
	}

	public OaNotifyRecord(OaNotify oaNotify){
		this.oaNotify = oaNotify;
	}

	public OaNotify getOaNotify() {
		return oaNotify;
	}

	public void setOaNotify(OaNotify oaNotify) {
		this.oaNotify = oaNotify;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=1, message="阅读标记（0：未读；1：已读）长度必须介于 0 和 1 之间")
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOa_notify_id() {
		return oa_notify_id;
	}

	public void setOa_notify_id(String oa_notify_id) {
		this.oa_notify_id = oa_notify_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public List<OaNotifyRecord> getOaNotifyRecordList() {
		return oaNotifyRecordList;
	}

	public void setOaNotifyRecordList(List<OaNotifyRecord> oaNotifyRecordList) {
		this.oaNotifyRecordList = oaNotifyRecordList;
	}
}