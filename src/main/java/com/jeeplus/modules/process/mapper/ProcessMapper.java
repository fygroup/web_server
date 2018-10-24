/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.process.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.process.entity.Process;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 进程MAPPER接口
 * @author le
 * @version 2017-11-07
 */
@MyBatisMapper
public interface ProcessMapper extends BaseMapper<Process> {

    public void saveList(@Param(value = "list") List<Process> list);

    public void delByResourceId (@Param(value = "id") String id );
	
}