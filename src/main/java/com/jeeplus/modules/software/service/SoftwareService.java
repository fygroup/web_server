/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.software.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.indicator.service.IndicatorService;
import com.jeeplus.modules.process.entity.Process;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.mapper.ResourceIndicatorlistMapper;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.software.entity.Software;
import com.jeeplus.modules.software.mapper.SoftwareMapper;

/**
 * 软件列表Service
 * @author le
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class SoftwareService extends CrudService<SoftwareMapper, Software> {
	@Autowired
	private SoftwareMapper softwareMapper;

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;

	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	public Software get(String id) {
		return super.get(id);
	}
	
	public List<Software> findList(Software software) {
		return super.findList(software);
	}
	
	public Page<Software> findPage(Page<Software> page, Software software) {
		return super.findPage(page, software);
	}
	
	@Transactional(readOnly = false)
	public void save(Software software) {
		super.save(software);
	}
	
	@Transactional(readOnly = false)
	public void delete(Software software) {
		super.delete(software);
	}


	@Transactional(readOnly = false)
	public void saveList(ResourceGatherItem resourceGatherItem, List<Software> list, Resource resource) {
		//System.out.println("软件开始插入 ++++++++++++++++ "+list.size());
		Long start=System.currentTimeMillis();
		try {
			if(CheckObject.checkList(list)) {
				softwareMapper.delByResourceId(resource.getId());
			}
		}catch (Exception e){
			//System.out.println("==========s "+resource.getId());
			//e.printStackTrace();
		}

		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}

		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"12");
		if(CheckObject.checkList(updateList)){
			Date now=new Date();
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				if("12".equals(resourceIndicatorlist.getIndicator().getItemType())){   //软件名称列表
					resourceIndicatorlistMapper.setValue("",resourceIndicatorlist.getId(),now);
				}
			}
		}

			if(CheckObject.checkList(list)) {
				softwareMapper.saveList(list);
			}


		//System.out.println("进程插入结束 ++++++++++++++++ "+(System.currentTimeMillis()-start));
	}
}