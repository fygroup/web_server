/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.disk.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 磁盘Entity
 * @author le
 * @version 2017-11-07
 */
public class Disk extends DataEntity<Disk> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 磁盘名称
	private String total;		// 磁盘总量
	private String used;		// 磁盘使用量
	private String free;		// 磁盘剩余量
	private String usedRate;		// 磁盘利用率
	private String resourceId;
	private String sort;
	
	public Disk() {
		super();
	}

	public Disk(String id){
		this.setResourceId(id);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@ExcelField(title="磁盘名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="磁盘总量", align=2, sort=8)
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	@ExcelField(title="磁盘使用量", align=2, sort=9)
	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}
	
	@ExcelField(title="磁盘剩余量", align=2, sort=10)
	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}
	
	@ExcelField(title="磁盘利用率", align=2, sort=11)
	public String getUsedRate() {
		return usedRate;
	}

	public void setUsedRate(String usedRate) {
		this.usedRate = usedRate;
	}
	
}