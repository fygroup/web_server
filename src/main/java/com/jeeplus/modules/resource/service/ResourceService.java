package com.jeeplus.modules.resource.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.http.HttpUrl;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicatorValue;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indextemplate.entity.IndexTemplate;
import com.jeeplus.modules.indextemplate.service.IndexTemplateService;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.resource.entity.*;
import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
import com.jeeplus.modules.resourcebaseinfo.mapper.ResourceBaseInfoMapper;
import com.jeeplus.modules.resourcebaseinfo.service.ResourceBaseInfoService;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.mapper.ResourceIndicatorlistMapper;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import com.jeeplus.modules.toposymbols.mapper.TopoSymbolsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.resource.mapper.ResourceMapper;

import javax.xml.ws.Response;

import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;

/**
 * 资源Service
 * @author le
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends CrudService<ResourceMapper, Resource> {


	@Autowired
	NetworkInterfaceService networkInterfaceService;

	@Autowired
	ApplicationIndicatorService applicationIndicatorService;

	@Autowired
	ResourceBaseInfoService resourceBaseInfoService;
	@Autowired
	ResourceBaseInfoMapper resourceBaseInfoMapper;

	@Autowired
	ResourceMapper resourceMapper;

	@Autowired
	private ResourceIndicatorlistMapper resourceIndicatorlistMapper;

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;

	@Autowired
	private ResourceExceptionService resourceExceptionService;

	@Autowired
	private LinkIndicatorService linkIndicatorService;

	@Autowired
	private TopoSymbolsMapper topoSymbolsMapper;


	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private IndexTemplateService indexTemplateService;

	public Resource get(String id) {
		return super.get(id);
	}
	
	public List<Resource> findList(Resource resource) {
		return super.findList(resource);
	}
	
	public Page<Resource> findPage(Page<Resource> page, Resource resource) {
		return super.findPage(page, resource);
	}
	
	@Transactional(readOnly = false)
	public void save(Resource resource) {
		super.save(resource);
	}

	@Transactional(readOnly = false)
	public void save(Resource resource, ResourceBaseInfo resourceBaseInfo,ResourceGatherItem resourceGatherItem) {
		resourceBaseInfoService.save(resourceBaseInfo);
		resource.setResourceBaseInfo(resourceBaseInfo);
		super.save(resource);
		if(resourceGatherItem!=null){
			resourceGatherItem.setResourceId(resource.getId());
			resourceGatherItemService.save(resourceGatherItem);
		}
		/*List<IndexTemplate> list=indexTemplateService.list(resource.getResourceType().getId());
		if(CheckObject.checkList(list)){
			IndexTemplate indexTemplate=list.get(0);
			if(CheckObject.checkList(indexTemplate.getIndicatorList())){
				Date now=new Date();
				List<ResourceIndicatorlist>  resourceIndicatorlists=new ArrayList<>();
				for(Indicator indicator:indexTemplate.getIndicatorList()){
					ResourceIndicatorlist resourceIndicatorlist = new ResourceIndicatorlist();
						resourceIndicatorlist.setId(IdGen.uuid());
						resourceIndicatorlist.setCreateDate(now);
						resourceIndicatorlist.setUpdateDate(now);
						resourceIndicatorlist.setDelFlag("0");
						resourceIndicatorlist.setResourceId(resource.getId());
						resourceIndicatorlist.setGatherType("1");
						resourceIndicatorlist.setIndicatorId(indicator.getId());
					   resourceIndicatorlists.add(resourceIndicatorlist);
				}

				if(CheckObject.checkList(list)){
					//保存
					resourceIndicatorlistMapper.saveList(resourceIndicatorlists);
				}

			}

		}*/
	}

	@Transactional(readOnly = false)
	public void saveApplication(Resource resource, ResourceBaseInfo resourceBaseInfo,ResourceGatherItem resourceGatherItem) {
		resourceBaseInfoService.save(resourceBaseInfo);
		resource.setResourceBaseInfo(resourceBaseInfo);
		super.save(resource);
		Date date=new Date();
		List<ApplicationIndicator> list=resource.getApplicationIndicatorList();
		List<ApplicationIndicatorValue> valueList=new ArrayList<>();
		if(CheckObject.checkList(list)){
				for(ApplicationIndicator item : list){
					item.setId(IdGen.uuid());
					item.setCreateDate(date);
					item.setResource(resource);
					ApplicationIndicatorValue applicationIndicatorValue=new ApplicationIndicatorValue();
					applicationIndicatorValue.setApplicationIndicator(item);
					applicationIndicatorValue.setCreateDate(date);
					applicationIndicatorValue.setValue(HttpUrl.getURLContent(item.getUrl()));
					valueList.add(applicationIndicatorValue);
				}
			applicationIndicatorService.saveList(list);
		}
		if(CheckObject.checkList(valueList)){
			applicationIndicatorService.saveValueList(valueList);
		}
		if(resourceGatherItem!=null){
			resourceGatherItem.setResourceId(resource.getId());
			resourceGatherItemService.save(resourceGatherItem);
		}
	}


	@Transactional(readOnly = false)
	public void saveLink(Resource resource, LinkIndicator linkIndicator,ResourceGatherItem resourceGatherItem) {
		super.save(resource);
		linkIndicator.setResource(resource);
		linkIndicatorService.save(linkIndicator);

		if(resourceGatherItem!=null){
			resourceGatherItem.setResourceId(resource.getId());
			resourceGatherItemService.save(resourceGatherItem);
		}
		//TODO 保存拓扑图链路信息
		TopoSymbols topoSymbols = new TopoSymbols();
		topoSymbols.preInsert();
		//topoSymbolsMapper.insert(topoSymbols);
	}


	@Transactional(readOnly = false)
	public void delete(Resource resource) {
		if(resource.getResourceBaseInfo()!=null){
			resourceBaseInfoService.delete(new ResourceBaseInfo(resource.getResourceBaseInfo().getId()));
		}
		linkIndicatorService.delByResource(resource.getId());
		networkInterfaceService.delByResourceId(resource.getId());
		super.delete(resource);
	}


	@Transactional(readOnly = false)
	public void saveICMPTime (ResourceGatherItem resourceGatherItem, ResponseTime responseTime, Resource resource){
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}
		String value="";
		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistMapper.getUpdateList(resource.getId(),resource.getResourceType().getId(),resource.getOperatingSystemId(),"9");
		if(CheckObject.checkList(updateList)){
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				if("9".equals(resourceIndicatorlist.getIndicator().getItemType())){
					value= responseTime.getTime();
					resourceIndicatorlistMapper.setValue(responseTime.getTime(),resourceIndicatorlist.getId(),new Date());
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
		resourceMapper.saveResponseTime(responseTime);
	}



	@Transactional(readOnly = false)
	public void saveMiddlewareResponseTime ( ResponseTime responseTime){
		resourceMapper.saveResponseTime(responseTime);
	}


	public ResponseTime getTopResponseTime (String  resourceId ){
		return resourceMapper.getTopResponseTime(resourceId);
	}

	public List<ResponseTime> findResponseTimeList (@Param(value = "resourceId") String  resourceId ){
		return resourceMapper.findResponseTimeList(resourceId);
	}

	public Resource  checkIp(String  ip,String  resourceType){
		return resourceMapper.checkIp(ip,resourceType);
	}


	public Resource  checkMiddlewareIp( String  ip,String  accessConfigPort,String  resourceType, String  accessConfigUserName,String  accessConfigPassword){
		return resourceMapper.checkMiddlewareIp(ip,accessConfigPort,resourceType,accessConfigUserName,accessConfigPassword);
	}



	public List<Resource> getListByCode (String  code ){
		return resourceMapper.getListByCode(code);
	}



	public Resource getByIpType ( String  ip, String  code ){
		List<Resource> list=resourceMapper.getByIpType(ip,code);
		if(CheckObject.checkList(list)){
			return list.get(0);
		}
		return null;
	}



	public Resource getByIpTypeOfLink ( String  ip){
		List<Resource> list=resourceMapper.getByIpType(ip,"1");
		if(CheckObject.checkList(list)){
			return list.get(0);
		}
		list=resourceMapper.getByIpType(ip,"2");
		if(CheckObject.checkList(list)){
			return list.get(0);
		}
		list=resourceMapper.getByIpType(ip,"6");
		if(CheckObject.checkList(list)){
			return list.get(0);
		}
		return null;
	}



	public String getResourceImg (String  resourceId ){
		return resourceMapper.getResourceImg(resourceId);
	}

	@Transactional(readOnly = false)
	public int updateResourceImg ( String  resourceId , String  img){
		return resourceMapper.updateResourceImg(resourceId,img);
	}

	@Transactional(readOnly = false)
	public int setMac(String mac,String id){
		return resourceMapper.setMac(mac,id);
	}


	@Transactional(readOnly = false)
	public int updateResourceName ( String  id, String  name ){
		return resourceMapper.updateResourceName(id,name);
	}

	@Transactional(readOnly = false)
	public int updateResourceRdcommunity ( String  id, String  rdcommunity ){
		return resourceBaseInfoMapper.updateResourceRdcommunity(id,rdcommunity);
	}

	/**
	 * 设置资源指标模板
	 * @param id
	 * @param indexTemplateId
	 * @return
	 */
	@Transactional(readOnly = false)
	public int setIndexTemplate( String  id, String  indexTemplateId ){
		return resourceMapper.setIndexTemplate(id,indexTemplateId);
	}


	/**
	 * 获取最新可用率
	 * @param resourceId
	 * @return
	 */
	public AvailabilityRate getTopAvailabilityRate( String resourceId){
		return resourceMapper.getTopAvailabilityRate(resourceId);
	}

	/**
	 * 获取最新健康度
	 * @param resourceId
	 * @return
	 */
	public HealthDegree getTopHealthDegree(String resourceId){
		return resourceMapper.getTopHealthDegree(resourceId);
	}


	/**
	 * 保存健康度
	 * @param healthDegree
	 * @return
	 */
	@Transactional(readOnly = false)
	public void  saveHealthDegree(HealthDegree healthDegree){
		 resourceMapper.saveHealthDegree(healthDegree);
	}


	/**
	 * 保存健康度
	 * @param availabilityRate
	 * @return
	 */
	@Transactional(readOnly = false)
	public void  saveAvailabilityRate(AvailabilityRate availabilityRate){
		resourceMapper.saveAvailabilityRate(availabilityRate);
	}


	/**
	 * 获取健康度列表
	 * @param resourceId
	 * @return
	 */
	public List<HealthDegree> findHealthDegreeList( String resourceId){
		return resourceMapper.findHealthDegreeList(resourceId);
	}






	/**
	 * 根据时间段获取健康度列表
	 * @param resourceId
	 * @return
	 */
	public List<HealthDegree> findHealthDegreeListDate( String resourceId,Date beginDate, Date endDate) {
		return resourceMapper.findHealthDegreeListDate(resourceId,beginDate,  endDate);
	}


	/**
	 * 根据时间段获取健康度总数
	 * @param resourceId
	 * @return
	 */
	public String findHealthDegreeListDateTotal( String resourceId,Date beginDate, Date endDate) {
		return resourceMapper.findHealthDegreeListDateTotal(resourceId,beginDate,  endDate);
	}




	/**
	 * 获取可用率列表
	 * @param resourceId
	 * @return
	 */
	public List<AvailabilityRate> findAvailabilityRateList(String resourceId){
		return resourceMapper.findAvailabilityRateList(resourceId);
	}


	/**
	 * 根据时间段获取可用率列表
	 * @param resourceId
	 * @return
	 */
	public List<AvailabilityRate> findAvailabilityRateListDate(String resourceId,Date beginDate, Date endDate) {
		return resourceMapper.findAvailabilityRateListDate(resourceId,beginDate,  endDate);
	}


	/**
	 * 根据时间段获取可用率总数
	 * @param resourceId
	 * @return
	 */
	public String findAvailabilityRateListDateTotal(String resourceId,Date beginDate, Date endDate) {
		return resourceMapper.findAvailabilityRateListDateTotal(resourceId,beginDate,  endDate);
	}



	public List<MokaCollector> getCollectorList(){
		return resourceMapper.getCollectorList();
	}

	public MokaCollector getCollector( String id){
		if(StringUtils.isEmpty(id)){
			return  new MokaCollector();
		}
		return resourceMapper.getCollector(id);
	}
}