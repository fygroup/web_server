/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceindicatorlist.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.modules.resourceindicatorlist.entity.UserResourceIndicatorlist;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.mapper.ResourceIndicatorlistMapper;

/**
 * 资源指标列表Service
 * @author le
 * @version 2017-11-14
 */
@Service
@Transactional(readOnly = true)
public class ResourceIndicatorlistService extends CrudService<ResourceIndicatorlistMapper, ResourceIndicatorlist> {

	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	public ResourceIndicatorlist get(String id) {
		return super.get(id);
	}
	
	public List<ResourceIndicatorlist> findList(ResourceIndicatorlist resourceIndicatorlist) {
		return super.findList(resourceIndicatorlist);
	}



	public List<ResourceIndicatorlist> findListByUser(String  userId,String type) {
		return resourceIndicatorlistMapper.findListByUser(userId,type);
	}


	public Page<ResourceIndicatorlist> findPage(Page<ResourceIndicatorlist> page, ResourceIndicatorlist resourceIndicatorlist) {
		return super.findPage(page, resourceIndicatorlist);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceIndicatorlist resourceIndicatorlist) {
		super.save(resourceIndicatorlist);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceIndicatorlist resourceIndicatorlist) {
		super.delete(resourceIndicatorlist);
	}

	@Transactional(readOnly = false)
	public void delById(String id) {
		resourceIndicatorlistMapper.delById(id);
	}


	@Transactional(readOnly = false)
	public void delByResourceId(String id) {
		resourceIndicatorlistMapper.delByResourceId(id);
	}


	public List<ResourceIndicatorlist> findListByResourceId(String resourceId){
		return resourceIndicatorlistMapper.findListByResourceId(resourceId);
	}

	public List<String> findIdsByResource( String resourceId){
		return resourceIndicatorlistMapper.findIdsByResource(resourceId);
	}

	public ResourceIndicatorlist findResourceIndicator( String resourceId,String indicatorId){
		return resourceIndicatorlistMapper.findResourceIndicator(resourceId,indicatorId);
	}



	/**
	 * 批量插入
	 * @param resourceId
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void saveList( String resourceId,List<ResourceIndicatorlist> list,String eventType){
		 resourceIndicatorlistMapper.delAllOfResourceByEventType(resourceId,eventType);
		 resourceIndicatorlistMapper.saveList(list);
	}


	/**
	 * 用户与指标关系批量插入
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void saveUserIndicatorList( List<UserResourceIndicatorlist> list,String userId,String type){
		resourceIndicatorlistMapper.delByUser(userId,type);
		if(CheckObject.checkList(list)){
			resourceIndicatorlistMapper.saveUserIndicatorList(list);
		}

	}


	/**
	 * 插入操作前删除不在当前指标里的数据
	 * @param resourceId
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delByIndicatorIds( String resourceId,String ids){
		return resourceIndicatorlistMapper.delByIndicatorIds(resourceId,"("+ids+")");
	}


	/**
	 *
	 * @param value
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void setValue(String value,String id){
		resourceIndicatorlistMapper.setValue(value,id,new Date());
	}

	/**
	 * 获取网络设备和服务器资源所属指标列表
	 * @param resourceId
	 * @param resourceTypeId
	 * @param operatingSystemid
	 * @param type
	 * @return
	 */
	public List<ResourceIndicatorlist> getUpdateList(String resourceId, String resourceTypeId, String operatingSystemid, String type){
		return resourceIndicatorlistMapper.getUpdateList(resourceId,  resourceTypeId,  operatingSystemid,  type);
	}

	/**
	 * 根据资源类型获取当前资源的指标列表
	 * @param resourceId
	 * @param resourceTypeId
	 * @return
	 */
	public List<ResourceIndicatorlist> getUpdateListByCode(String resourceId, String resourceTypeId,String code){
		return resourceIndicatorlistMapper.getUpdateListByCode(resourceId,  resourceTypeId,code);
	}


	public String getTitle(String type,String userId){
		return resourceIndicatorlistMapper.getTitle(type,userId);
	}
}