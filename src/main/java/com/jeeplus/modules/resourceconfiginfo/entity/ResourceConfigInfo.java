/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceconfiginfo.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资源配置信息Entity
 * @author le
 * @version 2017-11-08
 */
public class ResourceConfigInfo extends DataEntity<ResourceConfigInfo> {
	
	private static final long serialVersionUID = 1L;
	private String cpuNum;		// CPU 个数
	private String singleCpuType;		// 单个 CPU 型号
	private String singleCpuRate;		// 单个 CPU 频率
	private String singleCpuCorenum;		// 单个 CPU 核数
	private String memory;		// 内存
	private String memoryNum;		// 内存条个数
	private String memorycCapacity;		// 内存条容量
	private String diskNum;		// 硬盘个数
	private String diskCapacity;		// 硬盘容量
	private String diskType;		// 内置硬盘类型
	private String singleDiskCapacity;		// 单个内置硬盘容量
	private String diskAvailableCapacity;		// 内置硬盘可用容量
	private String diskIfRaid;		// 内置硬盘是否RAID级别
	private String netcardNum;		// 网卡个数         
	private String resourceId;		// 资源编号
	private String UNum;		// U数
	private String controllerNum;		// 控制器数量
	private String elepowerModuleNum;		// 电源模块数量
	private String singlePowermodulePower;		// 单个电源模块功率
	private String usedInterfaceNum;		// 已用接口数量
	private String avaliableInterfaceNum;		// 可用接口数量
	private String specialBoardNum;		// 特殊板卡数量
	private String specialBoardType;		// 特殊板卡类型
	private String elepowerModulePower;		// 电源模块功率
	
	public ResourceConfigInfo() {
		super();
	}

	public ResourceConfigInfo(String id){
		super(id);
	}

	@ExcelField(title="CPU 个数", align=2, sort=7)
	public String getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}
	
	@ExcelField(title="单个 CPU 型号", align=2, sort=8)
	public String getSingleCpuType() {
		return singleCpuType;
	}

	public void setSingleCpuType(String singleCpuType) {
		this.singleCpuType = singleCpuType;
	}
	
	@ExcelField(title="单个 CPU 频率", align=2, sort=9)
	public String getSingleCpuRate() {
		return singleCpuRate;
	}

	public void setSingleCpuRate(String singleCpuRate) {
		this.singleCpuRate = singleCpuRate;
	}
	
	@ExcelField(title="单个 CPU 核数", align=2, sort=10)
	public String getSingleCpuCorenum() {
		return singleCpuCorenum;
	}

	public void setSingleCpuCorenum(String singleCpuCorenum) {
		this.singleCpuCorenum = singleCpuCorenum;
	}
	
	@ExcelField(title="内存", align=2, sort=11)
	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}
	
	@ExcelField(title="内存条个数", align=2, sort=12)
	public String getMemoryNum() {
		return memoryNum;
	}

	public void setMemoryNum(String memoryNum) {
		this.memoryNum = memoryNum;
	}
	
	@ExcelField(title="内存条容量", align=2, sort=13)
	public String getMemorycCapacity() {
		return memorycCapacity;
	}

	public void setMemorycCapacity(String memorycCapacity) {
		this.memorycCapacity = memorycCapacity;
	}
	
	@ExcelField(title="硬盘个数", align=2, sort=14)
	public String getDiskNum() {
		return diskNum;
	}

	public void setDiskNum(String diskNum) {
		this.diskNum = diskNum;
	}
	
	@ExcelField(title="硬盘容量", align=2, sort=15)
	public String getDiskCapacity() {
		return diskCapacity;
	}

	public void setDiskCapacity(String diskCapacity) {
		this.diskCapacity = diskCapacity;
	}
	
	@ExcelField(title="内置硬盘类型", align=2, sort=16)
	public String getDiskType() {
		return diskType;
	}

	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}
	
	@ExcelField(title="单个内置硬盘容量", align=2, sort=17)
	public String getSingleDiskCapacity() {
		return singleDiskCapacity;
	}

	public void setSingleDiskCapacity(String singleDiskCapacity) {
		this.singleDiskCapacity = singleDiskCapacity;
	}
	
	@ExcelField(title="内置硬盘可用容量", align=2, sort=18)
	public String getDiskAvailableCapacity() {
		return diskAvailableCapacity;
	}

	public void setDiskAvailableCapacity(String diskAvailableCapacity) {
		this.diskAvailableCapacity = diskAvailableCapacity;
	}
	
	@ExcelField(title="内置硬盘是否RAID级别", align=2, sort=19)
	public String getDiskIfRaid() {
		return diskIfRaid;
	}

	public void setDiskIfRaid(String diskIfRaid) {
		this.diskIfRaid = diskIfRaid;
	}
	
	@ExcelField(title="网卡个数         ", align=2, sort=20)
	public String getNetcardNum() {
		return netcardNum;
	}

	public void setNetcardNum(String netcardNum) {
		this.netcardNum = netcardNum;
	}
	
	@ExcelField(title="资源编号", align=2, sort=21)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="U数", align=2, sort=22)
	public String getUNum() {
		return UNum;
	}

	public void setUNum(String UNum) {
		this.UNum = UNum;
	}
	
	@ExcelField(title="控制器数量", align=2, sort=23)
	public String getControllerNum() {
		return controllerNum;
	}

	public void setControllerNum(String controllerNum) {
		this.controllerNum = controllerNum;
	}
	
	@ExcelField(title="电源模块数量", align=2, sort=24)
	public String getElepowerModuleNum() {
		return elepowerModuleNum;
	}

	public void setElepowerModuleNum(String elepowerModuleNum) {
		this.elepowerModuleNum = elepowerModuleNum;
	}
	
	@ExcelField(title="单个电源模块功率", align=2, sort=25)
	public String getSinglePowermodulePower() {
		return singlePowermodulePower;
	}

	public void setSinglePowermodulePower(String singlePowermodulePower) {
		this.singlePowermodulePower = singlePowermodulePower;
	}
	
	@ExcelField(title="已用接口数量", align=2, sort=26)
	public String getUsedInterfaceNum() {
		return usedInterfaceNum;
	}

	public void setUsedInterfaceNum(String usedInterfaceNum) {
		this.usedInterfaceNum = usedInterfaceNum;
	}
	
	@ExcelField(title="可用接口数量", align=2, sort=27)
	public String getAvaliableInterfaceNum () {
		return avaliableInterfaceNum;
	}

	public void setAvaliableInterfaceNum (String avaliableInterfaceNum ) {
		this.avaliableInterfaceNum  = avaliableInterfaceNum ;
	}
	
	@ExcelField(title="特殊板卡数量", align=2, sort=28)
	public String getSpecialBoardNum() {
		return specialBoardNum;
	}

	public void setSpecialBoardNum(String specialBoardNum) {
		this.specialBoardNum = specialBoardNum;
	}
	
	@ExcelField(title="特殊板卡类型", align=2, sort=29)
	public String getSpecialBoardType() {
		return specialBoardType;
	}

	public void setSpecialBoardType(String specialBoardType) {
		this.specialBoardType = specialBoardType;
	}
	
	@ExcelField(title="电源模块功率", align=2, sort=30)
	public String getElepowerModulePower() {
		return elepowerModulePower;
	}

	public void setElepowerModulePower(String elepowerModulePower) {
		this.elepowerModulePower = elepowerModulePower;
	}
	
}