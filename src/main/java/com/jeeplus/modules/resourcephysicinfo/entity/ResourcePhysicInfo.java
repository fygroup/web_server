/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcephysicinfo.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资源物理信息Entity
 * @author le
 * @version 2017-11-08
 */
public class ResourcePhysicInfo extends DataEntity<ResourcePhysicInfo> {
	
	private static final long serialVersionUID = 1L;
	private String building;		// 建筑
	private String machineroom;		// 机房
	private String resourceId;		// 资源编号
	private String cabinet;		// 机柜
	private String cabinetNo;		// 柜内编号
	private String cabinetCapacity;		// 机柜容量
	private String locate;		// 所属位置
	private String datailedAddress;		// 详细地址
	private String weight;		// 重量
	private String height;		// 高度
	private String powerDissipation;		// 功耗
	private String heat;		// 发热
	
	public ResourcePhysicInfo() {
		super();
	}

	public ResourcePhysicInfo(String id){
		super(id);
	}

	@ExcelField(title="建筑", align=2, sort=7)
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}
	
	@ExcelField(title="机房", align=2, sort=8)
	public String getMachineroom() {
		return machineroom;
	}

	public void setMachineroom(String machineroom) {
		this.machineroom = machineroom;
	}
	
	@ExcelField(title="资源编号", align=2, sort=9)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="机柜", align=2, sort=10)
	public String getCabinet() {
		return cabinet;
	}

	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	
	@ExcelField(title="柜内编号", align=2, sort=11)
	public String getCabinetNo() {
		return cabinetNo;
	}

	public void setCabinetNo(String cabinetNo) {
		this.cabinetNo = cabinetNo;
	}
	
	@ExcelField(title="机柜容量", align=2, sort=12)
	public String getCabinetCapacity() {
		return cabinetCapacity;
	}

	public void setCabinetCapacity(String cabinetCapacity) {
		this.cabinetCapacity = cabinetCapacity;
	}
	
	@ExcelField(title="所属位置", align=2, sort=13)
	public String getLocate() {
		return locate;
	}

	public void setLocate(String locate) {
		this.locate = locate;
	}
	
	@ExcelField(title="详细地址", align=2, sort=14)
	public String getDatailedAddress() {
		return datailedAddress;
	}

	public void setDatailedAddress(String datailedAddress) {
		this.datailedAddress = datailedAddress;
	}
	
	@ExcelField(title="重量", align=2, sort=15)
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@ExcelField(title="高度", align=2, sort=16)
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@ExcelField(title="功耗", align=2, sort=17)
	public String getPowerDissipation() {
		return powerDissipation;
	}

	public void setPowerDissipation(String powerDissipation) {
		this.powerDissipation = powerDissipation;
	}
	
	@ExcelField(title="发热", align=2, sort=18)
	public String getHeat() {
		return heat;
	}

	public void setHeat(String heat) {
		this.heat = heat;
	}
	
}