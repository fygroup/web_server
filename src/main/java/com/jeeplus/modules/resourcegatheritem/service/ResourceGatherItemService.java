/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcegatheritem.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.mapper.ResourceGatherItemMapper;

/**
 * 资源指标采集项Service
 * @author le
 * @version 2017-11-18
 */
@Service
@Transactional(readOnly = true)
public class ResourceGatherItemService extends CrudService<ResourceGatherItemMapper, ResourceGatherItem> {

	@Autowired
	private ResourceGatherItemMapper resourceGatherItemMapper;

	public ResourceGatherItem get(String id) {
		return super.get(id);
	}
	
	public List<ResourceGatherItem> findList(ResourceGatherItem resourceGatherItem) {
		return super.findList(resourceGatherItem);
	}
	
	public Page<ResourceGatherItem> findPage(Page<ResourceGatherItem> page, ResourceGatherItem resourceGatherItem) {
		return super.findPage(page, resourceGatherItem);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceGatherItem resourceGatherItem) {
		super.save(resourceGatherItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceGatherItem resourceGatherItem) {
		super.delete(resourceGatherItem);
	}


	@Transactional(readOnly = false)
	public void delByResourceId( String resourceId){
		resourceGatherItemMapper.delByResourceId(resourceId);
	}

	public List<ResourceGatherItem> findListByResource(String resourceId) {
		return resourceGatherItemMapper.findListByResource(resourceId);
	}


}