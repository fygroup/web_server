
package com.jeeplus.modules.disk.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.disk.entity.Disk;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 磁盘MAPPER接口
 * @author le
 * @version 2017-11-07
 */
@MyBatisMapper
public interface DiskMapper extends BaseMapper<Disk> {
     void saveList(@Param(value = "list") List<Disk> list);
     void delByResourceId (@Param(value = "id") String id );
     Disk getTopUsed(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );
}