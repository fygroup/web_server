
package com.jeeplus.modules.networkinterface.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * CPU信息Entity
 * @author le
 * @version 2017-11-07
 */
public class NetInterfaceInOutRate extends DataEntity<NetInterfaceInOutRate> {

	private static final long serialVersionUID = 1L;
	private String networkInterfaceId;		// 接口id
	private NetworkInterface networkInterface;		// 接口
	private String type;		            // 速率类型
	private String rate;


	public NetInterfaceInOutRate() {
		super();
	}

	public NetInterfaceInOutRate(String id,String type){
		this.setNetworkInterfaceId(id);
		this.setType(type);
	}


	public String getNetworkInterfaceId() {
		return networkInterfaceId;
	}

	public void setNetworkInterfaceId(String networkInterfaceId) {
		this.networkInterfaceId = networkInterfaceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	public void setNetworkInterface(NetworkInterface networkInterface) {
		this.networkInterface = networkInterface;
	}
}