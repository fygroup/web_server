/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceconfiginfo.service;

import java.util.List;

import com.jeeplus.modules.resourcephysicinfo.entity.ResourcePhysicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resourceconfiginfo.entity.ResourceConfigInfo;
import com.jeeplus.modules.resourceconfiginfo.mapper.ResourceConfigInfoMapper;

/**
 * 资源配置信息Service
 * @author le
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class ResourceConfigInfoService extends CrudService<ResourceConfigInfoMapper, ResourceConfigInfo> {

	@Autowired
	private ResourceConfigInfoMapper resourceConfigInfoMapper;

	public ResourceConfigInfo get(String id) {
		return super.get(id);
	}
	
	public List<ResourceConfigInfo> findList(ResourceConfigInfo resourceConfigInfo) {
		return super.findList(resourceConfigInfo);
	}
	
	public Page<ResourceConfigInfo> findPage(Page<ResourceConfigInfo> page, ResourceConfigInfo resourceConfigInfo) {
		return super.findPage(page, resourceConfigInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceConfigInfo resourceConfigInfo) {
		super.save(resourceConfigInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceConfigInfo resourceConfigInfo) {
		super.delete(resourceConfigInfo);
	}

	public ResourceConfigInfo getByResourceId(String resourceId) {
		return resourceConfigInfoMapper.getByResourceId(resourceId);
	}
}