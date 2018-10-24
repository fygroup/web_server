
package com.jeeplus.modules.indicator.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.operatingsystem.entity.OperatingSystem;
import com.jeeplus.modules.resourcetype.entity.ResourceType;

/**
 * 指标列表Entity
 * @author le
 * @version 2017-11-13
 */
public class Indicator extends DataEntity<Indicator> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String resourceTypeId;		// 所属资源类型
	private ResourceType resourceType;		// 所属资源类型
	private String operatingSystemId;		// 操作系统
	private OperatingSystem operatingSystem;		// 操作系统
	private String eventType;		// 事件类型
	private String unit;		// 单位
	private boolean selected;
	private String type;		// 类型
	private String description;		// 指标描述
	private String itemType;  //

	public Indicator() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Indicator(String id){
		super(id);
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(OperatingSystem operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	@ExcelField(title="名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="所属资源类型", align=2, sort=8)
	public String getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(String resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	
	@ExcelField(title="操作系统", align=2, sort=9)
	public String getOperatingSystemId() {
		return operatingSystemId;
	}

	public void setOperatingSystemId(String operatingSystemId) {
		this.operatingSystemId = operatingSystemId;
	}
	
	@ExcelField(title="事件类型", dictType="indicator_list_type", align=2, sort=11)
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@ExcelField(title="单位", align=2, sort=10)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}