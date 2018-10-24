/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.operatingsystem.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcetype.entity.ResourceType;

/**
 * 操作系统Entity
 * @author le
 * @version 2017-11-01
 */
public class OperatingSystem extends DataEntity<OperatingSystem> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String serverId;		// 服务器id
	private ResourceType server;
	private String code ;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResourceType getServer() {
		return server;
	}

	public void setServer(ResourceType server) {
		this.server = server;
	}

	public OperatingSystem() {
		super();
	}

	public OperatingSystem(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="服务器id", align=2, sort=8)
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
}