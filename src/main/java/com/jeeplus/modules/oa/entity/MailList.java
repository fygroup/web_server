package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class MailList extends DataEntity<MailList> {
    private static final long serialVersionUID = 1L;
    private String id;//主键
    private String name;//
    private String sex;//
    private String phone;//
    private String orgId;//部门
    private String orgName;//部门名称
    private String address;//地址
    private String establish; //创建人
    private Date establishTime;//创建时间
    private String deleteFlag;//删除标记
    private Date deleteTime;//删除时间

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEstablish() {
        return establish;
    }

    public void setEstablish(String establish) {
        this.establish = establish;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
