
package com.jeeplus.modules.resourcetype.service;

import java.util.List;


import com.jeeplus.modules.resourcetype.entity.ResponseTypeNumStatistics;
import com.jeeplus.modules.sys.entity.Office;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.mapper.ResourceTypeMapper;

/**
 * 资源类型Service
 * @author le
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class ResourceTypeService extends TreeService<ResourceTypeMapper, ResourceType> {
	@Autowired
	private ResourceTypeMapper resourceTypeMapper;

	public ResourceType get(String id) {
		return super.get(id);
	}
	
	public List<ResourceType> findList(ResourceType resourceType) {
		if (StringUtils.isNotBlank(resourceType.getParentIds())){
			resourceType.setParentIds(","+resourceType.getParentIds()+",");
		}
		return super.findList(resourceType);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceType resourceType) {
		super.save(resourceType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceType resourceType) {
		super.delete(resourceType);
	}


	public List<ResourceType> getChildren(String parentId){
		return resourceTypeMapper.getChildren(parentId);
	}

	public List<ResourceType> getAllChilds(){
		return resourceTypeMapper.getAllChilds();
	}

	public ResourceType getParentByChild(String id) {
		return resourceTypeMapper.getParentByChild(id);
	}

	public List<ResourceType> getChildsByCode( String code){
		return resourceTypeMapper.getChildsByCode(code);
	}

	public ResourceType getByCode( String code){
		return resourceTypeMapper.getByCode(code);
	}


	public List<ResourceType> findListByCode(String code){
		return resourceTypeMapper.findListByCode(code);
	}

	public List<ResourceType> findParentListByCode(String code){
		return resourceTypeMapper.findParentListByCode(code);
	}

	public List<ResourceType> getChildrenOfSelect(String id){
		return resourceTypeMapper.getChildrenOfSelect(id);
	}


	public List<ResponseTypeNumStatistics> numStatistics(){
		return resourceTypeMapper.numStatistics();
	}
}