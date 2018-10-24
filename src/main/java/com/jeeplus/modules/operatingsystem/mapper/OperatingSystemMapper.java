/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.operatingsystem.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.operatingsystem.entity.OperatingSystem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作系统MAPPER接口
 * @author le
 * @version 2017-11-01
 */
@MyBatisMapper
public interface OperatingSystemMapper extends BaseMapper<OperatingSystem> {

    List<OperatingSystem> findListByServerId(@Param(value = "serverId") String serverId);

}