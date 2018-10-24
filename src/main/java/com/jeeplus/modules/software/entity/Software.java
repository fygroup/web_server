/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.software.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 软件列表Entity
 * @author le
 * @version 2017-11-07
 */
public class Software extends DataEntity<Software> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 软件名称
	private String type;		// 软件类型
	private String resourceId;		// 资源id
	private String modifyTime;
	
	public Software() {
		super();
	}

	public Software(String resourceId){
		this.setResourceId(resourceId);
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	@ExcelField(title="软件名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="软件类型", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="资源id", align=2, sort=9)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}