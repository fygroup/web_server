/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.linkindicator.entity;

import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.resource.entity.Resource;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 链路指标Entity
 * @author le
 * @version 2017-11-30
 */
public class LinkIndicator extends DataEntity<LinkIndicator> {
	
	private static final long serialVersionUID = 1L;
	private String capacity;		// 容量
	private String upLinkEquequipment;		// 上联设备
	private String upLinkInterface;		// 上联接口
	private String downLinkEquequipment;		// 下联设备
	private String downLinkInterface;		// 下联接口
	private String status;		// 状态
	private String upRate;		// 上行速率
	private String downRate;		// 下行速率
	private String upUsedRate;		// 上行利用率
	private String downUsedRate;		// 下行利用率
	private String healthDegree;		// 健康度
	private String availability;		// 可用率
	private Resource resource;		// 资源
	private Resource upResource;		// 上联设备
	private Resource downResource;		// 下联设备
	private String  gatherItem;		// 取值项

	private NetworkInterface upInterface;		// 上联接口
	private NetworkInterface downInterface;		// 下联接口

	private String upSymbolsId;
	private String DownSymbolsId;

	public String getUpSymbolsId() {
		return upSymbolsId;
	}

	public void setUpSymbolsId(String upSymbolsId) {
		this.upSymbolsId = upSymbolsId;
	}

	public String getDownSymbolsId() {
		return DownSymbolsId;
	}

	public void setDownSymbolsId(String downSymbolsId) {
		DownSymbolsId = downSymbolsId;
	}

	public LinkIndicator() {
		super();
	}

	public LinkIndicator(String id){
		super(id);
	}

	public String getGatherItem() {
		return gatherItem;
	}

	public void setGatherItem(String gatherItem) {
		this.gatherItem = gatherItem;
	}

	public Resource getUpResource() {
		return upResource;
	}

	public void setUpResource(Resource upResource) {
		this.upResource = upResource;
	}

	public Resource getDownResource() {
		return downResource;
	}

	public void setDownResource(Resource downResource) {
		this.downResource = downResource;
	}

	public NetworkInterface getUpInterface() {
		return upInterface;
	}

	public void setUpInterface(NetworkInterface upInterface) {
		this.upInterface = upInterface;
	}

	public NetworkInterface getDownInterface() {
		return downInterface;
	}

	public void setDownInterface(NetworkInterface downInterface) {
		this.downInterface = downInterface;
	}

	@ExcelField(title="容量", align=2, sort=7)
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	@ExcelField(title="上联设备", align=2, sort=8)
	public String getUpLinkEquequipment() {
		return upLinkEquequipment;
	}

	public void setUpLinkEquequipment(String upLinkEquequipment) {
		this.upLinkEquequipment = upLinkEquequipment;
	}
	
	@ExcelField(title="上联接口", align=2, sort=9)
	public String getUpLinkInterface() {
		return upLinkInterface;
	}

	public void setUpLinkInterface(String upLinkInterface) {
		this.upLinkInterface = upLinkInterface;
	}
	
	@ExcelField(title="下联设备", align=2, sort=10)
	public String getDownLinkEquequipment() {
		return downLinkEquequipment;
	}

	public void setDownLinkEquequipment(String downLinkEquequipment) {
		this.downLinkEquequipment = downLinkEquequipment;
	}
	
	@ExcelField(title="下联接口", align=2, sort=11)
	public String getDownLinkInterface() {
		return downLinkInterface;
	}

	public void setDownLinkInterface(String downLinkInterface) {
		this.downLinkInterface = downLinkInterface;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="上行速率", align=2, sort=13)
	public String getUpRate() {
		return upRate;
	}

	public void setUpRate(String upRate) {
		this.upRate = upRate;
	}
	
	@ExcelField(title="下行速率", align=2, sort=14)
	public String getDownRate() {
		return downRate;
	}

	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}
	
	@ExcelField(title="上行利用率", align=2, sort=15)
	public String getUpUsedRate() {
		return upUsedRate;
	}

	public void setUpUsedRate(String upUsedRate) {
		this.upUsedRate = upUsedRate;
	}
	
	@ExcelField(title="下行利用率", align=2, sort=16)
	public String getDownUsedRate() {
		return downUsedRate;
	}

	public void setDownUsedRate(String downUsedRate) {
		this.downUsedRate = downUsedRate;
	}
	
	@ExcelField(title="健康度", align=2, sort=17)
	public String getHealthDegree() {
		return healthDegree;
	}

	public void setHealthDegree(String healthDegree) {
		this.healthDegree = healthDegree;
	}
	
	@ExcelField(title="可用率", align=2, sort=18)
	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	@ExcelField(title="资源", align=2, sort=19)
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}