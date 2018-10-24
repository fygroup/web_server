
package com.jeeplus.modules.resource.entity;

import com.jeeplus.core.persistence.DataEntity;
import java.util.Date;



/**
 * 采集器Entity
 * @author le
 * @version 2017-10-27
 */
public class MokaCollector extends DataEntity<MokaCollector> {

	private static final long serialVersionUID = 1L;
	private String name;		//
	private String status;		//
	private String ipAddress;		// ipAddress
	private Date createTime;		// createTime
	private Date updateTime;		// updateTime
	private String delFlag;		// delFlag


	public MokaCollector() {
		super();
	}

	public MokaCollector(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String getDelFlag() {
		return delFlag;
	}

	@Override
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}