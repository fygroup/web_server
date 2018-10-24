/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceinformation.service;

import java.util.List;

import com.jeeplus.modules.resourceconfiginfo.entity.ResourceConfigInfo;
import com.jeeplus.modules.resourceinformation.entity.ResourceInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resourceinformation.mapper.ResourceInformationMapper;

/**
 * 资源信息表Service
 * @author le
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class ResourceInformationService extends CrudService<ResourceInformationMapper, ResourceInformation> {
	@Autowired
	private ResourceInformationMapper resourceInformationMapper;

	public ResourceInformation get(String id) {
		return super.get(id);
	}
	
	public List<ResourceInformation> findList(ResourceInformation resourceInformation) {
		return super.findList(resourceInformation);
	}
	
	public Page<ResourceInformation> findPage(Page<ResourceInformation> page, ResourceInformation resourceInformation) {
		return super.findPage(page, resourceInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceInformation resourceInformation) {
		super.save(resourceInformation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceInformation resourceInformation) {
		super.delete(resourceInformation);
	}

	public ResourceInformation getByResourceId(String resourceId) {
		return resourceInformationMapper.getByResourceId(resourceId);
	}
}