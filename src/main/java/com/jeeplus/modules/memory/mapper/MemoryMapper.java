
package com.jeeplus.modules.memory.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 内存MAPPER接口
 * @author le
 * @version 2017-11-07
 */
@MyBatisMapper
public interface MemoryMapper extends BaseMapper<Memory> {
     void saveList(@Param(value = "list") List<Memory> list);
     void delByResourceId (@Param(value = "id") String id );
     void saveMemoryUsedRate (@Param(value = "memoryUsedRate") MemoryUsedRate memoryUsedRate );
     MemoryUsedRate getTopMemoryUsedRate (@Param(value = "resourceId") String  resourceId );

     List<MemoryUsedRate> findMemoryUsedRateList(String resourceId);


     List<MemoryUsedRate> findUsedRateListOfTime (@Param(value = "resourceId") String  resourceId , @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );

     List<MemoryUsedRate> findUsedRateListOfTimeType (@Param(value = "resourceTypeId") String  resourceTypeId , @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );


     String findTotalUsedRateListOfTime (@Param(value = "resourceId") String  resourceId , @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );


    List<MemoryUsedRate> getMemoryPanorama();
}