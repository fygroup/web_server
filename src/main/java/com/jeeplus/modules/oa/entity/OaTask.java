package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

import java.util.Date;

public class OaTask extends DataEntity<OaTask> {
    private static final long serialVersionUID = 1L;
    private String taskName;//任务名称
    private String userId;//参与人员id
    private User user;// 参与人员
    private String name;// 人员名称
    private Date startTime;	// 开始时间
    private Date endTime;// 结束时间
    private String isFlag;//是否完成
    private String description;// 任务描述
    private String remark;//备注
    private Date orderTime;//创建时间
    private String ids;//人员ids
    private String createUser;//创建人

    public OaTask() {
        super();
    }

    public OaTask(String id){
        super();
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
