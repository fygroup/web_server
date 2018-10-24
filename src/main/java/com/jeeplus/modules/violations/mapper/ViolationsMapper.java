/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.violations.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.violations.entity.Violations;
import org.apache.ibatis.annotations.Param;


/**
 * 违规事件 MAPPER接口
 * @author lei
 * @version 2018-09-28
 */
@MyBatisMapper
public interface ViolationsMapper extends BaseMapper<Violations> {

      Violations getTopViolations(@Param(value = "resourceId") String resourceId);

}