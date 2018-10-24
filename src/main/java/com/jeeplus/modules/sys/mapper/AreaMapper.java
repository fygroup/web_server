
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

/**
 * 区域MAPPER接口
 * @author clutek
 * @version 2017-05-16
 */
@MyBatisMapper
public interface AreaMapper extends TreeMapper<Area> {
    Area  getByOffice(@Param(value = "officeId") String officeId);
}
