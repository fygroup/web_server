
package com.jeeplus.modules.datainterface;

import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.attack.entity.Attack;
import com.jeeplus.modules.attack.service.AttackService;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indextemplate.service.IndexTemplateService;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.loophole.entity.Loophole;
import com.jeeplus.modules.loophole.service.LoopholeService;
import com.jeeplus.modules.manufacturer.service.ManufacturerService;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
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
import com.jeeplus.modules.violations.entity.Violations;
import com.jeeplus.modules.violations.service.ViolationsService;
import com.jeeplus.modules.viruses.entity.Viruses;
import com.jeeplus.modules.viruses.service.VirusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源Controller
 * @author le
 * @version 2017-10-27
 */
@org.springframework.stereotype.Controller
@RequestMapping(value = "${adminPath}/dataInterface1")
public class TestController extends BaseController {

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
	private LoopholeService loopholeService;

	@Autowired
	private VirusesService virusesService;

	@Autowired
	private ViolationsService violationsService;

	@Autowired
	private AttackService attackService;

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
	@RequestMapping(value = "qualitativeEffectIndexDataTest1")
	public void qualitativeEffectIndexData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<Resource> resourceList =resourceService.findList(new Resource());
		for(Resource resource:resourceList){
			String data="";
			//漏洞扫描
			if("64".equals(resource.getResourceType().getCode())||"64".equals(resource.getResourceType().getParent().getCode())){
				String data1="{\"source_type\":\"漏洞扫描\",\"data\":[";
				String dataValue="";
				Loophole loophole=loopholeService.getTopLoophole(resource.getId());
				if(loophole!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+loophole.getId()+"\",\"value\":"+loophole.getValue()+",\"indic_time\":\""+sdf.format(loophole.getCreateDate())+"\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}else{
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"null\",\"value\":null,\"indic_time\":\"null\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}
				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data1+=dataValue;
				data1+="]}";
				data+=data1;
				System.out.println(data1);

			}else if ("65".equals(resource.getResourceType().getCode())||"65".equals(resource.getResourceType().getParent().getCode())){
				String data2="{\"source_type\":\"防毒墙\",\"data\":[";
				String dataValue="";
				Viruses viruses=virusesService.getTopViruses(resource.getId());
				if(viruses!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+viruses.getId()+"\",\"value\":"+viruses.getValue()+",\"indic_time\":\""+sdf.format(viruses.getCreateDate())+"\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}else{
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"null\",\"value\":null,\"indic_time\":\"null\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}
				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data2+=dataValue;
				data2+="]}";
				data+=data2;
				System.out.println(data2);
			}else if ("63".equals(resource.getResourceType().getCode())||"63".equals(resource.getResourceType().getParent().getCode())){
				String data3="{\"source_type\":\"安全审计\",\"data\":[";
				String dataValue="";
				Violations violations=violationsService.getTopViolations(resource.getId());
				if(violations!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+violations.getId()+"\",\"value\":"+violations.getValue()+",\"indic_time\":\""+sdf.format(violations.getCreateDate())+"\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}else{
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"null\",\"value\":null,\"indic_time\":\"null\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}
				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data3+=dataValue;
				data3+="]}";
				data+=data3;
				System.out.println(data3);
			}else if ("62".equals(resource.getResourceType().getCode())||"62".equals(resource.getResourceType().getParent().getCode())){
				String data4="{\"source_type\":\"入侵检测\",\"data\":[";
				String dataValue="";
				Attack attack=attackService.getTopAttack(resource.getId());
				if(attack!=null){
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\""+attack.getId()+"\",\"value\":"+attack.getValue()+",\"indic_time\":\""+sdf.format(attack.getCreateDate())+"\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}else{
					dataValue+="{\"source_id\":\""+resource.getId()+"\",\"indicid\":\"null\",\"value\":null,\"indic_time\":\"null\",\"collect_time\":\""+sdf.format(new Date())+"\"},";
				}
				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data4+=dataValue;
				data4+="]}";
				data+=data4;
				System.out.println(data4);
			}


		}

	}




}