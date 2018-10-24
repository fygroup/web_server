package com.jeeplus.modules.applicationindicator.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 应用指标值Entity
 * @author le
 * @version 2017-12-12
 */
public class ApplicationIndicatorValue extends DataEntity<ApplicationIndicatorValue> {

    private static final long serialVersionUID = 1L;
    private ApplicationIndicator applicationIndicator;        // 应用指标
    private String value;                                    // 值

    public ApplicationIndicatorValue() {
        super();
    }

    public ApplicationIndicator getApplicationIndicator() {
        return applicationIndicator;
    }

    public void setApplicationIndicator(ApplicationIndicator applicationIndicator) {
        this.applicationIndicator = applicationIndicator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}