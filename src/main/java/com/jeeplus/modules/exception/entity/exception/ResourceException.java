package com.jeeplus.modules.exception.entity.exception;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import java.util.Date;

/**
 * 异常告警信息Entity
 * @author clouology
 * @version 2017-11-16
 */
public class ResourceException extends DataEntity<ResourceException> {
	
	private static final long serialVersionUID = 1L;
	private String exceptionSource;		// 异常来源
	private String indicatorName;		// 指标名称
	private String totalQuantity;		// 累计次数
	private String exceptionClass;		// 异常等级
	private String currentValue;		// 当前指标值
	private String currentStatus;		// 当前状态（0，未恢复 1，手动恢复 2，已恢复）
	private String firstTriggerValue;		// 第一次触发指标值
	private String firstTriggerClass;		// 第一次触发级别
	private Date firstTriggerTime;		// 第一次触发时间
	private ResourceIndicatorlist resourceIndicator;		// 资源指标id
	private Date lastTriggerTime;		// 最后一次产生异常的时间
	private ResourceType resourceType;		// 资源类型（冗余）
	private String eventType;		// 指标事件类型(冗余)
	private String confirmStatus;		// 确认状态（0，未确认 1，已确认）
	private User confirmUser;		// 确认人
	private Area area;		// 地域（冗余）
	private Office office;		// 归属部门（冗余）
	private Indicator indicatorItem;		// 资源列表项id（冗余）
	private Date beginFirstTriggerTime;		// 开始 第一次触发时间
	private Date endFirstTriggerTime;		// 结束 第一次触发时间
	
	public ResourceException() {
		super();
	}

	public ResourceException(String id){
		super(id);
	}

	@ExcelField(title="异常来源", align=2, sort=7)
	public String getExceptionSource() {
		return exceptionSource;
	}

	public void setExceptionSource(String exceptionSource) {
		this.exceptionSource = exceptionSource;
	}
	
	@ExcelField(title="指标名称", align=2, sort=8)
	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	
	@ExcelField(title="累计次数", align=2, sort=9)
	public String getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	@ExcelField(title="异常等级", dictType="exception_class", align=2, sort=10)
	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
	
	@ExcelField(title="当前指标值", align=2, sort=11)
	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	
	@ExcelField(title="当前状态（0，未恢复 1，手动恢复 2，已恢复）", dictType="exception_current_status", align=2, sort=12)
	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	@ExcelField(title="第一次触发指标值", align=2, sort=13)
	public String getFirstTriggerValue() {
		return firstTriggerValue;
	}

	public void setFirstTriggerValue(String firstTriggerValue) {
		this.firstTriggerValue = firstTriggerValue;
	}
	
	@ExcelField(title="第一次触发级别", dictType="exception_class", align=2, sort=14)
	public String getFirstTriggerClass() {
		return firstTriggerClass;
	}

	public void setFirstTriggerClass(String firstTriggerClass) {
		this.firstTriggerClass = firstTriggerClass;
	}
	
	@ExcelField(title="第一次触发时间", align=2, sort=15)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFirstTriggerTime() {
		return firstTriggerTime;
	}

	public void setFirstTriggerTime(Date firstTriggerTime) {
		this.firstTriggerTime = firstTriggerTime;
	}
	
	@NotNull(message="资源指标id不能为空")
	@ExcelField(title="资源指标id", fieldType=ResourceIndicatorlist.class, value="resourceIndicator.name", align=2, sort=16)
	public ResourceIndicatorlist getResourceIndicator() {
		return resourceIndicator;
	}

	public void setResourceIndicator(ResourceIndicatorlist resourceIndicator) {
		this.resourceIndicator = resourceIndicator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后一次产生异常的时间", align=2, sort=17)
	public Date getLastTriggerTime() {
		return lastTriggerTime;
	}

	public void setLastTriggerTime(Date lastTriggerTime) {
		this.lastTriggerTime = lastTriggerTime;
	}
	
	@NotNull(message="资源类型（冗余）不能为空")
	@ExcelField(title="资源类型（冗余）", fieldType=ResourceType.class, value="resourceType.name", align=2, sort=18)
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	@ExcelField(title="指标事件类型(冗余)", dictType="indicator_event_type", align=2, sort=19)
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	@ExcelField(title="确认状态（0，未确认 1，已确认）", dictType="exception_confirm_status", align=2, sort=20)
	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	@ExcelField(title="确认人", fieldType=User.class, value="confirmUser.name", align=2, sort=21)
	public User getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(User confirmUser) {
		this.confirmUser = confirmUser;
	}
	
	@ExcelField(title="地域（冗余）", align=2, sort=22)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@NotNull(message="归属部门（冗余）不能为空")
	@ExcelField(title="归属部门（冗余）", fieldType=Office.class, value="office.name", align=2, sort=23)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="资源列表项id（冗余）", fieldType=Indicator.class, value="indicatorItem.name", align=2, sort=24)
	public Indicator getIndicatorItem() {
		return indicatorItem;
	}

	public void setIndicatorItem(Indicator indicatorItem) {
		this.indicatorItem = indicatorItem;
	}

	public Date getBeginFirstTriggerTime() {
		return beginFirstTriggerTime;
	}

	public void setBeginFirstTriggerTime(Date beginFirstTriggerTime) {
		this.beginFirstTriggerTime = beginFirstTriggerTime;
	}

	public Date getEndFirstTriggerTime() {
		return endFirstTriggerTime;
	}

	public void setEndFirstTriggerTime(Date endFirstTriggerTime) {
		this.endFirstTriggerTime = endFirstTriggerTime;
	}
		
}