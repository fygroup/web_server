/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.manufacturer.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 厂商信息Entity
 * @author le
 * @version 2017-11-01
 */
public class Manufacturer extends DataEntity<Manufacturer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String code;		// code
	private String description;		// 描述
	
	public Manufacturer() {
		super();
	}

	public Manufacturer(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="描述", align=2, sort=8)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ExcelField(title="Code", align=2, sort=8)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}