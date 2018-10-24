
package com.jeeplus.modules.software.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.process.entity.Process;
import com.jeeplus.modules.software.entity.Software;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 软件列表MAPPER接口
 * @author le
 * @version 2017-11-07
 */
@MyBatisMapper
public interface SoftwareMapper extends BaseMapper<Software> {
    public void saveList(@Param(value = "list") List<Software> list);

    public void delByResourceId (@Param(value = "id") String id );
}