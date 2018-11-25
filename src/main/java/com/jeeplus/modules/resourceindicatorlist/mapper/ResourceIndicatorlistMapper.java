package com.jeeplus.modules.resourceindicatorlist.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.entity.UserResourceIndicatorlist;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 资源指标列表MAPPER接口
 * @author le
 * @version 2017-11-14
 */
@MyBatisMapper
public interface ResourceIndicatorlistMapper extends BaseMapper<ResourceIndicatorlist> {
    List<ResourceIndicatorlist> findListByResourceId(@Param(value = "resourceId") String resourceId);
    List<ResourceIndicatorlist> findListByUser(@Param(value = "userId") String userId,@Param(value = "type")  String type);

    List<String> findIdsByResource(@Param(value = "resourceId") String resourceId);

    int delByIndicatorIds(@Param(value = "resourceId") String resourceId, @Param(value = "ids") String ids);

    int delAllOfResourceByEventType(@Param(value = "resourceId") String resourceId,@Param(value = "eventType") String eventType);


    void saveList(@Param(value = "list")  List<ResourceIndicatorlist> list);

    void saveUserIndicatorList(@Param(value = "list")  List<UserResourceIndicatorlist> list);
    void delByUser(@Param(value = "userId")  String userId,@Param(value = "type")  String type);



    void delById(@Param(value = "id")  String id);

    void delByResourceId(@Param(value = "id")  String id);

     ResourceIndicatorlist findResourceIndicator( @Param(value = "resourceId")String resourceId,@Param(value = "indicatorId")String indicatorId);

    List<ResourceIndicatorlist> getUpdateList(@Param(value = "resourceId")String resourceId,@Param(value = "resourceTypeId")String resourceTypeId,@Param(value = "operatingSystemid")String operatingSystemid,@Param(value = "type")String type);

    /**
     * 根据资源类型获取当前资源的指标列表
     * @param resourceId
     * @param resourceTypeId
     * @param code
     * @return
     */
    List<ResourceIndicatorlist> getUpdateListByCode(@Param(value = "resourceId")String resourceId,@Param(value = "resourceTypeId")String resourceTypeId,@Param(value = "code")String code);



    void setValue(@Param(value = "value")String value,@Param(value = "id")String id,@Param(value = "updateDate")Date updateDate);

    String getTitle(@Param(value = "type")String type,@Param(value = "userId")String userId);
}