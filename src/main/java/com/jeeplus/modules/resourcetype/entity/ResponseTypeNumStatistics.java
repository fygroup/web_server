
package com.jeeplus.modules.resourcetype.entity;

import com.jeeplus.core.persistence.DataEntity;


public class ResponseTypeNumStatistics extends DataEntity<ResponseTypeNumStatistics> {

	private static final long serialVersionUID = 1L;
	protected String  num;	        //
	private ResourceType resourceType;

	public ResponseTypeNumStatistics() {
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
}