package com.jeeplus.modules.monitor.task;

import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.http.HttpUrl;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicatorValue;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.collect.GeneralMethod;
import com.jeeplus.modules.collect.Ping.Ping;
import com.jeeplus.modules.collect.database.mysql.ConnectionsCalculateMysql;
import com.jeeplus.modules.collect.database.mysql.ResponseTimeCalculateMysql;
import com.jeeplus.modules.collect.database.mysql.UpTimeCalculateMysql;
import com.jeeplus.modules.collect.database.sqlserver.ConnectionsCalculateSqlserver;
import com.jeeplus.modules.collect.database.sqlserver.DataSizeCalculateSqlserver;
import com.jeeplus.modules.collect.database.sqlserver.ResponseTimeCalculateSqlserver;
import com.jeeplus.modules.collect.middleware.nginx.*;
import com.jeeplus.modules.collect.middleware.tomcat.GeneralTomcatIndicator;
import com.jeeplus.modules.collect.middleware.tomcat.entity.TomcatInfo;
import com.jeeplus.modules.collect.networkequipment.exchange.cpu.CpuIndicatorCalculateCisco;
import com.jeeplus.modules.collect.networkequipment.exchange.memory.MemoryIndicatorCalculateCisco;
import com.jeeplus.modules.collect.networkequipment.exchange.networkinterface.NetworkInterfaceIndicatorCalculateCisco;
import com.jeeplus.modules.collect.networkequipment.firewall.ConnectionsIndicatorCalculateFirewall;
import com.jeeplus.modules.collect.networkequipment.firewall.cpu.CpuIndicatorCalculateFirewall;
import com.jeeplus.modules.collect.networkequipment.firewall.memory.MemoryIndicatorCalculateFirewall;
import com.jeeplus.modules.collect.networkequipment.firewall.networkinterface.NetworkInterfaceIndicatorCalculateFirewall;
import com.jeeplus.modules.collect.networkequipment.ngids.cpu.CpuIndicatorCalculateNGIDS;
import com.jeeplus.modules.collect.networkequipment.ngids.memory.MemoryIndicatorCalculateNGIDS;
import com.jeeplus.modules.collect.networkequipment.ngids.networkinterface.NetworkInterfaceIndicatorCalculateNGIDS;
import com.jeeplus.modules.collect.networkequipment.router.cpu.CpuIndicatorCalculateRouter;
import com.jeeplus.modules.collect.networkequipment.router.cpu.CpuIndicatorCalculateRouterCisco;
import com.jeeplus.modules.collect.networkequipment.router.memory.MemoryIndicatorCalculateRouter;
import com.jeeplus.modules.collect.networkequipment.router.memory.MemoryIndicatorCalculateRouterCisco;
import com.jeeplus.modules.collect.networkequipment.router.networkinterface.NetworkInterfaceIndicatorCalculateRouter;
import com.jeeplus.modules.collect.networkequipment.router.networkinterface.NetworkInterfaceIndicatorCalculateRouterCisco;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.monitor.entity.Task;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.process.entity.Process;
import com.jeeplus.modules.process.service.ProcessService;
import com.jeeplus.modules.resource.entity.*;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.service.ResourceTypeService;
import com.jeeplus.modules.software.entity.Software;
import com.jeeplus.modules.software.service.SoftwareService;
import com.jeeplus.modules.update.UpdateService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.formula.functions.T;
import org.quartz.DisallowConcurrentExecution;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;
import static com.jeeplus.modules.resource.web.ResourceController.checkDataBase;
import static com.jeeplus.modules.resource.web.ResourceController.checkMiddleware;

@DisallowConcurrentExecution
public class IndicatorTask extends Task {

	private ResourceService resourceService = (ResourceService)SpringBeanUtils.getBeanByClass(ResourceService.class);

	private ResourceGatherItemService resourceGatherItemService= (ResourceGatherItemService)SpringBeanUtils.getBeanByClass(ResourceGatherItemService.class);

	private ResourceTypeService resourceTypeService= (ResourceTypeService)SpringBeanUtils.getBeanByClass(ResourceTypeService.class);

	private CpuService cpuService= (CpuService)SpringBeanUtils.getBeanByClass(CpuService.class);

