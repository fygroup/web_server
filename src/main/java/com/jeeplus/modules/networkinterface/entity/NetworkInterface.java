
package com.jeeplus.modules.networkinterface.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 网络接口Entity
 * @author le
 * @version 2017-11-08
 */
public class NetworkInterface extends DataEntity<NetworkInterface> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源id
	private String capacity;		// 容量
	private String status;		 //接口状态 1，正常 2，断开
	private String abnormalGrade;		// 异常等级
	private String inputByte;		// 输入字节
	private String outputByte;		// 输出字节
	private String template;		// 模板
	private String name;		// 接口名称
	private String interfaceType;  // 接口类型
	private String mac;  // Mac
	private String sort;
	private String inputRate;
	private String outputRate;

	private NetInterfaceInOutRate inRate;
	private NetInterfaceInOutRate outRate;
	
	public NetworkInterface() {
		super();
	}

	public NetworkInterface(String id){
		this.setResourceId(id);
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public NetInterfaceInOutRate getInRate() {
		return inRate;
	}

	public void setInRate(NetInterfaceInOutRate inRate) {
		this.inRate = inRate;
	}

	public NetInterfaceInOutRate getOutRate() {
		return outRate;
	}

	public void setOutRate(NetInterfaceInOutRate outRate) {
		this.outRate = outRate;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getInputRate() {
		return inputRate;
	}

	public void setInputRate(String inputRate) {
		this.inputRate = inputRate;
	}

	public String getOutputRate() {
		return outputRate;
	}

	public void setOutputRate(String outputRate) {
		this.outputRate = outputRate;
	}



	@ExcelField(title="资源id", align=2, sort=7)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="容量", align=2, sort=8)
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	@ExcelField(title="状态", align=2, sort=9)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="异常等级", align=2, sort=10)
	public String getAbnormalGrade() {
		return abnormalGrade;
	}

	public void setAbnormalGrade(String abnormalGrade) {
		this.abnormalGrade = abnormalGrade;
	}
	
	@ExcelField(title="输入字节", align=2, sort=11)
	public String getInputByte() {
		return inputByte;
	}

	public void setInputByte(String inputByte) {
		this.inputByte = inputByte;
	}
	
	@ExcelField(title="输出字节", align=2, sort=12)
	public String getOutputByte() {
		return outputByte;
	}

	public void setOutputByte(String outputByte) {
		this.outputByte = outputByte;
	}
	
	@ExcelField(title="模板", align=2, sort=13)
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	@ExcelField(title="接口名称", align=2, sort=14)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}