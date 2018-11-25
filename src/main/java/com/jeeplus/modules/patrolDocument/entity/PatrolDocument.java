/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.patrolDocument.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 巡检生成文件Entity
 * @author lei
 * @version 2018-08-03
 */
public class PatrolDocument extends DataEntity<PatrolDocument> {

	private static final long serialVersionUID = 1L;
    private String name;//文件名称
	private String type;//文件类型
	private String documentUrls;//文件上传地址
	private String remarks;//备注
	private String tdFlag;//文件类别(0.模板1.文件)
	private String  deleteBy;//删除人
	private Date  deleteDate;//删除时间
	private String  status;//状态
	private String  patrolId;//巡检表id


	@ExcelField(title="ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocumentUrls() {
		return documentUrls;
	}

	public void setDocumentUrls(String documentUrls) {
		this.documentUrls = documentUrls;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTdFlag() {
		return tdFlag;
	}

	public void setTdFlag(String tdFlag) {
		this.tdFlag = tdFlag;
	}


	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPatrolId() {
		return patrolId;
	}

	public void setPatrolId(String patrolId) {
		this.patrolId = patrolId;
	}
}