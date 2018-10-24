/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.indextemplate.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.indicator.entity.Indicator;

import java.util.List;

/**
 * 指标模板Entity
 * @author le
 * @version 2018-01-20
 */
public class IndexTemplate extends DataEntity<IndexTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 模板名称
	private String resourceType;		// 资源类型
	private List<Indicator> indicatorList; //指标列表
	
	public IndexTemplate() {
		super();
	}

	public IndexTemplate(String id){
		super(id);
	}

	public List<Indicator> getIndicatorList() {
		return indicatorList;
	}

	public void setIndicatorList(List<Indicator> indicatorList) {
		this.indicatorList = indicatorList;
	}

	@ExcelField(title="模板名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="资源类型", align=2, sort=8)
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}