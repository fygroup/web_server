
package com.jeeplus.modules.cpu.entity;



import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * CPU信息Entity
 * @author le
 * @version 2017-11-07
 */
public class CpuUsedRate extends DataEntity<CpuUsedRate> {

	private static final long serialVersionUID = 1L;
	protected Date createDate;	// 创建日期
	private String resourceId;		// 资源id
	private String usedRate;// 利用率

	public CpuUsedRate() {
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

	public String getUsedRate() {
		return usedRate;
	}

	public void setUsedRate(String usedRate) {
		this.usedRate = usedRate;
	}
	
}