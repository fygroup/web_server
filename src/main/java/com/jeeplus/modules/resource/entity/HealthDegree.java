
package com.jeeplus.modules.resource.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;


public class HealthDegree extends DataEntity<HealthDegree> {

	private static final long serialVersionUID = 1L;
	protected Date createDate;	// 创建日期
	private String resourceId;		// 资源id
	private String healthDegree;  // 健康度

	public HealthDegree() {
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getHealthDegree() {
		return healthDegree;
	}

	public void setHealthDegree(String healthDegree) {
		this.healthDegree = healthDegree;
	}
}