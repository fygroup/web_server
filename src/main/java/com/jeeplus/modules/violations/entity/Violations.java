/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.violations.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 违规事件 Entity
 * @author lei
 * @version 2018-09-28
 */
public class Violations extends DataEntity<Violations> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源id
	private String sort;		// 排序
	private String name;		// 名称
	private String value;		// 值
	
	public Violations() {
		super();
	}

	public Violations(String id){
		this.setResourceId(id);
	}

	@ExcelField(title="资源id", align=2, sort=7)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="排序", align=2, sort=8)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="名称", align=2, sort=9)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="值", align=2, sort=10)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}