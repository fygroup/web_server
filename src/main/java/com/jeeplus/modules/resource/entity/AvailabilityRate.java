
package com.jeeplus.modules.resource.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;


public class AvailabilityRate extends DataEntity<AvailabilityRate> {

	private static final long serialVersionUID = 1L;
	protected Date createDate;	// 创建日期
	private String resourceId;		// 资源id
	private String availabilityRate;  // 可用率

	public String getAvailabilityRate() {
		return availabilityRate;
	}

	public void setAvailabilityRate(String availabilityRate) {
		this.availabilityRate = availabilityRate;
	}

	public AvailabilityRate() {
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

}