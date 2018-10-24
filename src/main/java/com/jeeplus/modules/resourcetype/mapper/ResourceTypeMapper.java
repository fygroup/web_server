
package com.jeeplus.modules.resourcetype.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.entity.ResponseTypeNumStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源类型MAPPER接口
 * @author le
 * @version 2017-10-27
 */
@MyBatisMapper
public interface ResourceTypeMapper extends TreeMapper<ResourceType> {


     List<ResourceType> getChildrenOfSelect(@Param(value = "id") String id);
     ResourceType getParentByChild(@Param(value = "childId") String childId);
     List<ResourceType> getChildsByCode(@Param(value = "code") String code);
     ResourceType getByCode(@Param(value = "code") String code);
     List<ResourceType> findListByCode(@Param(value = "code") String code );
     List<ResourceType> findParentListByCode(@Param(value = "code") String code );
     List<ResourceType> getAllChilds();

     List<ResponseTypeNumStatistics> numStatistics();


}