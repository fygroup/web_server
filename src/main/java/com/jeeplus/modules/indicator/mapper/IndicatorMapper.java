
package com.jeeplus.modules.indicator.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.indicator.entity.Indicator;
import org.apache.ibatis.annotations.Param;

/**
 * 指标列表MAPPER接口
 * @author le
 * @version 2017-11-13
 */
@MyBatisMapper
public interface IndicatorMapper extends BaseMapper<Indicator> {
    Indicator getByType(@Param(value = "type") String type);
}