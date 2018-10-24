
package com.jeeplus.modules.datainterface;

import com.jeeplus.common.utils.Transformation;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indextemplate.service.IndexTemplateService;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.manufacturer.service.ManufacturerService;
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.operatingsystem.service.OperatingSystemService;
import com.jeeplus.modules.process.service.ProcessService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourceconfiginfo.service.ResourceConfigInfoService;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.resourceinformation.service.ResourceInformationService;
import com.jeeplus.modules.resourcephysicinfo.service.ResourcePhysicInfoService;
import com.jeeplus.modules.resourcetype.service.ResourceTypeService;
import com.jeeplus.modules.software.service.SoftwareService;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.service.DictTypeService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.tools.utils.HttpPostTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源Controller
 * @author le
 * @version 2017-10-27
 */
@org.springframework.stereotype.Controller
@RequestMapping(value = "${adminPath}/datainterface/dataInterface")
public class Controller extends BaseController {

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ProcessService processService;

	@Autowired
	private DiskService diskService;

	@Autowired
	private NetworkInterfaceService networkInterfaceService;

	@Autowired
	private CpuService cpuService;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private MemoryService memoryService;

	@Autowired
	private ResourcePhysicInfoService resourcePhysicInfoService;

	@Autowired
	private ResourceConfigInfoService resourceConfigInfoService;
	@Autowired
	private ResourceInformationService resourceInformationService;

	@Autowired
	private SoftwareService softwareService;

	@Autowired
	private DictTypeService dictTypeService;

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private OperatingSystemService operatingSystemService;

	@Autowired
	private ResourceTypeService resourceTypeService;

	@Autowired
	private ResourceExceptionService resourceExceptionService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private LinkIndicatorService linkIndicatorService;

	@Autowired
	private ApplicationIndicatorService applicationIndicatorService;

	@Autowired
	private IndexTemplateService indexTemplateService;



	@ResponseBody
	@RequestMapping(value = "qualitativeEffectIndexDataTest")
	public void qualitativeEffectIndexData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DecimalFormat df = new DecimalFormat("######0.00");
		int serverNums=0;
		int netNums=0;

