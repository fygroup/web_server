package com.jeeplus.modules.oa.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DevicePurchase extends DataEntity<DevicePurchase> {
    private static final long serialVersionUID = 1L;
    private String id;
    private Date purTime;//采购时间
    private String purUser;//采购人
    private String purName;//采购人姓名
    private String devName;//设备名称
    private String devSource;//设备来源
    private String purMoney;//采购金额
    private String audUser;//审核人
    private String audName;//采购人姓名
    private String isflag;//是否通过
    private String isorder;//是否下单
    private Date ordTime;//下单时间
    private String deviceId;//设备id
    private String isarrival;//是否到货
    private Date arriTime;//到货时间

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

    @ExcelField(title = "采购时间",  align = 2, sort = 3)
    public Date getPurTime() {
        return purTime;
    }

    public void setPurTime(Date purTime) {
        this.purTime = purTime;
    }


    public String getPurUser() {
        return purUser;
    }

    public void setPurUser(String purUser) {
        this.purUser = purUser;
    }

    public String getPurName() {
        return purName;
    }

    public void setPurName(String purName) {
        this.purName = purName;
    }

    @NotNull(message = "设备名称不能为空")
    @ExcelField(title = "设备名称", align = 2, sort = 1)
    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    @ExcelField(title = "设备来源", align = 2, sort = 2)
    public String getDevSource() {
        return devSource;
    }

    public void setDevSource(String devSource) {
        this.devSource = devSource;
    }

    @ExcelField(title = "采购金额", align = 2, sort = 4)
    public String getPurMoney() {
        return purMoney;
    }

    public void setPurMoney(String purMoney) {
        this.purMoney = purMoney;
    }

    public String getAudUser() {
        return audUser;
    }

    public void setAudUser(String audUser) {
        this.audUser = audUser;
    }

    public String getAudName() {
        return audName;
    }

    public void setAudName(String audName) {
        this.audName = audName;
    }

    public String getIsflag() {
        return isflag;
    }

    public void setIsflag(String isflag) {
        this.isflag = isflag;
    }

    public String getIsorder() {
        return isorder;
    }

    public void setIsorder(String isorder) {
        this.isorder = isorder;
    }

    public Date getOrdTime() {
        return ordTime;
    }

    public void setOrdTime(Date ordTime) {
        this.ordTime = ordTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIsarrival() {
        return isarrival;
    }

    public void setIsarrival(String isarrival) {
        this.isarrival = isarrival;
    }

    public Date getArriTime() {
        return arriTime;
    }

    public void setArriTime(Date arriTime) {
        this.arriTime = arriTime;
    }
}
