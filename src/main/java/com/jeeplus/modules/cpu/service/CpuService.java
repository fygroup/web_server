package com.jeeplus.modules.cpu.service;

import java.util.Date;
import java.util.List;
import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indicator.service.IndicatorService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.mapper.ResourceIndicatorlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.mapper.CpuMapper;
import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;

/**
 * CPU信息Service
 * @author le
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class CpuService extends CrudService<CpuMapper, Cpu> {

	@Autowired
	private CpuMapper cpuMapper;

	@Autowired
	private IndicatorService indicatorService;
	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;

	@Autowired
	private ResourceExceptionService resourceExceptionService;

	public Cpu get(String id) {
		return super.get(id);
	}
	
	public List<Cpu> findList(Cpu cpu) {
		return super.findList(cpu);
	}

	public List<CpuUsedRate> findUsedRateList(String resourceId) {
		return cpuMapper.findUsedRateList(resourceId);
	}

	public List<CpuUsedRate> findUsedRateListOfTime(String resourceId,Date beginDate, Date endDate) {
		return cpuMapper.findUsedRateListOfTime(resourceId,beginDate,  endDate);
	}

	public String findTotalUsedRateListOfTime(String resourceId,Date beginDate, Date endDate) {
		return cpuMapper.findTotalUsedRateListOfTime(resourceId,beginDate,  endDate);
	}

	public String findCpuUsedRate(String resourceId) {
		return cpuMapper.findCpuUsedRate(resourceId);
	}


	public String findCpuUsedRateNew(String resourceId) {
		return cpuMapper.findCpuUsedRateNew(resourceId);
	}

	public List<CpuUsedRate> findUsedRateListOfTimeType(String resourceTypeId,Date beginDate, Date endDate) {
		return cpuMapper.findUsedRateListOfTimeType(resourceTypeId,beginDate,  endDate);
	}
	
	public Page<Cpu> findPage(Page<Cpu> page, Cpu cpu) {
		return super.findPage(page, cpu);
	}
	
	@Transactional(readOnly = false)
	public void save(Cpu cpu) {
		super.save(cpu);
	}
	
	@Transactional(readOnly = false)
	public void delete(Cpu cpu) {
		super.delete(cpu);
	}

	@Transactional(readOnly = false)
	public void saveList(ResourceGatherItem resourceGatherItem, List<Cpu> list, Resource resource, CpuUsedRate cpuUsedRate) {
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}

		if(list!=null&&CheckObject.checkList(list)) {
			cpuMapper.delByResourceId(resource.getId());
			cpuMapper.saveList(list);
		}
		cpuMapper.saveCpuUsedRate(cpuUsedRate);
		String value="";
		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"3");
	    if(CheckObject.checkList(updateList)){
	    	for(ResourceIndicatorlist resourceIndicatorlist : updateList){
	    		if("1".equals(resourceIndicatorlist.getIndicator().getItemType())){
					value=cpuUsedRate.getUsedRate();
					resourceIndicatorlistMapper.setValue(cpuUsedRate.getUsedRate(),resourceIndicatorlist.getId(),new Date());
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
	public void saveCpuUsedRate(Resource resource,ResourceGatherItem resourceGatherItem, CpuUsedRate cpuUsedRate) {
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}
		cpuMapper.saveCpuUsedRate(cpuUsedRate);
		String value="";
		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"3");
		if(CheckObject.checkList(updateList)){
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				if("1".equals(resourceIndicatorlist.getIndicator().getItemType())){
					value=cpuUsedRate.getUsedRate();
					resourceIndicatorlistMapper.setValue(cpuUsedRate.getUsedRate(),resourceIndicatorlist.getId(),new Date());
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

	public CpuUsedRate getTopCpuUsedRate(String id) {
		return cpuMapper.getTopCpuUsedRate(id);
	}

	@Transactional(readOnly = false)
	public void saveCpuUsedRate( CpuUsedRate cpuUsedRate) {
		cpuMapper.saveCpuUsedRate(cpuUsedRate);
	}


	public List<CpuUsedRate> getCpuPanorama(){
		return cpuMapper.getCpuPanorama();
	}

	public List<Cpu> getWithResourceId(String resourceId){
 	    return cpuMapper.getWithResourceId(resourceId);
    }

}