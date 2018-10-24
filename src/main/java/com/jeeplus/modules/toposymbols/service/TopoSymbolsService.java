/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.toposymbols.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.core.persistence.dialect.db.H2Dialect;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.topoline.mapper.TopoLineMapper;
import com.jeeplus.modules.topoline.service.TopoLineService;
import com.jeeplus.modules.topoview.entity.TopoView;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import com.jeeplus.modules.toposymbols.mapper.TopoSymbolsMapper;

/**
 * 拓扑图资源UI信息Service
 * @author sun
 * @version 2017-11-29
 */
@Service
@Transactional(readOnly = true)
public class TopoSymbolsService extends CrudService<TopoSymbolsMapper, TopoSymbols> {
	@Autowired
	private TopoSymbolsMapper topoSymbolsMapper;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private TopoLineMapper topoLineMapper;

	@Autowired
	private TopoLineService topoLineService;

	@Autowired
	private LinkIndicatorService linkIndicatorService;

	public TopoSymbols get(String id) {
		return super.get(id);
	}
	
	public List<TopoSymbols> findList(TopoSymbols topoSymbols) {
		return super.findList(topoSymbols);
	}


	public List<TopoSymbols> getList(TopoSymbols topoSymbols) {
		return topoSymbolsMapper.getList(topoSymbols);
	}

	public List<TopoSymbols> findListByUser(String userId,String topoVIewId) {
		return topoSymbolsMapper.findListByUser(userId, topoVIewId);
	}


	public Page<TopoSymbols> findPage(Page<TopoSymbols> page, TopoSymbols topoSymbols) {
		return super.findPage(page, topoSymbols);
	}
	
	@Transactional(readOnly = false)
	public void save(TopoSymbols topoSymbols) {
		super.save(topoSymbols);
	}

