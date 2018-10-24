
package com.jeeplus.modules.resourceindicatorlist.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.resource.entity.Resource;

/**
 * 资源指标列表Entity
 * @author le
 * @version 2017-11-14
 */
public class ResourceIndicatorlist extends DataEntity<ResourceIndicatorlist> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源id
	private Resource resource;		// 资源
	private Indicator indicator;		// 指标列表
	private String indicatorId;		// 指标列表id
	private String value;		// 指标值
	private String highUrgentThreshold; //特急阈值
	private String middleUrgentThreshold; //较急阈值
	private String normalUrgentThreshold; //一般紧急阈值
	private String tipThreshold; //提示阈值
	private String gatherType;		// 采集类型

	
	public ResourceIndicatorlist() {
		super();
	}

	public ResourceIndicatorlist(String id){
		super(id);
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public String getHighUrgentThreshold() {
		return highUrgentThreshold;
	}

	public void setHighUrgentThreshold(String highUrgentThreshold) {
		this.highUrgentThreshold = highUrgentThreshold;
	}

	public String getMiddleUrgentThreshold() {
		return middleUrgentThreshold;
	}

	public void setMiddleUrgentThreshold(String middleUrgentThreshold) {
		this.middleUrgentThreshold = middleUrgentThreshold;
	}

	public String getNormalUrgentThreshold() {
		return normalUrgentThreshold;
	}

	public void setNormalUrgentThreshold(String normalUrgentThreshold) {
		this.normalUrgentThreshold = normalUrgentThreshold;
	}


	@ExcelField(title="采集类型", dictType="", align=2, sort=10)
	public String getGatherType() {
		return gatherType;
	}

	public void setGatherType(String gatherType) {
		this.gatherType = gatherType;
	}


	public String getTipThreshold() {
		return tipThreshold;
	}

	public void setTipThreshold(String tipThreshold) {
		this.tipThreshold = tipThreshold;
	}

	@ExcelField(title="资源id", align=2, sort=7)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@ExcelField(title="指标列表", align=2, sort=8)
	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}
	
	@ExcelField(title="指标值", align=2, sort=9)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	
}