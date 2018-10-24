/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcephysicinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resourcephysicinfo.entity.ResourcePhysicInfo;
import com.jeeplus.modules.resourcephysicinfo.mapper.ResourcePhysicInfoMapper;

/**
 * 资源物理信息Service
 * @author le
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class ResourcePhysicInfoService extends CrudService<ResourcePhysicInfoMapper, ResourcePhysicInfo> {
	@Autowired
	private ResourcePhysicInfoMapper resourcePhysicInfoMapper;

	public ResourcePhysicInfo get(String id) {
		return super.get(id);
	}
	
	public List<ResourcePhysicInfo> findList(ResourcePhysicInfo resourcePhysicInfo) {
		return super.findList(resourcePhysicInfo);
	}
	
	public Page<ResourcePhysicInfo> findPage(Page<ResourcePhysicInfo> page, ResourcePhysicInfo resourcePhysicInfo) {
		return super.findPage(page, resourcePhysicInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourcePhysicInfo resourcePhysicInfo) {
		super.save(resourcePhysicInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourcePhysicInfo resourcePhysicInfo) {
		super.delete(resourcePhysicInfo);
	}


	public ResourcePhysicInfo getByResourceId(String resourceId) {
		return resourcePhysicInfoMapper.getByResourceId(resourceId);
	}

}