		List<Resource> resourceList =resourceService.findList(new Resource());
		for(Resource resource:resourceList){

			//网络设备
			if("1".equals(resource.getResourceType().getCode())||"1".equals(resource.getResourceType().getParent().getCode())){
				netNums++;
				String data="{\"source_type\":\"主机\",\"data\":[";
				String dataValue="";
				CpuUsedRate cpuUsedRate= cpuService.getTopCpuUsedRate(resource.getId());
				if(cpuUsedRate!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"c5b2d871178544df90070574effefd2e\",\"value\":\""+cpuUsedRate.getUsedRate()+"\",\"indic_time\":\""+sdf.format(cpuUsedRate.getCreateDate())+"\",\"collect_time\":\""+sdf.format(cpuUsedRate.getCreateDate())+"\"},";
				}

				MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
				if(memoryUsedRate!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"b31b83fc5ca64f88a32cf873dc3616bd\",\"value\":\""+memoryUsedRate.getUsedRate()+"\",\"indic_time\":\""+sdf.format(memoryUsedRate.getCreateDate())+"\",\"collect_time\":\""+sdf.format(memoryUsedRate.getCreateDate())+"\"},";
				}



				List<NetworkInterface> networkInterfaces= networkInterfaceService.findListBySort(resource.getId());
				int nums=0;
				Double totalOutUsedRate=0.0;
				Double totalInputUsedRate=0.0;
				NetworkInterface n=null;
				for(NetworkInterface networkInterface:networkInterfaces){
					Double capacity=Transformation.null2Double(networkInterface.getCapacity().replaceAll("Mbps",""));
					if(capacity<=0.0){
						continue;
					}
					if(n==null){
						n=networkInterface;
					}
					nums++;
					if(networkInterface.getOutRate().getRate().contains("Mbps")){
						Double outRate=Transformation.null2Double(networkInterface.getOutRate().getRate().replaceAll("Mbps",""));
						totalOutUsedRate+=(outRate/capacity)*100.00;
					}else if(networkInterface.getOutRate().getRate().contains("Kbps")){
						Double outRate=Transformation.null2Double(networkInterface.getOutRate().getRate().replaceAll("Kbps",""));
						totalOutUsedRate+=(outRate/capacity/1024)*100.00;
					}


					if(networkInterface.getInRate().getRate().contains("Mbps")){
						Double inRate=Transformation.null2Double(networkInterface.getInRate().getRate().replaceAll("Mbps",""));
						totalInputUsedRate+=(inRate/capacity)*100.00;
					}else if(networkInterface.getInRate().getRate().contains("Kbps")){
						Double inRate=Transformation.null2Double(networkInterface.getInRate().getRate().replaceAll("Kbps",""));
						totalInputUsedRate+=(inRate/capacity/1024)*100.00;
					}


				}
				String networkInterfaceUsedRate="-";
				if(nums>0){
					networkInterfaceUsedRate=df.format(((totalOutUsedRate/nums)+(totalInputUsedRate/nums))/2);
				}
				//带宽利用率
				dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+(n!=null?n.getId():"-")+"\",\"value\":\""+networkInterfaceUsedRate+"\",\"indic_time\":\""+(n!=null?sdf.format(n.getCreateDate()):"-")+"\",\"collect_time\":\""+(n!=null?sdf.format(n.getCreateDate()):"-")+"\"},";



				List<ResourceException> exceptions=resourceExceptionService.findList(new ResourceException());
				for(ResourceException exception:exceptions){

					//告警流水号
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getId()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//事件源ID
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getId()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//事件源名称
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//事件源类型
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getExceptionSource()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警标题
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警正文
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警采集方
					//告警级别
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+ DictUtils.getDictLabels(exception.getExceptionClass(),"exception_class", "-")+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警发生时间
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+sdf.format(exception.getCreateDate())+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警状态
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+ DictUtils.getDictLabels(exception.getCurrentStatus(),"exception_current_status", "-")+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警发现时间
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+sdf.format(exception.getCreateDate())+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					if("1".equals(exception.getCurrentStatus())||"2".equals(exception.getCurrentStatus())){
						//告警清除时间
						dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+sdf.format(exception.getUpdateDate())+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

						//告警清除正文
						dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"已清除\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					}




				}
				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data+=dataValue;
				data+="]}";
				System.out.println(data);
				/*params.put("data", data);
				String result = HttpPostTest.post("http://localhost:8080/xxxx", params);*/

				//  服务器
			}else if("2".equals(resource.getResourceType().getCode())||"2".equals(resource.getResourceType().getParent().getCode())){
				serverNums++;
				String data="{\"source_type\":\"主机\",\"data\":[";
				String dataValue="";
				CpuUsedRate cpuUsedRate= cpuService.getTopCpuUsedRate(resource.getId());
				if(cpuUsedRate!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"c5b2d871178544df90070574effefd2e\",\"value\":\""+cpuUsedRate.getUsedRate()+"\",\"indic_time\":\""+sdf.format(cpuUsedRate.getCreateDate())+"\",\"collect_time\":\""+sdf.format(cpuUsedRate.getCreateDate())+"\"},";
				}

				MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
				if(memoryUsedRate!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"b31b83fc5ca64f88a32cf873dc3616bd\",\"value\":\""+memoryUsedRate.getUsedRate()+"\",\"indic_time\":\""+sdf.format(memoryUsedRate.getCreateDate())+"\",\"collect_time\":\""+sdf.format(memoryUsedRate.getCreateDate())+"\"},";
				}
				List<Memory> virtualPhysicalMemoryList = memoryService.findList(new Memory(resource.getId()));
				Double totalMem=0.0;
				Double freeMem=0.0;
				Memory m=null;
				for(Memory memory:virtualPhysicalMemoryList){
					if(m==null){
						m=memory;
					}
					totalMem+= Transformation.null2Double(memory.getTotal());
					freeMem+= Transformation.null2Double(memory.getFree());
				}


					//内存总量
				dataValue += "{\"source_id\":\"" + resource.getId() + "\",\"indicid\":\"" + resource.getId() + "\",\"value\":\"" + totalMem + "\",\"indic_time\":\"" + (m != null ? sdf.format(m.getCreateDate()) : "-") + "\",\"collect_time\":\"" + (m != null ? sdf.format(m.getCreateDate()) : "-") + "\"},";

				//可用内存量
				dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+resource.getId()+"\",\"value\":\""+freeMem+"\",\"indic_time\":\""+(m!=null?sdf.format(m.getCreateDate()):"-")+"\",\"collect_time\":\""+(m!=null?sdf.format(m.getCreateDate()):"-")+"\"},";




				List<Disk> diskList  = diskService.findList(new Disk(resource.getId()));
				String usedRate="-";
				Double totalUsedRate=0.0;
				Disk d=null;
				for(Disk disk:diskList){
					if(d==null){
						d=disk;
					}
					totalUsedRate+=Transformation.null2Double(disk.getUsedRate());
				}
				if(diskList.size()>0){

					usedRate=df.format(totalUsedRate/diskList.size());
				}
				//磁盘利用率
				dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+resource.getId()+"\",\"value\":\""+usedRate+"\",\"indic_time\":\""+(d!=null?sdf.format(d.getCreateDate()):"-")+"\",\"collect_time\":\""+(d!=null?sdf.format(d.getCreateDate()):"-")+"\"},";




				List<NetworkInterface> networkInterfaces= networkInterfaceService.findListBySort(resource.getId());
				int nums=0;
				Double totalOutUsedRate=0.0;
				Double totalInputUsedRate=0.0;
				NetworkInterface n=null;
				for(NetworkInterface networkInterface:networkInterfaces){
					Double capacity=Transformation.null2Double(networkInterface.getCapacity().replaceAll("Mbps",""));
					if(capacity<=0.0){
						continue;
					}
					if(n==null){
						n=networkInterface;
					}
					nums++;
					if(networkInterface.getOutRate().getRate().contains("Mbps")){
						Double outRate=Transformation.null2Double(networkInterface.getOutRate().getRate().replaceAll("Mbps",""));
						totalOutUsedRate+=(outRate/capacity)*100.00;
					}else if(networkInterface.getOutRate().getRate().contains("Kbps")){
						Double outRate=Transformation.null2Double(networkInterface.getOutRate().getRate().replaceAll("Kbps",""));
						totalOutUsedRate+=(outRate/capacity/1024)*100.00;
					}


					if(networkInterface.getInRate().getRate().contains("Mbps")){
						Double inRate=Transformation.null2Double(networkInterface.getInRate().getRate().replaceAll("Mbps",""));
						totalInputUsedRate+=(inRate/capacity)*100.00;
					}else if(networkInterface.getInRate().getRate().contains("Kbps")){
						Double inRate=Transformation.null2Double(networkInterface.getInRate().getRate().replaceAll("Kbps",""));
						totalInputUsedRate+=(inRate/capacity/1024)*100.00;
					}


				}
				String networkInterfaceUsedRate="-";
				if(nums>0){
					networkInterfaceUsedRate=df.format(((totalOutUsedRate/nums)+(totalInputUsedRate/nums))/2);
				}
				//带宽利用率
				dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+(n!=null?n.getId():"-")+"\",\"value\":\""+networkInterfaceUsedRate+"\",\"indic_time\":\""+(n!=null?sdf.format(n.getCreateDate()):"-")+"\",\"collect_time\":\""+(n!=null?sdf.format(n.getCreateDate()):"-")+"\"},";


				List<ResourceException> exceptions=resourceExceptionService.findList(new ResourceException());
				for(ResourceException exception:exceptions){

					//告警流水号
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getId()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//事件源ID
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getId()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//事件源名称
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//事件源类型
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getExceptionSource()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警标题
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警正文
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警采集方
					//告警级别
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+ DictUtils.getDictLabels(exception.getExceptionClass(),"exception_class", "-")+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警发生时间
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+sdf.format(exception.getCreateDate())+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警状态
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+ DictUtils.getDictLabels(exception.getCurrentStatus(),"exception_current_status", "-")+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					//告警发现时间
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+sdf.format(exception.getCreateDate())+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					if("1".equals(exception.getCurrentStatus())||"2".equals(exception.getCurrentStatus())){
						//告警清除时间
						dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+sdf.format(exception.getUpdateDate())+"\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

						//告警清除正文
						dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+exception.getId()+"\",\"value\":\""+exception.getIndicatorName()+"已清除\",\"indic_time\":\""+sdf.format(exception.getCreateDate())+"\",\"collect_time\":\""+sdf.format(exception.getCreateDate())+"\"},";

					}

				}
				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data+=dataValue;
				data+="]}";
				System.out.println(data);
			}


			/*data=  "{" +
					"\"source_type\": \"主机\"," +
					"\"data\": [{" +
					"\"source_id\": \"101102424\"," +
					"\"indicid\": \"20100001\"," +
					"\"value\": 10.24," +
					"\"indic_time\": \"\"," +
					"\"collect_time\": \"\"" +
					"}]" +
					"}";*/

		}
		System.out.println("netNums ==>"+netNums+"  serverNums ==>"+serverNums);

	}




}