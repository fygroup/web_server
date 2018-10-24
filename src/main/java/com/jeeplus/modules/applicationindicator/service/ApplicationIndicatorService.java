package com.jeeplus.modules.applicationindicator.service;

import java.util.List;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicatorValue;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.applicationindicator.mapper.ApplicationIndicatorMapper;

/**
 * 应用指标Service
 *
 * @author le
 * @version 2017-12-12
 */
@Service
@Transactional(readOnly = true)
public class ApplicationIndicatorService extends CrudService<ApplicationIndicatorMapper, ApplicationIndicator> {
    @Autowired
    private ApplicationIndicatorMapper applicationIndicatorMapper;

    public ApplicationIndicator get(String id) {
        return super.get(id);
    }

    public List<ApplicationIndicator> findList(ApplicationIndicator applicationIndicator) {
        return super.findList(applicationIndicator);
    }

    public List<ApplicationIndicator> findListByResource(String resourceId) {
        List<ApplicationIndicator> list = applicationIndicatorMapper.findListByResource(resourceId);
        if (CheckObject.checkList(list)) {
            for (ApplicationIndicator applicationIndicator : list) {
                applicationIndicator.setApplicationIndicatorValue(applicationIndicatorMapper.getTopValue(applicationIndicator.getId()));
            }
        }
        return list;
    }


    public Page<ApplicationIndicator> findPage(Page<ApplicationIndicator> page, ApplicationIndicator applicationIndicator) {
        return super.findPage(page, applicationIndicator);
    }

    @Transactional(readOnly = false)
    public void save(ApplicationIndicator applicationIndicator) {
        super.save(applicationIndicator);
    }


    @Transactional(readOnly = false)
    public void saveList(List<ApplicationIndicator> list) {
        applicationIndicatorMapper.saveList(list);
    }

    @Transactional(readOnly = false)
    public void saveValueList(List<ApplicationIndicatorValue> list) {
        applicationIndicatorMapper.saveValueList(list);
    }


    @Transactional(readOnly = false)
    public void delete(ApplicationIndicator applicationIndicator) {
        super.delete(applicationIndicator);
    }

}