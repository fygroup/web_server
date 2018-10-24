package com.jeeplus.modules.oa.mapper;


import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.oa.entity.OaDispose;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 问题处理MAPPER接口
 * @author huanglei
 * @version 2017-11-06
 */
@MyBatisMapper
public interface OaDisposeMapper extends BaseMapper<OaDispose> {
}
