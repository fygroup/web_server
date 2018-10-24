package com.jeeplus.modules.exception.mapper.exception;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 异常告警信息MAPPER接口
 * @author clouology
 * @version 2017-11-16
 */
@MyBatisMapper
public interface ResourceExceptionMapper extends BaseMapper<ResourceException> {

    ResourceException getByResourceIndicatorId(@Param(value = "resourceIndicatorId") String resourceIndicatorId);

    int manualRecovery(@Param(value = "id") String  id,@Param(value = "updateDate") Date updateDate, @Param(value = "currentStatus") String currentStatus , @Param(value = "confirmStatus") String confirmStatus,@Param(value = "confirmUserId") String confirmUserId);

    int confirmException(@Param(value = "id") String  id,@Param(value = "updateDate") Date updateDate, @Param(value = "confirmStatus") String confirmStatus, @Param(value = "confirmUserId") String confirmUserId);

    List<ResourceException> findListByResource(@Param(value = "resourceId") String resourceId,@Param(value = "exceptionClass")String exceptionClass);

    List<ResourceException> findListByResourceDate(@Param(value = "resourceId") String resourceId,@Param(value = "exceptionClass")String exceptionClass, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );



    int delete(@Param(value = "id") String id);

    List<ResourceException> indexFindList();

}