/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcegatheritem.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资源指标采集项Entity
 * @author le
 * @version 2017-11-18
 */
public class ResourceGatherItem extends DataEntity<ResourceGatherItem> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源id
	private String type;		// 类型
	private String description;		// 指标描述
	private String collectionClass;		// 采集类
	
	public ResourceGatherItem() {
		super();
	}

	public ResourceGatherItem(String id){
		super(id);
	}

	public String getCollectionClass() {
		return collectionClass;
	}

	public void setCollectionClass(String collectionClass) {
		this.collectionClass = collectionClass;
	}

	@ExcelField(title="资源id", align=2, sort=7)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="类型", dictType="indicator_type", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="指标描述", align=2, sort=9)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}