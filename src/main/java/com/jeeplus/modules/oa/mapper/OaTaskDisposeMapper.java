package com.jeeplus.modules.oa.mapper;


import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.oa.entity.OaTask;
import com.jeeplus.modules.oa.entity.OaTaskDispose;
import org.apache.ibatis.annotations.Param;

/**
 * 任务处理MAPPER接口
 * @author huanglei
 * @version 2018-04-11
 */
@MyBatisMapper
public interface OaTaskDisposeMapper extends BaseMapper<OaTaskDispose> {

    int amountdispose (@Param(value = "taskId") String  taskId);
    int amountfinish (@Param(value = "taskId") String  taskId);
}
