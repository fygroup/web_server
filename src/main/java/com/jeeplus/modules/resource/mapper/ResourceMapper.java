
package com.jeeplus.modules.resource.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resource.entity.*;
import org.apache.ibatis.annotations.Param;

import javax.xml.ws.Response;
import java.util.Date;
import java.util.List;

/**
 * 资源MAPPER接口
 * @author le
 * @version 2017-10-27
 */
@MyBatisMapper
public interface ResourceMapper extends BaseMapper<Resource> {

    int setMac(@Param(value = "mac") String mac,@Param(value = "id") String id);

    void saveResponseTime (@Param(value = "responseTime") ResponseTime responseTime);

    ResponseTime getTopResponseTime (@Param(value = "resourceId") String  resourceId );

    List<ResponseTime> findResponseTimeList (@Param(value = "resourceId") String  resourceId );

    Resource  checkIp(@Param(value = "ip") String  ip,@Param(value = "resourceType") String  resourceType);

    Resource  checkMiddlewareIp(@Param(value = "ip") String  ip,@Param(value = "accessConfigPort") String  accessConfigPort,@Param(value = "resourceType") String  resourceType,@Param(value = "accessConfigUserName") String  accessConfigUserName,@Param(value = "accessConfigPassword") String  accessConfigPassword);

    List<Resource> getListByCode (@Param(value = "code") String  code );

    String getResourceImg (@Param(value = "resourceId") String  resourceId );

    int updateResourceImg (@Param(value = "resourceId") String  resourceId ,@Param(value = "img") String  img);

    List<Resource> getByIpType (@Param(value = "ip") String  ip,@Param(value = "code") String  code );

    int updateResourceName (@Param(value = "id") String  id,@Param(value = "name") String  name );

    int setIndexTemplate(@Param(value = "id") String  id,@Param(value = "name") String  indexTemplateId );



     void  saveHealthDegree(@Param(value = "healthDegree") HealthDegree healthDegree);

     void  saveAvailabilityRate(@Param(value = "availabilityRate") AvailabilityRate availabilityRate);

    AvailabilityRate getTopAvailabilityRate(@Param(value = "resourceId") String resourceId);

    HealthDegree getTopHealthDegree(@Param(value = "resourceId") String resourceId);

    List<HealthDegree> findHealthDegreeList(@Param(value = "resourceId") String resourceId);



    List<HealthDegree> findHealthDegreeListDate(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );


    String findHealthDegreeListDateTotal(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );


    List<AvailabilityRate> findAvailabilityRateList(@Param(value = "resourceId") String resourceId);
    List<AvailabilityRate> findAvailabilityRateListDate(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );

    String findAvailabilityRateListDateTotal(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );


    List<MokaCollector> getCollectorList();

    MokaCollector getCollector(@Param(value = "id") String id);

}