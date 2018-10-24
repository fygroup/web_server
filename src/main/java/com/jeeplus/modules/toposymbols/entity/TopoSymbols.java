/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.toposymbols.entity;

import com.jeeplus.modules.topoview.entity.TopoView;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 拓扑图资源UI信息Entity
 * @author sun
 * @version 2017-11-29
 */
public class TopoSymbols extends DataEntity<TopoSymbols> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String x;		// 坐标x
	private String y;		// 坐标y
	private String type;		// 类型
	private String timestamp;		// 创建时间
	private TopoView view;		// 归属拓扑图id
	private String parentId;		// 父类id
	private String objectId;		// 归属资源id
	private String objectClass;		// 归属资源编码
	private String option;		// 可选项
	private String url;		// url
	private String alarm;		// alarm
	private String style;       //资源显示
	
	public TopoSymbols() {
		super();
	}

	public TopoSymbols(String id){
		super(id);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@ExcelField(title="姓名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="坐标x", align=2, sort=8)
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}
	
	@ExcelField(title="坐标y", align=2, sort=9)
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	@ExcelField(title="类型", align=2, sort=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="创建时间", align=2, sort=11)
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@ExcelField(title="归属拓扑图id", align=2, sort=12)
	public TopoView getView() {
		return view;
	}

	public void setView(TopoView view) {
		this.view = view;
	}
	
	@ExcelField(title="父类id", align=2, sort=13)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@ExcelField(title="归属资源id", align=2, sort=14)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	@ExcelField(title="归属资源编码", align=2, sort=15)
	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}
	
	@ExcelField(title="可选项", align=2, sort=16)
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	@ExcelField(title="url", align=2, sort=17)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="alarm", align=2, sort=18)
	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	
}