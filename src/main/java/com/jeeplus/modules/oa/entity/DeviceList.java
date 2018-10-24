package com.jeeplus.modules.oa.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import javax.validation.constraints.NotNull;

public class DeviceList extends DataEntity<DeviceList> {
    private static final long serialVersionUID = 1L;
    private String id;
    private String devName;//设备名称
    private String devMoney;//价值
    private String state;
    private String isUse;
    private String devSource;//设备来源

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = "设备名称不能为空")
    @ExcelField(title = "设备名称", align = 2, sort = 1)
    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    @NotNull(message = "价值不能为空")
    @ExcelField(title = "价值", align = 2, sort = 2)
    public String getDevMoney() {
        return devMoney;
    }

    public void setDevMoney(String devMoney) {
        this.devMoney = devMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    @ExcelField(title = "设备来源", align = 2, sort = 3)
    public String getDevSource() {
        return devSource;
    }

    public void setDevSource(String devSource) {
        this.devSource = devSource;
    }
}
