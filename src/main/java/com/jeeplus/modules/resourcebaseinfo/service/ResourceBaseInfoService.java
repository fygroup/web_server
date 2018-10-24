/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcebaseinfo.service;

import java.util.List;

import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resourcebaseinfo.mapper.ResourceBaseInfoMapper;

/**
 * 资源访问信息Service
 * @author le
 * @version 2017-10-31
 */
@Service
@Transactional(readOnly = true)
public class ResourceBaseInfoService extends CrudService<ResourceBaseInfoMapper, ResourceBaseInfo> {

	public ResourceBaseInfo get(String id) {
		return super.get(id);
	}
	
	public List<ResourceBaseInfo> findList(ResourceBaseInfo resourceBaseInfo) {
		return super.findList(resourceBaseInfo);
	}
	
	public Page<ResourceBaseInfo> findPage(Page<ResourceBaseInfo> page, ResourceBaseInfo resourceBaseInfo) {
		return super.findPage(page, resourceBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceBaseInfo resourceBaseInfo) {
		super.save(resourceBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceBaseInfo resourceBaseInfo) {
		super.delete(resourceBaseInfo);
	}
	
}