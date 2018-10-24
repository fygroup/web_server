/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cpu.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * CPU信息MAPPER接口
 * @author le
 * @version 2017-11-07
 */
@MyBatisMapper
public interface CpuMapper extends BaseMapper<Cpu> {

     void saveList(@Param(value = "list") List<Cpu> list);
     void delByResourceId (@Param(value = "id") String id );

     void saveCpuUsedRate (@Param(value = "cpuUsedRate") CpuUsedRate cpuUsedRate );

     CpuUsedRate getTopCpuUsedRate (@Param(value = "resourceId") String  resourceId );

     List<CpuUsedRate> findUsedRateList (@Param(value = "resourceId") String  resourceId );

    List<CpuUsedRate> findUsedRateListOfTime (@Param(value = "resourceId") String  resourceId , @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );

    List<CpuUsedRate> findUsedRateListOfTimeType (@Param(value = "resourceTypeId") String  resourceTypeId , @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );

    String findTotalUsedRateListOfTime (@Param(value = "resourceId") String  resourceId , @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );


    List<CpuUsedRate> getCpuPanorama();

    List<Cpu> getWithResourceId(@Param("resourceId")String resourceId);
}