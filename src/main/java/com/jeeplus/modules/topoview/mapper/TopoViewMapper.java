/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoview.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.topoview.entity.TopoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拓扑视图MAPPER接口
 * @author le
 * @version 2017-11-29
 */
@MyBatisMapper
public interface TopoViewMapper extends BaseMapper<TopoView> {

    void deleteTopoLine(@Param(value="id")String id);

    void deleteTopoSymnols(@Param(value="id")String id);

    int modifyTopoViewName(@Param(value="id")String id,@Param(value="name")String name);

    List<TopoView> getTopoViewList(@Param(value = "topoView") TopoView topoView);
}