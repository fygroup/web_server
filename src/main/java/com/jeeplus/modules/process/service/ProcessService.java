/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.process.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.indicator.service.IndicatorService;
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
import com.jeeplus.modules.process.entity.Process;
import com.jeeplus.modules.process.mapper.ProcessMapper;

/**
 * 进程Service
 * @author le
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class ProcessService extends CrudService<ProcessMapper, Process> {
	@Autowired
	private ProcessMapper processMapper;

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;

	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	public Process get(String id) {
		return super.get(id);
	}
	
	public List<Process> findList(Process process) {
		return super.findList(process);
	}
	
	public Page<Process> findPage(Page<Process> page, Process process) {
		return super.findPage(page, process);
	}
	
	@Transactional(readOnly = false)
	public void save(Process process) {
		super.save(process);
	}

	@Transactional(readOnly = false)
	public void saveList(ResourceGatherItem resourceGatherItem, List<Process> list, Resource resource) {


		try {
			if (CheckObject.checkList(list)) {
				processMapper.delByResourceId(resource.getId());
			}
		}catch (Exception e){
			//System.out.println("========== "+resource.getId());
			//e.printStackTrace();
		}

		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}



		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"11");
		if(CheckObject.checkList(updateList)){
			Date now=new Date();
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				if("11".equals(resourceIndicatorlist.getIndicator().getItemType())){   //进程名称列表
					resourceIndicatorlistMapper.setValue("",resourceIndicatorlist.getId(),now);
				}
			}
		}

		if (CheckObject.checkList(list)) {
			processMapper.saveList(list);
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(Process process) {
		super.delete(process);
	}
	
}