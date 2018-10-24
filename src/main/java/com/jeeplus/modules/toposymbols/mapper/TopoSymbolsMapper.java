/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.toposymbols.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拓扑图资源UI信息MAPPER接口
 * @author sun
 * @version 2017-11-29
 */
@MyBatisMapper
public interface TopoSymbolsMapper extends BaseMapper<TopoSymbols> {

     void saveList(@Param(value = "list") List<TopoSymbols> list);
     String getSymbolIdByResourceIdAndTopoViewId(@Param(value = "resourceId")String resourceId, @Param(value = "viewId")String viewId);
     TopoSymbols getSymbolWithResourceIdAndTopoViewId(@Param(value = "resourceId")String resourceId, @Param(value = "viewId")String viewId);
     void deleteSymbolsLinkLine(@Param(value = "id")String id);


    List<TopoSymbols> findListByUser(@Param(value = "userId") String userId,@Param(value = "topoVIewId") String topoVIewId);

    List<TopoSymbols> getList(@Param(value = "topoSymbols") TopoSymbols topoSymbols);
}