	@Transactional(readOnly = false)
	public void saveSymbolsAndLines(String addSymbolsString,String addLineString){
		Date now=new Date();

		addSymbolsString = addSymbolsString.replaceAll("&quot;","'");
		addLineString = addLineString.replaceAll("&quot;","'");
		//添加拓扑图的相关资源
		List<TopoSymbols> symbolsList=new ArrayList<>();
		JSONArray symbolsarray = new JSONArray(addSymbolsString);
		for(int i=0;i<symbolsarray.length();i++) {
			JSONObject jsonObject = symbolsarray.getJSONObject(i);
			TopoSymbols topoSymbols=new TopoSymbols();
			topoSymbols.setId(jsonObject.getString("id"));
			topoSymbols.setName(jsonObject.getString("name"));
			topoSymbols.setX(jsonObject.getString("x"));
			topoSymbols.setY(jsonObject.getString("y"));
			topoSymbols.setView(new TopoView(jsonObject.getString("viewId")));
			topoSymbols.setObjectId(jsonObject.getString("objectId"));
			topoSymbols.setObjectClass(jsonObject.getString("objectClass"));
			topoSymbols.setStyle(jsonObject.getString("style"));
			int k =topoSymbolsMapper.insert(topoSymbols);
              if(k==0){
              	topoSymbolsMapper.update(topoSymbols);
			  }
		 symbolsList.add(topoSymbols);
		}

		//添加拓扑图的相关线路信息
		JSONArray lineArray = new JSONArray(addLineString);
		for(int i =0;i<lineArray.length();i++) {

			JSONObject jsonObject = lineArray.getJSONObject(i);
			TopoLine topoLine = new TopoLine();
			topoLine.setId(jsonObject.getString("id"));
			topoLine.setView(new TopoView(jsonObject.getString("viewId")));
			topoLine.setName(jsonObject.getString("name"));
			topoLine.setPath(jsonObject.getString("path"));
			topoLine.setType(jsonObject.getString("type"));
			topoLine.setSrcSymbol(jsonObject.getString("srcSymbol"));
			topoLine.setDstSymbol(jsonObject.getString("dstSymbol"));
			topoLine.setObjectId(jsonObject.getString("objectId"));
			topoLine.setObjectClass(jsonObject.getString("objectClass"));
			topoLine.setStyle(jsonObject.getString("style"));
			topoLine.setLineType("1");
			int k = topoLineMapper.insert(topoLine);
			if(k == 0){
				TopoLine oldTopoLine =topoLineService.get(topoLine.getId());
				if (oldTopoLine!=null){
					topoLine.setLineType(oldTopoLine.getLineType());
				}
			    topoLineMapper.update(topoLine);
			}
		}

      if(CheckObject.checkList(symbolsList)){
		  List<String> pairResult=new ArrayList<>();
			List<String> pairedResourceId=new ArrayList<>();
			for(TopoSymbols up:symbolsList){
					for (TopoSymbols down : symbolsList) {
						if (!up.getObjectId().equals(down.getObjectId()) && !pairedResourceId.contains(up.getObjectId()+","+down.getObjectId())&& !pairedResourceId.contains(down.getObjectId()+","+up.getObjectId())) {
							int num = linkIndicatorService.getByTwoResource(up.getObjectId(), down.getObjectId());
							if (num > 0) {
								pairedResourceId.add(up.getObjectId()+","+down.getObjectId());
								pairedResourceId.add(down.getObjectId()+","+up.getObjectId());
								pairResult.add(up.getObjectId()+","+down.getObjectId()+","+up.getId()+","+down.getId()+","+up.getView().getId());
							}
						}
					}
				}

			List<TopoLine> lineList=new ArrayList<>();
			String viewId=null;
			for(String item : pairResult){
				String[] items=item.split(",");
				if(items.length!=5){
					continue;
				}
				Resource up=resourceService.get(items[0]);
				Resource down=resourceService.get(items[1]);
				if(up==null||down==null){
					continue;
				}
				TopoLine topoLine=new TopoLine();
				topoLine.setId(IdGen.uuid());
				topoLine.setCreateDate(now);
				topoLine.setName(up.getName()+" -&gt; "+down.getName());
				topoLine.setSrcSymbol(items[2]);
				topoLine.setDstSymbol(items[3]);
				topoLine.setType("0");
				TopoSymbols srcSymbols = getSymbolWithResourceIdAndTopoViewId(items[0],items[4]);
				TopoSymbols dstSymbols = getSymbolWithResourceIdAndTopoViewId(items[1],items[4]);
				topoLine.setPath("M "+srcSymbols.getX()+" "+srcSymbols.getY() +" L "+dstSymbols.getX()+" "+dstSymbols.getY());
				topoLine.setLineType("2");
				topoLine.setObjectClass("2");
				topoLine.setStyle("dashed:8,4,14,4;border:0;border-color:;weight:4;");
				topoLine.setView(new TopoView(items[4]));
				String objectId=linkIndicatorService.getId(items[0],items[1]);
				topoLine.setObjectId(objectId);
				if(viewId==null){
					viewId=items[4];
				}
				/*if(topoLineService.checkLinkExist(viewId,objectId)==0){
					lineList.add(topoLine);
				}*/
				lineList.add(topoLine);
			}

		  if(viewId!=null){
			  topoLineMapper.delByViewId(viewId);
		  }
		  if(lineList.size()>0){
			  topoLineMapper.saveList(lineList);
		  }

	  }
	}

	@Transactional(readOnly = false)
	public void saveList(List<TopoSymbols> list) {
		topoSymbolsMapper.saveList(list);
	}

	@Transactional(readOnly = false)
	public void saveList(List<TopoSymbols> symList,List<TopoLine> lineList) {

		if (CheckObject.checkList(symList)){
			topoSymbolsMapper.saveList(symList);
		}

		if (CheckObject.checkList(lineList)){
			topoLineMapper.saveList(lineList);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TopoSymbols topoSymbols) {
		super.delete(topoSymbols);
	}



	public String getSymbolIdByResourceIdAndTopoViewId(String resourceId, String viewId) {

		return topoSymbolsMapper.getSymbolIdByResourceIdAndTopoViewId(resourceId,viewId);
	}


	public TopoSymbols getSymbolWithResourceIdAndTopoViewId(String resourceId, String viewId) {

		return topoSymbolsMapper.getSymbolWithResourceIdAndTopoViewId(resourceId,viewId);
	}

	@Transactional(readOnly = false)
	public void deleteSymbolsLinkLine(String[] symbolIds, String[] lineIds){

		if(symbolIds!=null&&symbolIds.length>0){
			for(String id : symbolIds){
				TopoSymbols topoSymbols = new TopoSymbols();
				topoSymbols.setId(id);
				topoSymbolsMapper.delete(topoSymbols);
                topoSymbolsMapper.deleteSymbolsLinkLine(id);
			}
		}
		if(lineIds!=null&&lineIds.length>0){
			for(String id:lineIds){
				TopoLine topoLine = new TopoLine();
				topoLine.setId(id);
				topoLineService.delete(topoLine);
			}
		}
	}
}