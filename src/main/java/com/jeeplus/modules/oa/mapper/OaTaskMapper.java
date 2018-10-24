package com.jeeplus.modules.oa.mapper;


import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.oa.entity.OaTask;
import org.apache.ibatis.annotations.Param;

/**
 * 任务安排MAPPER接口
 * @author huanglei
 * @version 2018-04-10
 */
@MyBatisMapper
public interface OaTaskMapper extends BaseMapper<OaTask> {
    int setIsFlag(@Param(value = "taskId") String taskId);//传到xml 数据操作
    int setIsFlagFinish(@Param(value = "taskId") String taskId);

}
