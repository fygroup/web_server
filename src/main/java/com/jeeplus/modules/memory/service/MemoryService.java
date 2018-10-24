package com.jeeplus.modules.memory.service;

import java.util.Date;
import java.util.List;
import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indicator.service.IndicatorService;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
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
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.mapper.MemoryMapper;
import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;

/**
 * 内存Service
 * @author le
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class MemoryService extends CrudService<MemoryMapper, Memory> {

	@Autowired
	private MemoryMapper memoryMapper;


	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;

	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	@Autowired
	private ResourceExceptionService resourceExceptionService;


	public Memory get(String id) {
		return super.get(id);
	}
	
	public List<Memory> findList(Memory memory) {
		return super.findList(memory);
	}


	public List<MemoryUsedRate> findMemoryUsedRateList(String resourceId) {
		return memoryMapper.findMemoryUsedRateList(resourceId);
	}


	public List<MemoryUsedRate> findUsedRateListOfTime(String resourceId, Date beginDate, Date endDate) {
		return memoryMapper.findUsedRateListOfTime(resourceId,beginDate,  endDate);
	}

	public List<MemoryUsedRate> findUsedRateListOfTimetype(String resourceTypeId, Date beginDate, Date endDate) {
		return memoryMapper.findUsedRateListOfTimeType(resourceTypeId,beginDate,  endDate);
	}

	public String findTotalUsedRateListOfTime(String resourceId,Date beginDate, Date endDate) {
		return memoryMapper.findTotalUsedRateListOfTime(resourceId,beginDate,  endDate);
	}


	public Page<Memory> findPage(Page<Memory> page, Memory memory) {
		return super.findPage(page, memory);
	}
	
	@Transactional(readOnly = false)
	public void save(Memory memory) {
		super.save(memory);
	}
	
	@Transactional(readOnly = false)
	public void delete(Memory memory) {
		super.delete(memory);
	}

	@Transactional(readOnly = false)
	public void saveList(ResourceGatherItem resourceGatherItem, List<Memory> list, Resource resource, MemoryUsedRate memoryUsedRate,Memory physicalMemory,Memory virtualMemory) {
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}
		memoryMapper.delByResourceId(resource.getId());
		if(list!=null) {
			memoryMapper.saveList(list);
		}
		memoryMapper.saveMemoryUsedRate(memoryUsedRate);


		String value="";

		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"8");
		if(CheckObject.checkList(updateList)){
			Date now=new Date();
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){

				if("2".equals(resourceIndicatorlist.getIndicator().getItemType())){   //平均内存利用率
					value=memoryUsedRate.getUsedRate();
					resourceIndicatorlistMapper.setValue(memoryUsedRate.getUsedRate(),resourceIndicatorlist.getId(),now);
				}
				if(physicalMemory!=null&&virtualMemory!=null) {
					if ("3".equals(resourceIndicatorlist.getIndicator().getItemType())) {   //物理内存
						value = physicalMemory.getTotal();
						resourceIndicatorlistMapper.setValue(physicalMemory.getTotal(), resourceIndicatorlist.getId(), now);
					} else if ("4".equals(resourceIndicatorlist.getIndicator().getItemType())) {  //已使用物理内存
						value = physicalMemory.getUsed();
						resourceIndicatorlistMapper.setValue(physicalMemory.getUsed(), resourceIndicatorlist.getId(), now);
					} else if ("5".equals(resourceIndicatorlist.getIndicator().getItemType())) {   //物理内存使用率
						value = physicalMemory.getUsedRate();
						resourceIndicatorlistMapper.setValue(physicalMemory.getUsedRate(), resourceIndicatorlist.getId(), now);
					} else if ("6".equals(resourceIndicatorlist.getIndicator().getItemType())) {   //虚拟内存
						value = virtualMemory.getTotal();
						resourceIndicatorlistMapper.setValue(virtualMemory.getTotal(), resourceIndicatorlist.getId(), now);
					} else if ("7".equals(resourceIndicatorlist.getIndicator().getItemType())) {    //已使用虚拟内存
						value = virtualMemory.getUsed();
						resourceIndicatorlistMapper.setValue(virtualMemory.getUsed(), resourceIndicatorlist.getId(), now);
					} else if ("8".equals(resourceIndicatorlist.getIndicator().getItemType())) {     //虚拟内存使用率
						value = virtualMemory.getUsedRate();
						resourceIndicatorlistMapper.setValue(virtualMemory.getUsedRate(), resourceIndicatorlist.getId(), now);
					}
				}

				if(StringUtils.isNotBlank(value)) {
					ResourceException resourceException = null;
					try {
						resourceException = calculationThreshold(resource, resourceIndicatorlist, value,resourceExceptionService.getByResourceIndicatorId(resourceIndicatorlist.getId()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (resourceException != null) {
						resourceExceptionService.save(resourceException);
					}
				}
			}
		}

	}


	@Transactional(readOnly = false)
	public void saveMemoryUsedRate(ResourceGatherItem resourceGatherItem, Resource resource, MemoryUsedRate memoryUsedRate) {
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}
		memoryMapper.saveMemoryUsedRate(memoryUsedRate);
		String value="";
		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"8");
		if(CheckObject.checkList(updateList)){
			Date now=new Date();
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				if("2".equals(resourceIndicatorlist.getIndicator().getItemType())){   //平均内存利用率
					value=memoryUsedRate.getUsedRate();
					resourceIndicatorlistMapper.setValue(memoryUsedRate.getUsedRate(),resourceIndicatorlist.getId(),now);
				}
				if(StringUtils.isNotBlank(value)) {
					ResourceException resourceException = null;
					try {
						resourceException = calculationThreshold(resource, resourceIndicatorlist, value,resourceExceptionService.getByResourceIndicatorId(resourceIndicatorlist.getId()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (resourceException != null) {
						resourceExceptionService.save(resourceException);
					}
				}
			}
		}
	}


	@Transactional(readOnly = false)
	public void saveMemoryUsedRate(MemoryUsedRate memoryUsedRate) {
		memoryMapper.saveMemoryUsedRate(memoryUsedRate);
	}



	public MemoryUsedRate getTopMemoryUsedRate(String id) {
		return memoryMapper.getTopMemoryUsedRate(id);
	}

	public List<MemoryUsedRate> getMemoryPanorama(){
		return memoryMapper.getMemoryPanorama();
	}
}