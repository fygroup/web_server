/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcebaseinfo.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 资源访问信息MAPPER接口
 * @author le
 * @version 2017-10-31
 */
@MyBatisMapper
public interface ResourceBaseInfoMapper extends BaseMapper<ResourceBaseInfo> {
    int updateResourceRdcommunity (@Param(value = "id") String  id, @Param(value = "rdcommunity") String  rdcommunity );
}