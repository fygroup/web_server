
package com.jeeplus.modules.resourceinformation.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourceinformation.entity.ResourceInformation;

/**
 * 资源信息表MAPPER接口
 * @author le
 * @version 2017-11-08
 */
@MyBatisMapper
public interface ResourceInformationMapper extends BaseMapper<ResourceInformation> {
    public ResourceInformation getByResourceId(String resourceId);
}