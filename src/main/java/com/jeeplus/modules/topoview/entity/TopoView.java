/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoview.entity;

import com.google.common.collect.Lists;
import com.jeeplus.modules.sys.entity.User;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;

/**
 * 拓扑视图Entity
 * @author le
 * @version 2017-11-29
 */
public class TopoView extends DataEntity<TopoView> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名字
	private String descr;		// 描述
	private String type;		// 类型
	private User user;		// 用户
	private Date timeStamp;		// 时间
	private String isInstance;		// 是否是实例化
	private String isCache;		// 是否缓存
	private String isHidden;		// 是否隐藏
	private String config;		// 配置
	private String options;		// 设置
	private String parentViewId;		// 父类视图编码
	private String orderCode;		// 排列编码
	private String relSymbolId;		// 符号编码
	private List<TopoSymbols> topoSymbolsList = Lists.newArrayList();
	private List<TopoLine> topoLineList = Lists.newArrayList();
	private Boolean hasException;     //是否有异常
	
	public TopoView() {
		super();
	}

	public TopoView(String id){
		super(id);
	}

	public Boolean getHasException() {
		return hasException;
	}

	public void setHasException(Boolean hasException) {
		this.hasException = hasException;
	}

	public List<TopoLine> getTopoLineList() {
		return topoLineList;
	}

	public void setTopoLineList(List<TopoLine> topoLineList) {
		this.topoLineList = topoLineList;
	}

	public List<TopoSymbols> getTopoSymbolsList() {
		return topoSymbolsList;
	}

	public void setTopoSymbolsList(List<TopoSymbols> topoSymbolsList) {
		this.topoSymbolsList = topoSymbolsList;
	}


	@ExcelField(title="名字", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="描述", align=2, sort=8)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	@ExcelField(title="类型", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="用户", align=2, sort=10)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", align=2, sort=11)
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@ExcelField(title="是否是实例化", align=2, sort=12)
	public String getIsInstance() {
		return isInstance;
	}

	public void setIsInstance(String isInstance) {
		this.isInstance = isInstance;
	}
	
	@ExcelField(title="是否缓存", align=2, sort=13)
	public String getIsCache() {
		return isCache;
	}

	public void setIsCache(String isCache) {
		this.isCache = isCache;
	}
	
	@ExcelField(title="是否隐藏", align=2, sort=14)
	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	
	@ExcelField(title="配置", align=2, sort=15)
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
	
	@ExcelField(title="设置", align=2, sort=16)
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	
	@ExcelField(title="父类视图编码", align=2, sort=17)
	public String getParentViewId() {
		return parentViewId;
	}

	public void setParentViewId(String parentViewId) {
		this.parentViewId = parentViewId;
	}
	
	@ExcelField(title="排列编码", align=2, sort=18)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@ExcelField(title="符号编码", align=2, sort=19)
	public String getRelSymbolId() {
		return relSymbolId;
	}

	public void setRelSymbolId(String relSymbolId) {
		this.relSymbolId = relSymbolId;
	}
	
}