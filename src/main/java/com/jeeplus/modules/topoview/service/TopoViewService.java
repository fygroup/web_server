/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoview.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.topoline.mapper.TopoLineMapper;
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import com.jeeplus.modules.toposymbols.mapper.TopoSymbolsMapper;
import com.jeeplus.modules.toposymbols.service.TopoSymbolsService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.topoview.entity.TopoView;
import com.jeeplus.modules.topoview.mapper.TopoViewMapper;

import javax.xml.ws.Action;

/**
 * 拓扑视图Service
 * @author le
 * @version 2017-11-29
 */
@Service
@Transactional(readOnly = true)
public class TopoViewService extends CrudService<TopoViewMapper, TopoView> {

	@Autowired
	private TopoSymbolsMapper topoSymbolsMapper;


	@Autowired
	private TopoLineMapper topoLineMapper;

	public TopoView get(String id) {
		return super.get(id);
	}
	
	public List<TopoView> findList(TopoView topoView) {
		List<TopoView> topoViewList = super.findList(topoView);
		for(TopoView t : topoViewList){
			TopoSymbols topoSymbols = new TopoSymbols();
			topoSymbols.setView(t);
			List<TopoSymbols> topoSymbolsList = topoSymbolsMapper.findList(topoSymbols);
			if(topoSymbolsList.size() == 0){
				topoSymbolsList = Lists.newArrayList();
			}
			t.setTopoSymbolsList(topoSymbolsList);

			TopoLine topoLine = new TopoLine();
			topoLine.setView(t);
			List<TopoLine> topoLineList = topoLineMapper.findList(topoLine);
			if (topoLineList.size() == 0){
				topoLineList = Lists.newArrayList();
			}
			t.setTopoLineList(topoLineList);
		}
		return topoViewList;
	}


	public List<TopoView> getList(TopoView topoView) {
		List<TopoView> topoViewList = mapper.getTopoViewList(topoView);
		/*for(TopoView t : topoViewList){
			TopoSymbols topoSymbols = new TopoSymbols();
			topoSymbols.setView(t);
			List<TopoSymbols> topoSymbolsList = topoSymbolsMapper.findList(topoSymbols);
			if(topoSymbolsList.size() == 0){
				topoSymbolsList = Lists.newArrayList();
			}
			t.setTopoSymbolsList(topoSymbolsList);

			TopoLine topoLine = new TopoLine();
			topoLine.setView(t);
			List<TopoLine> topoLineList = topoLineMapper.findList(topoLine);
			if (topoLineList.size() == 0){
				topoLineList = Lists.newArrayList();
			}
			t.setTopoLineList(topoLineList);
		}*/
		return topoViewList;
	}



	
	public Page<TopoView> findPage(Page<TopoView> page, TopoView topoView) {
		return super.findPage(page, topoView);
	}
	
	@Transactional(readOnly = false)
	public void save(TopoView topoView) {
		super.save(topoView);
	}
	
	@Transactional(readOnly = false)
	public void delete(TopoView topoView) {
		super.delete(topoView);
		mapper.deleteTopoLine(topoView.getId());
		mapper.deleteTopoSymnols(topoView.getId());
	}


	@Transactional(readOnly = false)
	public int modifyTopoViewName(String id,String name) {
     return mapper.modifyTopoViewName(id,name);
	}
}