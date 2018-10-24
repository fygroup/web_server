/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceinformation.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 资源信息表Entity
 * @author le
 * @version 2017-11-08
 */
public class ResourceInformation extends DataEntity<ResourceInformation> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源编码
	private String equipmentCategory;		// 设备大类
	private String equipmentType;		// 设备小类
	private String sysUrl;		// 系统网址
	private String opsFirm;		// 运维厂商
	private String opsPerson;		// 运维人员
	private String opsContact;		// 运维人员联系方式
	private String equipmentSupplier;		// 设备供应商
	private String purchaseSum;		// 采购金额
	private Date addedDate;		// 上架日期
	private String addedDateString;		// 上架日期
	private String maintenancePeriod;		// 维保年限
	private String courtId;		// 所属机构编码
	private String priority;		// 优先级
	private String server;		// 所在服务器
	private String dbPort;		// 数据库端口
	private String dbEdition;		// 数据库版本
	private String os;		// 操作系统
	private String osEdition;		// 系统版本
	
	public ResourceInformation() {
		super();
	}

	public ResourceInformation(String id){
		super(id);
	}

	public String getAddedDateString() {
		return addedDateString;
	}

	public void setAddedDateString(String addedDateString) {
		this.addedDateString = addedDateString;
	}

	@ExcelField(title="资源编码", align=2, sort=7)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="设备大类", align=2, sort=8)
	public String getEquipmentCategory() {
		return equipmentCategory;
	}

	public void setEquipmentCategory(String equipmentCategory) {
		this.equipmentCategory = equipmentCategory;
	}
	
	@ExcelField(title="设备小类", align=2, sort=9)
	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	@ExcelField(title="系统网址", align=2, sort=10)
	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}
	
	@ExcelField(title="运维厂商", align=2, sort=11)
	public String getOpsFirm() {
		return opsFirm;
	}

	public void setOpsFirm(String opsFirm) {
		this.opsFirm = opsFirm;
	}
	
	@ExcelField(title="运维人员", align=2, sort=12)
	public String getOpsPerson() {
		return opsPerson;
	}

	public void setOpsPerson(String opsPerson) {
		this.opsPerson = opsPerson;
	}
	
	@ExcelField(title="运维人员联系方式", align=2, sort=13)
	public String getOpsContact() {
		return opsContact;
	}

	public void setOpsContact(String opsContact) {
		this.opsContact = opsContact;
	}
	
	@ExcelField(title="设备供应商", align=2, sort=14)
	public String getEquipmentSupplier() {
		return equipmentSupplier;
	}

	public void setEquipmentSupplier(String equipmentSupplier) {
		this.equipmentSupplier = equipmentSupplier;
	}
	
	@ExcelField(title="采购金额", align=2, sort=15)
	public String getPurchaseSum() {
		return purchaseSum;
	}

	public void setPurchaseSum(String purchaseSum) {
		this.purchaseSum = purchaseSum;
	}
	
	@ExcelField(title="上架日期", align=2, sort=16)
	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	
	@ExcelField(title="维保年限", align=2, sort=17)
	public String getMaintenancePeriod() {
		return maintenancePeriod;
	}

	public void setMaintenancePeriod(String maintenancePeriod) {
		this.maintenancePeriod = maintenancePeriod;
	}
	
	@ExcelField(title="所属机构编码", align=2, sort=18)
	public String getCourtId() {
		return courtId;
	}

	public void setCourtId(String courtId) {
		this.courtId = courtId;
	}
	
	@ExcelField(title="优先级", align=2, sort=19)
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	@ExcelField(title="所在服务器", align=2, sort=20)
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	@ExcelField(title="数据库端口", align=2, sort=21)
	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	
	@ExcelField(title="数据库版本", align=2, sort=22)
	public String getDbEdition() {
		return dbEdition;
	}

	public void setDbEdition(String dbEdition) {
		this.dbEdition = dbEdition;
	}
	
	@ExcelField(title="操作系统", align=2, sort=23)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	@ExcelField(title="系统版本", align=2, sort=24)
	public String getOsEdition() {
		return osEdition;
	}

	public void setOsEdition(String osEdition) {
		this.osEdition = osEdition;
	}
	
}