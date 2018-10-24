
package com.jeeplus.modules.resourcephysicinfo.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourcephysicinfo.entity.ResourcePhysicInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 资源物理信息MAPPER接口
 * @author le
 * @version 2017-11-08
 */
@MyBatisMapper
public interface ResourcePhysicInfoMapper extends BaseMapper<ResourcePhysicInfo> {
    public ResourcePhysicInfo getByResourceId(@Param(value = "resourceId") String resourceId);
}