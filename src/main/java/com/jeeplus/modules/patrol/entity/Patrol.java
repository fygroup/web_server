package com.jeeplus.modules.patrol.entity;


import javax.validation.constraints.NotNull;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;


/** 智能巡检实体类
 * @author huanglei
 * @version 2018/11/12
 */
public class Patrol extends DataEntity<Patrol>{
    private static final long serialVersionUID = 1L;
    private String name;		// 巡检名称<=任务名
    private String group;		// 任务组
    private String cronExpression;		// 巡检周期<=定时规则
    private String status;		// 启用状态
    private String isInfo;		// 通知用户
    private String className;		// 任务类
    private String description;		// 描述
    private String checkType;      //  巡检类型
    private String checkContent;   //  巡检内容


    public Patrol() {
        super();
    }

    public Patrol(String id){
        super(id);
    }


    @NotNull(message="巡检名称不能为空")
    @ExcelField(title="巡检名称", align=2, sort=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message="巡检周期不能为空")
    @ExcelField(title="巡检周期", align=2, sort=2)
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @NotNull(message="巡检类型不能为空")
    @ExcelField(title="巡检类型", align=2, sort=3)
    public String getCheckType() {
        return checkType;
    }
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    @NotNull(message="巡检内容不能为空")
    @ExcelField(title="巡检内容", align=2, sort=4)
    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

//    @NotNull(message="任务组不能为空")
//    @ExcelField(title="任务组", dictType="schedule_task_group", align=2, sort=5)
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    @NotNull(message="启用状态不能为空")
    @ExcelField(title="启用状态", dictType="yes_no", align=2, sort=5)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ExcelField(title="通知用户", dictType="schedule_task_info", align=2, sort=6)
    public String getIsInfo() {
        return isInfo;
    }

    public void setIsInfo(String isInfo) {
        this.isInfo = isInfo;
    }

    @ExcelField(title="任务类", align=2, sort=7)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @ExcelField(title="描述", align=2, sort=8)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
