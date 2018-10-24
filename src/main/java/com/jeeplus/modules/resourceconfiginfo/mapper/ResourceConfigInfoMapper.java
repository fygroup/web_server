/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceconfiginfo.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourceconfiginfo.entity.ResourceConfigInfo;

/**
 * 资源配置信息MAPPER接口
 * @author le
 * @version 2017-11-08
 */
@MyBatisMapper
public interface ResourceConfigInfoMapper extends BaseMapper<ResourceConfigInfo> {
    public ResourceConfigInfo getByResourceId(String resourceId);
}