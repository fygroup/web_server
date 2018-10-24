package com.jeeplus.modules.resource.entity;

/**
 * 封装根据oid获取value值实体
 */
public class ResourceOidValue {

	private static final long serialVersionUID = 1L;
	private String oid;		    // oid
	private String value;		// 对应值


	public ResourceOidValue() {
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}