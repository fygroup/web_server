/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.loophole.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.attack.entity.Attack;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.loophole.entity.Loophole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 安全攻击MAPPER接口
 * @author lei
 * @version 2018-09-28
 */
@MyBatisMapper
public interface LoopholeMapper extends BaseMapper<Loophole> {

//     void saveList(@Param(value = "list") List <Attack> list);
//     void delByResourceId(@Param(value = "id") String id);

//     void saveCpuUsedRate(@Param(value = "cpuUsedRate") CpuUsedRate cpuUsedRate);

      Loophole getTopLoophole(@Param(value = "resourceId") String resourceId);

//     List<CpuUsedRate> findUsedRateList(@Param(value = "resourceId") String resourceId);
//
//    List<CpuUsedRate> findUsedRateListOfTime(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate, @Param(value = "endDate") Date endDate);
//
//    List<CpuUsedRate> findUsedRateListOfTimeType(@Param(value = "resourceTypeId") String resourceTypeId, @Param(value = "beginDate") Date beginDate, @Param(value = "endDate") Date endDate);
//
//    String findTotalUsedRateListOfTime(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate, @Param(value = "endDate") Date endDate);
//
//
//    List<CpuUsedRate> getCpuPanorama();

//    List<Cpu> getWithResourceId(@Param("resourceId") String resourceId);
}