
package com.jeeplus.modules.resourcegatheritem.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源指标采集项MAPPER接口
 * @author le
 * @version 2017-11-18
 */
@MyBatisMapper
public interface ResourceGatherItemMapper extends BaseMapper<ResourceGatherItem> {

   List<ResourceGatherItem> findListByResource(@Param(value = "resourceId") String resourceId);

   void delByResourceId(@Param(value = "resourceId") String resourceId);
}