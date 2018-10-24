
package com.jeeplus.modules.resourceindicatorlist.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 用户与资源指标列表关系Entity
 * @author le
 * @version 2017-11-14
 */
public class UserResourceIndicatorlist extends DataEntity<UserResourceIndicatorlist> {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String indicatorId;
	private String type;
	private String modularTitle;

	public UserResourceIndicatorlist(String userId, String indicatorId, String type, String modularTitle) {
		this.userId = userId;
		this.indicatorId = indicatorId;
		this.type = type;
		this.modularTitle = modularTitle;
	}

	public String getModularTitle() {
		return modularTitle;
	}

	public void setModularTitle(String modularTitle) {
		this.modularTitle = modularTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}
}