/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.process.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 进程Entity
 * @author le
 * @version 2017-11-07
 */
public class Process extends DataEntity<Process> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源id
	private String name;		// 名称
	private String path;		// 地址
	private String cpuUsedPercent;		// cpu利用率
	private String pid;		// pid
	private String initParameter;		// 初始化参数
	private String memory;		// 内存
	
	public Process() {
		super();
	}

	public Process(String resourceId){
		this.setResourceId(resourceId);
	}

	@ExcelField(title="资源id", align=2, sort=7)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="地址", align=2, sort=9)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@ExcelField(title="cpu利用时间", align=2, sort=10)
	public String getCpuUsedPercent() {
		return cpuUsedPercent;
	}

	public void setCpuUsedPercent(String cpuUsedPercent) {
		this.cpuUsedPercent = cpuUsedPercent;
	}
	
	@ExcelField(title="pid", align=2, sort=11)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@ExcelField(title="初始化参数", align=2, sort=12)
	public String getInitParameter() {
		return initParameter;
	}

	public void setInitParameter(String initParameter) {
		this.initParameter = initParameter;
	}
	
	@ExcelField(title="内存", align=2, sort=13)
	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}
	
}