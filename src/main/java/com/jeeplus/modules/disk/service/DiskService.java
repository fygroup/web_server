/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.disk.service;

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
import com.jeeplus.modules.software.entity.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.disk.mapper.DiskMapper;

/**
 * 磁盘Service
 * @author le
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class DiskService extends CrudService<DiskMapper, Disk> {

	@Autowired
	private DiskMapper diskMapper;


	@Autowired
	private IndicatorService indicatorService;


	@Autowired
	private ResourceGatherItemService resourceGatherItemService;


	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	public Disk get(String id) {
		return super.get(id);
	}

	public Disk getTopUsed(String resourceId,Date beginDate, Date endDate) {
		return mapper.getTopUsed(resourceId,beginDate, endDate);
	}
	
	public List<Disk> findList(Disk disk) {
		return super.findList(disk);
	}
	
	public Page<Disk> findPage(Page<Disk> page, Disk disk) {
		return super.findPage(page, disk);
	}
	
	@Transactional(readOnly = false)
	public void save(Disk disk) {
		super.save(disk);
	}
	
	@Transactional(readOnly = false)
	public void delete(Disk disk) {
		super.delete(disk);
	}


	@Transactional(readOnly = false)
	public void saveList(ResourceGatherItem resourceGatherItem, List<Disk> list, Resource resource,String diskSurplus) {
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}
		diskMapper.delByResourceId(resource.getId());
		diskMapper.saveList(list);

		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"10");
		if(CheckObject.checkList(updateList)){
			Date now=new Date();
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				if("10".equals(resourceIndicatorlist.getIndicator().getItemType())){   //磁盘剩余量
					resourceIndicatorlistMapper.setValue(diskSurplus,resourceIndicatorlist.getId(),now);
				}
			}
		}
	}
}