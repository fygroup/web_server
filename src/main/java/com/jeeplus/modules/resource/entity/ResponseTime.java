
package com.jeeplus.modules.resource.entity;



import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 响应时间Entity(ICMP和数据库连接响应时间)
 * @author le
 * @version 2017-11-07
 */
public class ResponseTime extends DataEntity<ResponseTime> {

	private static final long serialVersionUID = 1L;
	protected Date createDate;	// 创建日期
	private String resourceId;		// 资源id
	private String time;         // 响应时间

	public ResponseTime() {
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}