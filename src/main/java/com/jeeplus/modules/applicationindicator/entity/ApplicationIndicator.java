/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.applicationindicator.entity;

import com.jeeplus.modules.resource.entity.Resource;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 应用指标Entity
 * @author le
 * @version 2017-12-12
 */
public class ApplicationIndicator extends DataEntity<ApplicationIndicator> {

    private static final long serialVersionUID = 1L;
    private Resource resource;        // 资源id
    private String indicatorName;        // 指标名称
    private String unit;        // 单位
    private String url;        // 链接
    private String type;
    private ApplicationIndicatorValue applicationIndicatorValue;

    public ApplicationIndicator() {
        super();
    }

    public ApplicationIndicator(String id) {
        super(id);
    }

    public ApplicationIndicatorValue getApplicationIndicatorValue() {
        return applicationIndicatorValue;
    }

    public void setApplicationIndicatorValue(ApplicationIndicatorValue applicationIndicatorValue) {
        this.applicationIndicatorValue = applicationIndicatorValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ExcelField(title = "资源id", align = 2, sort = 7)
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @ExcelField(title = "指标名称", align = 2, sort = 8)
    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    @ExcelField(title = "单位", align = 2, sort = 9)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @ExcelField(title = "链接", align = 2, sort = 10)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}