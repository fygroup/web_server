
package com.jeeplus.modules.resource.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.test.entity.manytoone.Category;

/**
 * 资源Entity
 * @author le
 * @version 2017-10-27
 */
public class CpuInfo extends DataEntity<CpuInfo> {

	private static final long serialVersionUID = 1L;
	private String name;		// 资源名称
	private String status;		// 状态
	private ResourceType resourceType;		// 资源类型
	private String ip;		// IP
	private String port;		// 端口
	private String mac;		// mac地址
	private String rdcommunity;		// 读共同体
	private String subnetmask;		// 子网掩码
	private String delay;		// 延时（毫秒）
	private String repeatnum;		// 重试次数
	private Office company;		// 归属公司
	private String description;

	public CpuInfo() {
		super();
	}

	public CpuInfo(String id){
		super(id);
	}

	@ExcelField(title="资源名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="状态", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="资源类型", fieldType=Category.class, value="resourceType.name", align=2, sort=9)
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	@ExcelField(title="IP", align=2, sort=10)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="端口", align=2, sort=11)
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@ExcelField(title="mac地址", align=2, sort=12)
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@ExcelField(title="读共同体", align=2, sort=13)
	public String getRdcommunity() {
		return rdcommunity;
	}

	public void setRdcommunity(String rdcommunity) {
		this.rdcommunity = rdcommunity;
	}
	
	@ExcelField(title="子网掩码", align=2, sort=14)
	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}
	
	@ExcelField(title="延时（毫秒）", align=2, sort=15)
	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}
	
	@ExcelField(title="重试次数", align=2, sort=16)
	public String getRepeatnum() {
		return repeatnum;
	}

	public void setRepeatnum(String repeatnum) {
		this.repeatnum = repeatnum;
	}
	
	@ExcelField(title="归属公司", fieldType=String.class, value="", align=2, sort=17)
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}