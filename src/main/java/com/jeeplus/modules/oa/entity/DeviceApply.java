package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class DeviceApply extends DataEntity<DeviceApply> {
    private static final long serialVersionUID = 1L;
    private String id;//主键
    private String appUser;//申请人
    private String deviceId;//申请设备
    private String appDepartment;//申请部门
    private String place;//地点
    private Date appTime;//申请时间
    private String audUser; //审核人
    private String appName;
    private String audName;
    private String departmentName;
    private String isaudit; //是否审核
    private String isdelivery; //是否交付
    private String remark; //备注

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppDepartment() {
        return appDepartment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setAppDepartment(String appDepartment) {
        this.appDepartment = appDepartment;
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }

    public String getAudUser() {
        return audUser;
    }

    public void setAudUser(String audUser) {
        this.audUser = audUser;
    }

    public String getIsaudit() {
        return isaudit;
    }

    public void setIsaudit(String isaudit) {
        this.isaudit = isaudit;
    }

    public String getIsdelivery() {
        return isdelivery;
    }

    public void setIsdelivery(String isdelivery) {
        this.isdelivery = isdelivery;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAudName() {
        return audName;
    }

    public void setAudName(String audName) {
        this.audName = audName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
