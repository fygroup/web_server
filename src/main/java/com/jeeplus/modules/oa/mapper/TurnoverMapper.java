package com.jeeplus.modules.oa.mapper;


import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.oa.entity.OaOutRecords;
import com.jeeplus.modules.oa.entity.Turnover;

/**
 * 进出记录 MAPPER 接口
 * @Author huanglei
 * @Date 2017/11/10 上午 10:38
 */

@MyBatisMapper
public interface TurnoverMapper extends BaseMapper<Turnover>{
    /**
     * 更新流程实例ID
     * @param turnover
     * @return
     */
    public int updateProcessInstanceId(Turnover turnover);

    /**
     * 更新实际开始结束时间
     * @param turnover
     * @return
     */
    public int updateRealityTime(Turnover turnover);
}
