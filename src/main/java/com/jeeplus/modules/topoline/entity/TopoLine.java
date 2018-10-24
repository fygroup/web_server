/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoline.entity;

import com.jeeplus.modules.topoview.entity.TopoView;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 拓扑图线条位置信息Entity
 * @author sun
 * @version 2017-11-30
 */
public class TopoLine extends DataEntity<TopoLine> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 线路名称
	private String path;		// 线路位置
	private String type;		// 线路类型
	private String srcSymbol;		// 线路起始资源id
	private String dstSymbol;		// 线路最终资源id
	private TopoView view;		// 对应拓扑图id
	private String objectId;		// 线路id
	private String objectClass;		// 线路编码
	private String instanceId;		// 实例化id
	private String options;		// 可选项
	private String url;		// url
	private String alarm;		// alarm
	private String style;       //线路显示
	private String lineType;   //线类型

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public TopoLine() {
		super();
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public TopoLine(String id){
		super(id);
	}

	@ExcelField(title="线路名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="线路位置", align=2, sort=8)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@ExcelField(title="线路类型", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="线路起始资源id", align=2, sort=10)
	public String getSrcSymbol() {
		return srcSymbol;
	}

	public void setSrcSymbol(String srcSymbol) {
		this.srcSymbol = srcSymbol;
	}
	
	@ExcelField(title="线路最终资源id", align=2, sort=11)
	public String getDstSymbol() {
		return dstSymbol;
	}

	public void setDstSymbol(String dstSymbol) {
		this.dstSymbol = dstSymbol;
	}
	
	@ExcelField(title="对应拓扑图id", align=2, sort=12)
	public TopoView getView() {
		return view;
	}

	public void setView(TopoView view) {
		this.view = view;
	}
	
	@ExcelField(title="线路id", align=2, sort=13)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	@ExcelField(title="线路编码", align=2, sort=14)
	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}
	
	@ExcelField(title="实例化id", align=2, sort=15)
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	@ExcelField(title="可选项", align=2, sort=16)
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
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