/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.indextemplate.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.indextemplate.entity.IndexTemplate;
import com.jeeplus.modules.indicator.entity.Indicator;

import java.util.List;

/**
 * 指标模板MAPPER接口
 * @author le
 * @version 2018-01-20
 */
@MyBatisMapper
public interface IndexTemplateMapper extends BaseMapper<IndexTemplate> {
	List<IndexTemplate> list(String resourceTypeId);
    List<Indicator> indicatorListByIndexTemplateId(String indexTemplateId);
}