	private MemoryService memoryService= (MemoryService)SpringBeanUtils.getBeanByClass(MemoryService.class);


	@Override
	public void run() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------任务在 "+dateFormat.format(new Date())+" 时运行"+"------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

		updateResourceIndicator();

		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------任务结束---------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------\n\n\n\n");

	}


	void updateResourceIndicator(){
		Long star=System.currentTimeMillis();
		Date date=new Date();
		ResourceType resourceType=new ResourceType();
		Resource initResource=new Resource();
		initResource.setResourceType(resourceType);
		List<Resource> resourceList= resourceService.findList(initResource);
		if(CheckObject.checkList(resourceList)){
			for(Resource resource:resourceList) {
				ResourceType parentResourceType =resourceTypeService.getParentByChild(resource.getResourceType().getId());
				Boolean conn=checkConnection(resource,parentResourceType);
				if(conn){
					List<ResourceGatherItem> list = resourceGatherItemService.findListByResource(resource.getId());
					if(CheckObject.checkList(list)) {
						for (ResourceGatherItem resourceGatherItem : list) {
							if(StringUtils.isNotBlank(resourceGatherItem.getCollectionClass())){
								new Thread(new Runnable() {
									@Override
									public void run() {
										Class onwClass = null;
										try {
											onwClass=Class.forName(resourceGatherItem.getCollectionClass());
											UpdateService updateClass = (UpdateService) onwClass.newInstance();
											updateClass.updateIndicator(resource);
										} catch (Exception e) {
											e.printStackTrace();
										}
								//System.out.println(resourceGatherItem.getCollectionClass()+" ："+(System.currentTimeMillis()-star));
									}
								}).start();
							}
						}
					}

					/*new Thread(new Runnable() {
						@Override
						public void run() {
							List<ResourceGatherItem> list = resourceGatherItemService.findListByResource(resource.getId());
							if(CheckObject.checkList(list)) {
								for (ResourceGatherItem resourceGatherItem : list) {
									if(StringUtils.isNotBlank(resourceGatherItem.getCollectionClass())){
												Class onwClass = null;
												try {
													onwClass=Class.forName(resourceGatherItem.getCollectionClass());
													UpdateService updateClass = (UpdateService) onwClass.newInstance();
													updateClass.updateIndicator(resource);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
									}
								}
							}
					}).start();*/
				}else{
					if("1".equals(parentResourceType.getCode())||"2".equals(parentResourceType.getCode())||"6".equals(parentResourceType.getCode())) {
						CpuUsedRate cpuUsedRate = new CpuUsedRate();
						cpuUsedRate.setCreateDate(date);
						cpuUsedRate.setResourceId(resource.getId());
						cpuUsedRate.setUsedRate("0.00");
						cpuService.saveCpuUsedRate(cpuUsedRate);

						MemoryUsedRate memoryUsedRate = new MemoryUsedRate();
						memoryUsedRate.setCreateDate(date);
						memoryUsedRate.setResourceId(resource.getId());
						memoryUsedRate.setUsedRate("0.00");
						memoryService.saveMemoryUsedRate(memoryUsedRate);
					}
				}


				if("1".equals(parentResourceType.getCode())||"2".equals(parentResourceType.getCode())||"6".equals(parentResourceType.getCode())||"7".equals(parentResourceType.getCode())) {
					setHealthDegreeValue(resource,conn);
					setAvailabilityRateValue(resource,conn);
				}

				if("4".equals(parentResourceType.getCode())||"5".equals(parentResourceType.getCode())){
					setDataBaseMiddle(resource,parentResourceType.getCode());
				}
			}
		}




		System.out.println("方法结束=====================："+(System.currentTimeMillis()-star));
	}

	boolean checkConnection(Resource resource,ResourceType parentResourceType){
		Date date=new Date();
		if("1".equals(parentResourceType.getCode())||"2".equals(parentResourceType.getCode())||"6".equals(parentResourceType.getCode())||"7".equals(parentResourceType.getCode())) {
			Long time= Ping.ping(resource.getIp());
			if(time<0l) {
				ResponseTime responseTime = new ResponseTime();
				responseTime.setCreateDate(date);
				responseTime.setResourceId(resource.getId());
				responseTime.setTime(String.valueOf(time));
				resourceService.saveICMPTime(null, responseTime, resource);
				System.out.println("========="+resource.getName()+"("+resource.getIp()+") ping失败"+"=========");
				return false;
			}
		}
		return true;
	}







	/**
	 * 计算网络设备、服务器、安全设备健康度
	 * @param resource
	 */
	void setHealthDegreeValue(Resource resource,Boolean conn){
		DecimalFormat df = new DecimalFormat("######0.00");
		int successNum=0;
		String value = "0";
			if(conn) {
				List<ResponseTime> list = resourceService.findResponseTimeList(resource.getId());
				if (CheckObject.checkList(list)) {
					for (ResponseTime item : list) {
						if (Integer.parseInt(item.getTime()) > 0) {
							successNum++;
						}
					}
					value = df.format(Transformation.null2Double((successNum * 100.00) / list.size() ));
				}
			}
			HealthDegree healthDegree=new HealthDegree();
			healthDegree.setResourceId(resource.getId());
			healthDegree.setHealthDegree(value);
			healthDegree.setCreateDate(new Date());
			resourceService.saveHealthDegree(healthDegree);
	}


	/**
	 * 计算网络设备、服务器、安全设备可用率
	 * @param resource
	 */
	void setAvailabilityRateValue(Resource resource,Boolean conn) {
		DecimalFormat df = new DecimalFormat("######0.00");
		String value = "0";
		MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
		CpuUsedRate cpuUsedRate=cpuService.getTopCpuUsedRate(resource.getId());

		if(conn) {
			if (memoryUsedRate != null && memoryUsedRate.getUsedRate() != null && cpuUsedRate != null && cpuUsedRate.getUsedRate() != null) {
				double total = 200.00 - (Transformation.null2Double(memoryUsedRate.getUsedRate()) + Transformation.null2Double(cpuUsedRate.getUsedRate()));
				value = df.format(Transformation.null2Double(total / 2.00));
			}
		}

		AvailabilityRate availabilityRate = new AvailabilityRate();
		availabilityRate.setResourceId(resource.getId());
		availabilityRate.setAvailabilityRate(value);
		availabilityRate.setCreateDate(new Date());
		resourceService.saveAvailabilityRate(availabilityRate);
	}



	void setDataBaseMiddle(Resource resource,String resourceTypeCode){
		Boolean conn=false;
		DecimalFormat df = new DecimalFormat("######0.00");
		int successNum=0;
		String value = "0";

		if("4".equals(resourceTypeCode)) {
			conn = checkDataBase(resource.getIp(), resource.getResourceBaseInfo().getAccessConfigPort(),
					resource.getResourceBaseInfo().getAccessConfigUserName(), resource.getResourceBaseInfo().getAccessConfigPassword(), resource.getResourceType());
		}
		if("5".equals(resourceTypeCode)) {
			conn = checkMiddleware(resource.getResourceType(),resource.getIp(), resource.getResourceBaseInfo().getAccessConfigPort(),
					resource.getResourceBaseInfo().getAccessConfigUserName(), resource.getResourceBaseInfo().getAccessConfigPassword());
		}
		if(conn) {
			List<ResponseTime> list = resourceService.findResponseTimeList(resource.getId());
			if (CheckObject.checkList(list)) {
				for (ResponseTime item : list) {
					if (Integer.parseInt(item.getTime()) > 0) {
						successNum++;
					}
				}
				value = df.format(Transformation.null2Double((successNum * 100.00) / list.size() ));
			}
		}
		HealthDegree healthDegree=new HealthDegree();
		healthDegree.setResourceId(resource.getId());
		healthDegree.setHealthDegree(value);
		healthDegree.setCreateDate(new Date());
		resourceService.saveHealthDegree(healthDegree);

		AvailabilityRate availabilityRate = new AvailabilityRate();
		availabilityRate.setResourceId(resource.getId());
		availabilityRate.setAvailabilityRate(value);
		availabilityRate.setCreateDate(new Date());
		resourceService.saveAvailabilityRate(availabilityRate);

	}

}
