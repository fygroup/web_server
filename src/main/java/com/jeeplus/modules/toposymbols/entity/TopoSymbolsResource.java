package com.jeeplus.modules.toposymbols.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.resource.entity.Resource;

import java.util.Map;


public class TopoSymbolsResource extends DataEntity<TopoSymbolsResource> {

	private static final long serialVersionUID = 1L;
	private String memRate;
	private String sysUpTime;
	private String cpuRate;
	private String resourceName;
	private String ip;
	private String vendor;
	private String sysDesc;
	private String model;    //设备型号
	private String resourceType;    //资源类型
	private String resourceId;//资源id
	private String topoSymbolId;//拓扑图中对应的资源id
	private LinkIndicator linkIndicator;
	private LinkIndicator topoLineId;
	private Map<String,String> indicators;
	private Resource resource;


	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Map<String, String> getIndicators() {
		return indicators;
	}

	public void setIndicators(Map<String, String> indicators) {
		this.indicators = indicators;
	}

	public LinkIndicator getTopoLineId() {
		return topoLineId;
	}

	public void setTopoLineId(LinkIndicator topoLineId) {
		this.topoLineId = topoLineId;
	}

	public LinkIndicator getLinkIndicator() {
		return linkIndicator;
	}

	public void setLinkIndicator(LinkIndicator linkIndicator) {
		this.linkIndicator = linkIndicator;
	}

	public String getTopoSymbolId() {
		return topoSymbolId;
	}

	public void setTopoSymbolId(String topoSymbolId) {
		this.topoSymbolId = topoSymbolId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public TopoSymbolsResource() {
	}

	public String getMemRate() {
		return memRate;
	}

	public void setMemRate(String memRate) {
		this.memRate = memRate;
	}

	public String getSysUpTime() {
		return sysUpTime;
	}

	public void setSysUpTime(String sysUpTime) {
		this.sysUpTime = sysUpTime;
	}

	public String getCpuRate() {
		return cpuRate;
	}

	public void setCpuRate(String cpuRate) {
		this.cpuRate = cpuRate;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSysDesc() {
		return sysDesc;
	}

	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
}