/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.linkindicator.service;

import java.util.List;

import com.jeeplus.common.utils.CheckObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.mapper.LinkIndicatorMapper;

/**
 * 链路指标Service
 * @author le
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class LinkIndicatorService extends CrudService<LinkIndicatorMapper, LinkIndicator> {
	@Autowired
	private LinkIndicatorMapper linkIndicatorMapper;

	public LinkIndicator get(String id) {
		return super.get(id);
	}
	
	public List<LinkIndicator> findList(LinkIndicator linkIndicator) {
		return super.findList(linkIndicator);
	}
	
	public Page<LinkIndicator> findPage(Page<LinkIndicator> page, LinkIndicator linkIndicator) {
		return super.findPage(page, linkIndicator);
	}
	
	@Transactional(readOnly = false)
	public void save(LinkIndicator linkIndicator) {
		super.save(linkIndicator);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinkIndicator linkIndicator) {
		super.delete(linkIndicator);
	}

	public LinkIndicator getByResource(String resourceId){
		return linkIndicatorMapper.getByResource(resourceId);
	}

	public int getByTwoResource(String upId,String downId){
		return linkIndicatorMapper.getByTwoResource(upId,downId);
	}

	//检查当前链路接口是否已经添加
	public int checkExist(String interfaceId){
		return linkIndicatorMapper.checkExist(interfaceId);
	}

	@Transactional(readOnly = false)
	public int delByResource(String resourceId){
		return linkIndicatorMapper.delByResource(resourceId);
	}

	public String getId(String up,String down){
		List<String> list= linkIndicatorMapper.getId(up,down);
		if(CheckObject.checkList(list)){
			return list.get(0);
		}
		return null;
	}

	public List<LinkIndicator> getListByLinkEquequipment(String resourceId){
		return linkIndicatorMapper.getListByLinkEquequipment(resourceId);
	}
}