/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoline.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.topoline.entity.TopoLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拓扑图线条位置信息MAPPER接口
 * @author sun
 * @version 2017-11-30
 */
@MyBatisMapper
public interface TopoLineMapper extends BaseMapper<TopoLine> {

    void saveList(@Param(value = "list") List<TopoLine> list);

    int delByViewId(@Param(value = "viewId") String viewId);

    void delByResourceId(@Param(value = "resourceId") String resourceId);

    List<TopoLine> lineIdsByClass(@Param(value = "viewId") String viewId);
    int checkLinkExist(@Param(value = "viewId") String viewId,@Param(value = "objectId") String objectId);
}