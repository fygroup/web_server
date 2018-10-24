/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cpu.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * CPU信息Entity
 * @author le
 * @version 2017-11-07
 */
public class Cpu extends DataEntity<Cpu> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源id
	private String sort;		// 排序
	private String name;		// 名称
	private String value;		// 值
	
	public Cpu() {
		super();
	}

	public Cpu(String id){
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