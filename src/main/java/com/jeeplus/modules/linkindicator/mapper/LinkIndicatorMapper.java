/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.linkindicator.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 链路指标MAPPER接口
 * @author le
 * @version 2017-11-30
 */
@MyBatisMapper
public interface LinkIndicatorMapper extends BaseMapper<LinkIndicator> {
    LinkIndicator getByResource(@Param(value = "resourceId")String resourceId);
    int getByTwoResource(@Param(value = "upId")String upId, @Param(value = "downId")String downId);
    int checkExist(@Param(value = "interfaceId")String interfaceId);
    int delByResource(@Param(value = "resourceId")String resourceId);
    List<String> getId(@Param(value = "up")String up,@Param(value = "down")String down);

    List<LinkIndicator> getListByLinkEquequipment(@Param(value = "resourceId")String resourceId);
}