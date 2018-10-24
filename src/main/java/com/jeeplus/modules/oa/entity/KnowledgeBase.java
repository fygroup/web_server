package com.jeeplus.modules.oa.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 知识库Entity
 *
 * @author dzy
 * @version 2018-4-8
 */
public class KnowledgeBase extends DataEntity<KnowledgeBase> {
    private static final long serialVersionUID = 1L;
    private String id;//主键
    private String description;//问题描述
    private String cause;//问题原因
    private String plan;//解决方案
    private String userId;//处理人
    private String ediId;//申报方式
    private String declareType;//申报类型

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public KnowledgeBase() {
        super();
    }

    public KnowledgeBase(String id) {
        super(id);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEdiId() {
        return ediId;
    }

    public void setEdiId(String ediId) {
        this.ediId = ediId;
    }

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }
}
