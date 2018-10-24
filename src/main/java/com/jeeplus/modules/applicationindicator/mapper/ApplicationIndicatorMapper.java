package com.jeeplus.modules.applicationindicator.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicatorValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 应用指标MAPPER接口
 * @author le
 * @version 2017-12-12
 */
@MyBatisMapper
public interface ApplicationIndicatorMapper extends BaseMapper<ApplicationIndicator> {

    void saveList(@Param(value = "list") List<ApplicationIndicator> list);

    void saveValueList(@Param(value = "list") List<ApplicationIndicatorValue> list);

    List<ApplicationIndicator> findListByResource(@Param(value = "resourceId") String resourceId);

    ApplicationIndicatorValue getTopValue(@Param(value = "id") String id);

}
