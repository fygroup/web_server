/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memory.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 内存Entity
 * @author le
 * @version 2017-11-07
 */
public class Memory extends DataEntity<Memory> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型 1:物理内存  2:虚拟内存
	private String resourceId;		// 资源id
	private String name;		// 名称
	private String total;		// 内存总量
	private String used;		// 内存已用量
	private String free;		// 内存剩余量
	private String usedRate;		// 内存利用率
	
	public Memory() {
		super();
	}

	public Memory(String id){
		this.setResourceId(id);
	}

	@ExcelField(title="类型 1:物理内存  2:虚拟内存", align=2, sort=7)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="资源id", align=2, sort=8)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="名称", align=2, sort=9)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="内存总量", align=2, sort=10)
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	@ExcelField(title="内存已用量", align=2, sort=11)
	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}
	
	@ExcelField(title="内存剩余量", align=2, sort=12)
	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}
	
	@ExcelField(title="内存利用率", align=2, sort=13)
	public String getUsedRate() {
		return usedRate;
	}

	public void setUsedRate(String usedRate) {
		this.usedRate = usedRate;
	}
	
}