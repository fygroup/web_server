/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoline.service;

import java.util.List;

import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import com.jeeplus.modules.toposymbols.mapper.TopoSymbolsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.topoline.mapper.TopoLineMapper;

/**
 * 拓扑图线条位置信息Service
 * @author sun
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class TopoLineService extends CrudService<TopoLineMapper, TopoLine> {

	@Autowired
	private TopoLineMapper topoLineMapper;

	public TopoLine get(String id) {
		return super.get(id);
	}
	
	public List<TopoLine> findList(TopoLine topoLine) {
		return super.findList(topoLine);
	}
	
	public Page<TopoLine> findPage(Page<TopoLine> page, TopoLine topoLine) {
		return super.findPage(page, topoLine);
	}
	
	@Transactional(readOnly = false)
	public void save(TopoLine topoLine) {
		super.save(topoLine);
	}
	
	@Transactional(readOnly = false)
	public void delete(TopoLine topoLine) {
		super.delete(topoLine);
	}

	@Transactional(readOnly = false)
	public void saveList(List<TopoLine> list) {
		topoLineMapper.saveList(list);
	}

	@Transactional(readOnly = false)
	public int delByViewId(@Param(value = "viewId") String viewId){
		return topoLineMapper.delByViewId(viewId);
	}

	public List<TopoLine> lineIdsByClass( String viewId){
		return topoLineMapper.lineIdsByClass(viewId);
	}

	public int checkLinkExist(String viewId, String objectId){
		return topoLineMapper.checkLinkExist(viewId,objectId);
	}

	@Transactional(readOnly = false)
	public void delByResourceId( String resourceId){
		topoLineMapper.delByResourceId(resourceId);
	}
}