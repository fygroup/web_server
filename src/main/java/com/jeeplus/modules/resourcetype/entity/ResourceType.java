
package com.jeeplus.modules.resourcetype.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 资源类型Entity
 * @author le
 * @version 2017-10-27
 */
public class ResourceType extends TreeEntity<ResourceType> {
	
	private static final long serialVersionUID = 1L;
	private String img;		// 图片

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResourceType() {
		super();
	}

	public ResourceType(String id){
		super(id);
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public  ResourceType getParent() {
			return parent;
	}
	
	@Override
	public void setParent(ResourceType parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}