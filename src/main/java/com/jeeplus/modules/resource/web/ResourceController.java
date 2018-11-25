
package com.jeeplus.modules.resource.web;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.imageio.ImageIO;
import com.alibaba.druid.support.json.JSONUtils;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.http.NSAuthenticator;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.collect.GeneralMethod;
import com.jeeplus.modules.collect.Ping.Ping;
import com.jeeplus.modules.collect.database.mysql.ConnectionsCalculateMysql;
import com.jeeplus.modules.collect.database.mysql.ResponseTimeCalculateMysql;
import com.jeeplus.modules.collect.database.mysql.UpTimeCalculateMysql;
import com.jeeplus.modules.collect.database.sqlserver.ConnectionsCalculateSqlserver;
import com.jeeplus.modules.collect.database.sqlserver.DataSizeCalculateSqlserver;
import com.jeeplus.modules.collect.database.sqlserver.ResponseTimeCalculateSqlserver;
import com.jeeplus.modules.collect.middleware.tomcat.GeneralTomcatIndicator;
import com.jeeplus.modules.collect.middleware.tomcat.entity.TomcatInfo;
import com.jeeplus.modules.collect.networkequipment.router.memory.MemoryIndicatorCalculateRouterHuawei;
import com.jeeplus.modules.collect.server.windows.memory.MemoryIndicatorCalculateWindows;
import com.jeeplus.modules.collect.server.windows.memory.MemoryIndicatorCalculateWindowsByV1V2;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indextemplate.service.IndexTemplateService;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.manufacturer.entity.Manufacturer;
import com.jeeplus.modules.manufacturer.service.ManufacturerService;
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.operatingsystem.entity.OperatingSystem;
import com.jeeplus.modules.operatingsystem.service.OperatingSystemService;
import com.jeeplus.modules.process.service.ProcessService;
import com.jeeplus.modules.resource.entity.*;
import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
import com.jeeplus.modules.resourceconfiginfo.service.ResourceConfigInfoService;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.resourceinformation.service.ResourceInformationService;
import com.jeeplus.modules.resourcephysicinfo.service.ResourcePhysicInfoService;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.service.ResourceTypeService;
import com.jeeplus.modules.software.entity.Software;
import com.jeeplus.modules.software.service.SoftwareService;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.DictType;
import com.jeeplus.modules.sys.entity.DictValue;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.service.DictTypeService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.update.UpdateService;
import com.jeeplus.modules.update.application.ApplicationIndicator;
import com.jeeplus.modules.update.database.DatabaseIndicator;
import com.jeeplus.modules.update.link.LinkRateIndicator;
import com.jeeplus.modules.update.middleware.MiddlewareIndicator;
import com.jeeplus.modules.update.networkequipment.exchange.cpu.CiscoExchangeCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.cpu.H3CExchangeCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.cpu.HuaweiExchangeCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.cpu.RuijieExchangeCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.memory.CiscoExchangeMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.memory.H3CExchangeMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.memory.HuaweiExchangeMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.memory.RuijieExchangeMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.networkinterface.CiscoExchangeNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.networkinterface.H3CExchangeNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.networkinterface.HuaweiExchangeNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.exchange.networkinterface.RuijieExchangeNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.router.cpu.CiscoRouterCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.router.cpu.GeneralRouterCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.router.cpu.H3CRouterCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.router.cpu.HuaweiRouterCpuByV1V2;
import com.jeeplus.modules.update.networkequipment.router.memory.CiscoRouterMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.router.memory.GeneralRouterMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.router.memory.H3CRouterMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.router.memory.HuaweiRouterMemoryByV1V2;
import com.jeeplus.modules.update.networkequipment.router.networkinterface.CiscoRouterNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.router.networkinterface.GeneralRouterNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.router.networkinterface.H3CRouterNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.networkequipment.router.networkinterface.HuaweiRouterNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.safetyequipment.cpu.*;
import com.jeeplus.modules.update.safetyequipment.indicator.TopsecFirewallIndicator;
import com.jeeplus.modules.update.safetyequipment.indicator.TopsecIDSIndicator;
import com.jeeplus.modules.update.safetyequipment.indicator.TopsecNessusIndicator;
import com.jeeplus.modules.update.safetyequipment.memory.*;
import com.jeeplus.modules.update.safetyequipment.networkinterface.*;
import com.jeeplus.modules.update.server.linux.cpu.LinuxCpuByV1V2;
import com.jeeplus.modules.update.server.linux.disk.LinuxDiskByV1V2;
import com.jeeplus.modules.update.server.linux.memory.LinuxMemoryByV1V2;
import com.jeeplus.modules.update.server.linux.networkInterface.LinuxNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.server.linux.process.LinuxProcesByV1V2;
import com.jeeplus.modules.update.server.linux.software.LinuxSoftwareByV1V2;
import com.jeeplus.modules.update.server.Icmp;
import com.jeeplus.modules.update.server.windows.cpu.WindowsCpuByV1V2;
import com.jeeplus.modules.update.server.windows.disk.WindowsDiskByV1V2;
import com.jeeplus.modules.update.server.windows.memory.WindowsMemoryByV1V2;
import com.jeeplus.modules.update.server.windows.networkInterface.WindowsNetworkInterfaceByV1V2;
import com.jeeplus.modules.update.server.windows.process.WindowsProcesByV1V2;
import com.jeeplus.modules.update.server.windows.software.WindowsSoftwareByV1V2;
import com.jeeplus.modules.update.storageequipment.cpu.StorageEquipmentCpuByV1V2;
import com.jeeplus.modules.update.storageequipment.disk.StorageEquipmentDiskByV1V2;
import com.jeeplus.modules.update.storageequipment.memory.StorageEquipmentMemoryByV1V2;
import com.jeeplus.modules.update.storageequipment.networkInterface.StorageEquipmentNetworkInterfaceByV1V2;

import jxl.write.WritableImage;
import jxl.Workbook;
import jxl.format.*;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.resource.service.ResourceService;
import springfox.documentation.spring.web.json.Json;
import com.jeeplus.modules.process.entity.Process;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;
import jxl.write.Label;
/**
 * 资源Controller
 * @author le
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/resource/resource")
public class ResourceController extends BaseController {

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

	
	@ModelAttribute
	public Resource get(@RequestParam(required=false) String id) {
		Resource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceService.get(id);
		}
		if (entity == null){
			entity = new Resource();
		}
		return entity;
	}
	
	/**
	 * resource列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resource/resourceList";
	}


	@RequestMapping(value = {"imageEdit", ""})
	public String imageEdit(Model model,String num,String resourceId) {
		model.addAttribute("num",num);
		model.addAttribute("resourceId",resourceId);
		return "modules/resource/resourceImageEdit";
	}

	@ResponseBody
	@RequestMapping(value = "imageEditSave", method = RequestMethod.POST)
	public Map<String, Object> picUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest.getFile(fileNames.next());
		String resourceId=null;
		String num=null;
		if(multipartRequest.getParameterMap()!=null&&(!"-1".equals(multipartRequest.getParameterMap().get("fodder")))){
			String parm=multipartRequest.getParameterMap().get("fodder")[0];
			String[] item=parm.split(",");
			resourceId=item[0];
			num=item[1];
		}

		String front_path=null;
		Map<String, Object> json = new HashMap<String, Object>();
		if(StringUtils.isEmpty(resourceId)){
			json.put("success", null);
			return json;
		}
		Resource resource =resourceService.get(resourceId);
		if(resource==null){
			json.put("success", null);
			return json;
		}
		String imgs[];
		if(StringUtils.isNotBlank(resource.getImg())){
			imgs=resource.getImg().split(",");
			if(imgs.length!=4){
				json.put("success", null);
				return json;
			}
		}else{
			imgs=new String[4];
		}


		if (!multipartFile.isEmpty()) {
			if (!multipartFile.isEmpty()) {
				// 文件保存路径
				String realPath = Global.RESOURCE_IMG_BASE_URL + resourceId + "/";
				String savePath=realPath + (resourceId + "_"+num+"." + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1));

				// 转存文件
				FileUtils.createDirectory(Global.getUserfilesBaseDir()+realPath);
				multipartFile.transferTo(new File( Global.getUserfilesBaseDir() +savePath));
				front_path=request.getContextPath()+ "/"+savePath;
			}
		}
		if(front_path!=null){
			int index=Integer.parseInt(num);
			imgs[index-1]=front_path;

			String resourceIms="";
			for(int i=0;i<imgs.length;i++){
				if(i<3) {
					resourceIms += imgs[i] + ",";
				}else{
					resourceIms += imgs[i];
				}
			}
			if(resourceService.updateResourceImg(resourceId,resourceIms)!=1){
				json.put("success", null);
				return json;
			}
			json.put("success", front_path+"?"+System.currentTimeMillis());
		}else{
			json.put("success", null);
		}
		return json;

}


	@ResponseBody
	@RequestMapping(value = "confirmDiscoverySave", method=RequestMethod.POST)
	public String confirmDiscoverySave(String name,String ip,String rdcommunity,String resourceType,String manufacturer,String snmpType) {
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(ip)||StringUtils.isEmpty(rdcommunity) ||StringUtils.isEmpty(resourceType)  ||StringUtils.isEmpty(manufacturer) ||StringUtils.isEmpty(snmpType)){
			return "资源信息错误";
		}
		ResourceType rType = resourceTypeService.get(resourceType);
		if("1".equals(rType.getParent().getCode())||"2".equals(rType.getParent().getCode())||"6".equals(rType.getParent().getCode())||"7".equals(rType.getParent().getCode())){
			Resource oldResource = resourceService.checkIp(ip,resourceType);
			if(oldResource!=null){
				return "当前资源已存在";
			}
		}
		Resource resource=new Resource();
		resource.setName(name);
		resource.setIp(ip);
		resource.setResourceType(rType);
		resource.setManufacturer(new Manufacturer(manufacturer));
		resource.setStatus("1");
		ResourceBaseInfo resourceBaseInfo=new ResourceBaseInfo();
		resourceBaseInfo.setRdcommunity(rdcommunity);
		resourceBaseInfo.setPort("161");
		resourceBaseInfo.setDelay("600");
		resourceBaseInfo.setRepeatnum("4");
		resourceBaseInfo.setManagerType(snmpType);

		ResourceOidValue desc = GeneralMethod.getDescription(resource.getIp(),  resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
		resource.setDescription((resource.getDescription() == null ? "" : resource.getDescription()) + desc.getValue() == null ? "" : desc.getValue());
		ResourceOidValue sysOid = GeneralMethod.getSysOid(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
		resource.setSysOid(sysOid.getValue() == null ? "" : sysOid.getValue());
		ResourceOidValue subNetMask = GeneralMethod.getSubNetMask(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
		resource.setSubnetmask(subNetMask.getValue() == null ? "" : subNetMask.getValue());
		ResourceOidValue sysName = GeneralMethod.getSysName(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
		resource.setSysName(sysName.getValue() == null ? "" : sysName.getValue());

		resourceService.save(resource, resourceBaseInfo,null);//保存
		delDataFile(resource.getIp());

		if("1".equals(rType.getParent().getCode())||"2".equals(rType.getParent().getCode())){
			saveIndicator(resource);
		}

		if("6".equals(rType.getParent().getCode())){
			savesafetyEquipmentIndicator(resource);
		}
		if("7".equals(rType.getParent().getCode())){
		}
		return "success";
	}


	@ResponseBody
	@RequestMapping(value = "confirmLinkDiscoverySave", method=RequestMethod.POST)
	public String confirmLinkDiscoverySave(String name,String upResourceId,String downResourceId,String upInterfaceId,String downInterfaceId,String upIp,String downIp) {
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(upResourceId)||StringUtils.isEmpty(downResourceId) ||StringUtils.isEmpty(upInterfaceId)
				||StringUtils.isEmpty(downInterfaceId)||StringUtils.isEmpty(upIp)||StringUtils.isEmpty(downIp)){
			return "链路资源信息错误";
		}

		Resource upResource=resourceService.get(upResourceId);
		Resource downResource=resourceService.get(downResourceId);
		if(upResource==null){
			return "上联设备不存在";
		}
		if(downResource==null){
			return "下联设备不存在";
		}


		NetworkInterface upNetworkInterface=networkInterfaceService.get(upInterfaceId);
		NetworkInterface downNetworkInterface=networkInterfaceService.get(downInterfaceId);
		if(upNetworkInterface==null){
			return "上联接口不存在";
		}
		if(downNetworkInterface==null){
			return "下联接口不存在";
		}

		/*int upCount = linkIndicatorService.checkExist(upInterfaceId);
		int downCount = linkIndicatorService.checkExist(downInterfaceId);
		if (upCount > 0) {
			return "上联接口已占用";
		}
		if (downCount>0) {
			return "上联接口已占用";
		}*/

		Date now=new Date();
		Resource resource=new Resource();
		resource.setCreateDate(now);
		resource.setName(name);
		resource.setResourceType(resourceTypeService.getByCode("31"));
		resource.setStatus("1");
		LinkIndicator linkIndicator =new LinkIndicator();
		linkIndicator.setUpLinkInterface(upInterfaceId);
		linkIndicator.setDownLinkInterface(downInterfaceId);
		linkIndicator.setGatherItem("11,21,22,31,32");
		linkIndicator.setUpLinkEquequipment(upResourceId);
		linkIndicator.setDownLinkEquequipment(downResourceId);
		linkIndicator.setStatus("UP");
		linkIndicator.setHealthDegree("100");
		linkIndicator.setAvailability("100");

		setLinkRate(resource,linkIndicator);
		delLinkDataFile(upIp,downIp);
		return "success";
	}




	@ResponseBody
	@RequestMapping(value = "confirmLinkDiscoveryDel", method=RequestMethod.POST)
	public String confirmLinkDiscoveryDel(String upIp,String downIp) {
		delLinkDataFile(upIp,downIp);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "confirmDiscoveryDel", method=RequestMethod.POST)
	public String confirmDiscoveryDel(String ip) {
		delDataFile(ip);
		return "success";
	}

	void delDataFile(String ip){
		String data="[";
		String path = Global.class.getResource("/discoverydata.txt").getPath();
		String JsonContext = Global.ReadFile(path);
		JSONArray json=new JSONArray(JsonContext);
		for(int  i = 0; i < json.length(); i++) {
			JSONObject item = json.getJSONObject(i);
			if(!item.getString("ip").equals(ip)){
				data+="{\"rdcommunity\":\""+item.getString("rdcommunity")+"\",\"ip\":\""+item.getString("ip")+"\"},";
			}
		}
		if(data.length()>1){
			data=data.substring(0,data.length()-1)+"]";
		}else{
			 data="";
		}
		File file=new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(data); // 往文件里写入字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	void delLinkDataFile(String upIp,String downIp){
		String path = Global.class.getResource("/discoverylinkdata.txt").getPath();
		String context = Global.ReadFile(path);
		String data="";
		JSONArray links = new JSONArray(context);
		for (int i = 0; i < links.length(); i++) {
			JSONObject item = links.getJSONObject(i);
			if(!(item.getString("up").equals(upIp)&&item.getString("down").equals(downIp))){
				HashMap<String,String> linkMap = new HashMap<String,String>();
				linkMap.put("up", item.getString("up"));
				linkMap.put("down", item.getString("down"));
				data+= JSONUtils.toJSONString(linkMap)+",";
			}
		}
		if(StringUtils.isNotBlank(data)){
			data="["+data.substring(0,data.length()-1)+"]";
		}
		File file=new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(data); // 往文件里写入字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}



	/**
	 * 服务器resource列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"server", ""})
	public String serverlist(HttpServletResponse response) {
		//exportData(response);
		return "modules/resource/serverList";
	}




	/**
	 * 服务器resource列表页面
	 */
	@RequestMapping(value = {"serverChart", ""})
	public String serverChart(HttpServletResponse response) {
		//exportData(response);
		return "modules/resource/serverchart";
	}


	/**
	 * 网络设备resource列表页面
	 */
	@RequestMapping(value = {"networkChart", ""})
	public String networkChart(HttpServletResponse response) {
		//exportData(response);
		return "modules/resource/networkchart";
	}

//	/**
//	 * 安全设备resource列表页面
//	 */
//	@RequestMapping(value = {"safetyEquipmentChart", ""})
//	public String safetyEquipmentChart(HttpServletResponse response) {
//		//exportData(response);
//		return "modules/resource/safetyEquipmentchart";
//	}

	/**
	 * 数据库列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"databaseList", ""})
	public String databaseList() {
		return "modules/resource/databaseResourceList";
	}



	/**
	 * 链路resource列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"link", ""})
	public String linkList() {
		return "modules/resource/linkList";
	}


	/**
	 * 中间件resource列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"middleware", ""})
	public String middlewareList() {
		return "modules/resource/middlewareList";
	}



	/**
	 * 应用resource列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"application", ""})
	public String applicationList() {
		return "modules/resource/applicationList";
	}


	/**
	 * 安全设备列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"safetyEquipmentList", ""})
	public String safetyEquipmentList() {
		return "modules/resource/safetyEquipmentResourceList";
	}



	/**
	 * 存储设备列表页面
	 */
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = {"storageEquipmentList", ""})
	public String storageEquipmentList() {
		return "modules/resource/storageEquipmentResourceList";
	}



	/**
	 * resource列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("1");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}


	/**
	 * 服务器列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "serverData")
	public Map<String, Object> serverData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("2");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}


	/**
	 * 服务器列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "linkData")
	public Map<String, Object> linkData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("3");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		List<Resource> list=page.getList();
		if(CheckObject.checkList(list)){
			for(Resource item:list){
				LinkIndicator linkIndicator =linkIndicatorService.getByResource(item.getId());
				if(linkIndicator!=null){
					linkIndicator.setStatus(linkIndicator.getStatus()==null?"-":linkIndicator.getStatus());
					linkIndicator.setUpRate(linkIndicator.getUpRate()==null?"-":linkIndicator.getUpRate());
					linkIndicator.setDownRate(linkIndicator.getDownRate()==null?"-":linkIndicator.getDownRate());
					linkIndicator.setUpUsedRate(linkIndicator.getUpUsedRate()==null?"-":(linkIndicator.getUpUsedRate()+"%"));
					linkIndicator.setDownUsedRate(linkIndicator.getDownUsedRate()==null?"-":(linkIndicator.getDownUsedRate()+"%"));
					linkIndicator.setHealthDegree(linkIndicator.getHealthDegree()==null?"-":(linkIndicator.getHealthDegree()+"%"));
					linkIndicator.setAvailability(linkIndicator.getAvailability()==null?"-":(linkIndicator.getAvailability()+"%"));
					item.setLinkIndicator(linkIndicator);
				}
			}
		}
		return getBootstrapData(page);
	}


	/**
	 * 中间件列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "middlewareData")
	public Map<String, Object> middlewareData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("5");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}



	/**
	 * 数据库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "databaseData")
	public Map<String, Object> databaseData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("4");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}

	/**
	 * 应用列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "applicationData")
	public Map<String, Object> applicationData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("8");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}




	/**
	 * 安全设备列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "safetyEquipmentData")
	public Map<String, Object> safetyEquipmentData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("6");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}


	/**
	 * 存储设备列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:list")
	@RequestMapping(value = "storageEquipmentData")
	public Map<String, Object> storageEquipmentData(Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
		resource.setSelectResourceTypeCodeIds("7");
		Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response), resource);
		for(Resource r:page.getList()){
			r.setMokaCollector(resourceService.getCollector(r.getMokaCollector().getId()));
		}
		return getBootstrapData(page);
	}






	/**
	 *添加链路
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "linkForm")
	public String linkForm(Resource resource, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}

		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		return "modules/resource/linkForm";
	}

	/**
	 *添加存储设备
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "storageEquipmentForm")
	public String storageEquipmentForm(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}

		if(StringUtils.isEmpty(resourceBaseInfo.getDelay())){
			resourceBaseInfo.setDelay(Global.getConfig("delay"));
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getRepeatnum())){
			resourceBaseInfo.setRepeatnum(Global.getConfig("repeatnum"));
		}
		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/storageEquipmentForm";
	}


	/**
	 *添加安全设备
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "safetyEquipmentForm")
	public String safetyEquipmentForm(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getDelay())){
			resourceBaseInfo.setDelay(Global.getConfig("delay"));
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getRepeatnum())){
			resourceBaseInfo.setRepeatnum(Global.getConfig("repeatnum"));
		}
		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/safetyEquipmentForm";
	}

	/**
	 *添加应用
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "applicationForm")
	public String applicationForm(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> typeList=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","application_indicator_type");
		if(dictType!=null){
			typeList=dictTypeService.getDictValueList(dictType);
		}
		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("typeList", typeList);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/applicationForm";
	}



	/**
	 * 查看，增加，编辑resource表单页面
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getDelay())){
			resourceBaseInfo.setDelay(Global.getConfig("delay"));
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getRepeatnum())){
			resourceBaseInfo.setRepeatnum(Global.getConfig("repeatnum"));
		}


		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/resourceForm";
	}


	/**
	 * 增加服务器resource表单页面
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "serverForm")
	public String serverForm(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getDelay())){
			resourceBaseInfo.setDelay(Global.getConfig("delay"));
		}
		if(StringUtils.isEmpty(resourceBaseInfo.getRepeatnum())){
			resourceBaseInfo.setRepeatnum(Global.getConfig("repeatnum"));
		}
		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/serverForm";
	}



	/**
	 *添加中间件
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "middlewareForm")
	public String middlewareForm(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}
		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/middlewareForm";
	}

	/**
	 *添加中间件
	 */
	@RequiresPermissions(value={"resource:resource:view","resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "databaseForm")
	public String databaseForm(Resource resource, ResourceBaseInfo resourceBaseInfo, Model model) {

		if(StringUtils.isBlank(resource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
			resourceBaseInfo.setPort("161");
		}
		List<DictValue> securityLevel=new ArrayList<>();
		DictType dictType=dictTypeService.findUniqueByProperty("type","security_level");
		if(dictType!=null){
			securityLevel=dictTypeService.getDictValueList(dictType);
		}
		model.addAttribute("mokaCollectorList", resourceService.getCollectorList());
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("securityLevel", securityLevel);
		model.addAttribute("resource", resource);
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		return "modules/resource/databaseForm";
	}
















	@ResponseBody
	@RequestMapping(value = "checkIp")
	public Json check(String type ,String ip,String port,String rdcommunity,String resourceType) throws Exception{
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("[{");
		Resource resource = resourceService.checkIp(ip,resourceType);
		if(resource!=null){
			stringBuffer.append("\"status\":401");
		}else {
			if ("1".equals(type) || "2".equals(type)) {
				ResourceOidValue oid = GeneralMethod.getSysOid(ip, rdcommunity, port, "600", "4");
				if (oid != null && StringUtils.isNotBlank(oid.getValue())) {
					stringBuffer.append("\"status\":200");
				}
			} else {
				stringBuffer.append("\"status\":402");
			}
		}
		stringBuffer.append("}]");
		return new Json(stringBuffer.toString());
	}


	/**
	 * 检查数据库ip
	 * @param ip
	 * @param port
	 * @param resourceType
	 * @param accessConfigUserName
	 * @param accessConfigPassword
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "checkDatabaseIp")
	public Json checkDatabase(String ip,String port,String resourceType,String accessConfigUserName,String accessConfigPassword) throws Exception{

		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("[{");
		Resource resource = resourceService.checkMiddlewareIp(ip,port,resourceType,accessConfigUserName, accessConfigPassword);
		if(resource!=null){
			stringBuffer.append("\"status\":401");
		}else {
			if(checkDataBase(ip,port,accessConfigUserName,accessConfigPassword,resourceTypeService.get(resourceType))){
				stringBuffer.append("\"status\":200");
			}else{
				stringBuffer.append("\"status\":402");
			}
		}
		stringBuffer.append("}]");
		return new Json(stringBuffer.toString());
	}


	/**
	 * 检查中间件ip
	 * @param ip
	 * @param port
	 * @param resourceType
	 * @param accessConfigUserName
	 * @param accessConfigPassword
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "checkMiddlewareIp")
	public Json checkMiddlewareIp(String ip,String port,String resourceType,String accessConfigUserName,String accessConfigPassword) throws Exception{

		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("[{");
		Resource resource = resourceService.checkMiddlewareIp(ip,port,resourceType,accessConfigUserName, accessConfigPassword);
		if(resource!=null){
			stringBuffer.append("\"status\":401");
		}else {
			if(checkMiddleware(resourceTypeService.get(resourceType),ip,port,accessConfigUserName,accessConfigPassword)){
				stringBuffer.append("\"status\":200");
			}else{
				stringBuffer.append("\"status\":402");
			}
		}
		stringBuffer.append("}]");
		return new Json(stringBuffer.toString());
	}


	public static boolean  checkMiddleware(ResourceType resourceType,String ip,String port,String user,String password){

		if(resourceType==null){
			return false;
		}else if("51".equals(resourceType.getCode())){  //tomcat
			try{
				return checkTomcatUrl("http://"+ip+":"+port+"/manager/status?XML=true",user,password);
			}catch(Exception e){
				return false;
			}
		}else if("52".equals(resourceType.getCode())) { //nginx
			return true;
		}else if("53".equals(resourceType.getCode())) { //weblogic
			return true;
		}else{
				return false;
		}
	}


	public static boolean checkDataBase(String ip,String port,String user,String password,ResourceType resourceType){
		if(resourceType==null){
			return false;
		}else if("41".equals(resourceType.getCode())){  //mysql
			try{
				Class.forName("com.mysql.jdbc.Driver");
				String url="jdbc:mysql://"+ip+":"+port+"/mysql";
				Connection conn = DriverManager.getConnection(url,user,password);
				conn.close();
				return true;
			}catch(Exception e){
				return false;
			}
		}else if("42".equals(resourceType.getCode())){  //sqlserver
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String connectDB="jdbc:sqlserver://"+ip+":"+port;
				Connection conn=DriverManager.getConnection(connectDB,user,password);
				conn.close();
				return true;
			}catch(Exception e){
				return false;
			}
		}else if("43".equals(resourceType.getCode())){  //oracle
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");     // 加载Oracle驱动程序
				String url = "jdbc:oracle:thin:@"+ip+":"+port+":orcl";
				Connection conn = DriverManager.getConnection(url, user, password);// 获取连接
				conn.close();
				return true;
			}catch(Exception e){
				return false;
			}
		}else if("44".equals(resourceType.getCode())){  //sybase
			try{
				//Class.forName("com.sybase.jdbc3.jdbc.SybDriver");     // 加载sybase驱动程序
				String url = "jdbc:sybase:Tds:"+ip+":"+port;
				Connection conn = DriverManager.getConnection(url, user, password);// 获取连接
				conn.close();
				return true;
			}catch(Exception e){
				return false;
			}
		}else {
			return false;
		}
	}



	public static boolean checkTomcatUrl(String webUrl, String name, String password) {
		URL url;
			try {
				url =new URL(webUrl);
				HttpURLConnection con = null;
				InputStream in = null;
				Authenticator authen = new NSAuthenticator(name, password);
					Authenticator.setDefault(authen);
					con = (HttpURLConnection)url.openConnection();
				    con.setConnectTimeout(5*1000); //链接超时5秒
					con.setDoInput(true);
					in = con.getInputStream();

				    return true;

			} catch (Exception e) {
				url=null;
				return false;
			}
	}








































	/**
	 * 保存链路resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "linkFormSave")
	public String linkFormSave(Resource resource, Model model, LinkIndicator linkIndicator, RedirectAttributes redirectAttributes) throws Exception{


		/*
		private String status;		// 状态
		private String upRate;		// 上行速率
		private String downRate;		// 下行速率
		private String upUsedRate;		// 上行利用率
		private String downUsedRate;		// 下行利用率
		*/
		resource.setResourceType(resourceTypeService.getByCode("31"));
		resource.setStatus("1");



		setLinkRate(resource,linkIndicator);

		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/link?repage";
	}


	void setLinkRate(Resource resource ,LinkIndicator linkIndicator){
		if(linkIndicator==null){
			linkIndicator=linkIndicatorService.getByResource(resource.getId());
		}
		if(linkIndicator==null){
			return;
		}

		NetworkInterface upNetworkInterface=networkInterfaceService.get(linkIndicator.getUpLinkInterface());
		NetworkInterface downNetworkInterface=networkInterfaceService.get(linkIndicator.getDownLinkInterface());
		if(upNetworkInterface==null||downNetworkInterface==null){
			return;
		}


		Resource upResource=resourceService.get(upNetworkInterface.getResourceId());
		Resource downResource=resourceService.get(downNetworkInterface.getResourceId());
		if(upResource==null||downResource==null){
			return;
		}

        List<String> gatherItemList=new ArrayList<>();
		String gatherItem=linkIndicator.getGatherItem();
		if(StringUtils.isNotBlank(gatherItem)){
			String[] items=gatherItem.split(",");
			for(String item:items){
				gatherItemList.add(item);
			}
		}



		linkIndicator.setUpLinkEquequipment(upNetworkInterface.getResourceId());
		linkIndicator.setDownLinkEquequipment(downNetworkInterface.getResourceId());
		if("0.00Mbps".equals(upNetworkInterface.getCapacity())){
			linkIndicator.setCapacity(downNetworkInterface.getCapacity());
		}else{
			linkIndicator.setCapacity(upNetworkInterface.getCapacity());
		}

		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0

		if(gatherItemList.contains("11")) {
			linkIndicator.setStatus("UP");
			linkIndicator.setHealthDegree("100");
			linkIndicator.setAvailability("100");
			ResourceOidValue upOid = GeneralMethod.getSysOid(upResource.getIp(), upResource.getResourceBaseInfo().getRdcommunity(), upResource.getResourceBaseInfo().getPort(), "600", "4");
			ResourceOidValue downOid = GeneralMethod.getSysOid(downResource.getIp(), downResource.getResourceBaseInfo().getRdcommunity(), downResource.getResourceBaseInfo().getPort(), "600", "4");
			if (upOid == null || upOid.getValue() == null || downOid == null || downOid.getValue() == null) {
				linkIndicator.setStatus("DOWN");
				linkIndicator.setHealthDegree("0");
				linkIndicator.setAvailability("0");
			}
		}



		double upRateNum=0.00;
		if(gatherItemList.contains("21")) {
			NetInterfaceInOutRate upInput = networkInterfaceService.findTopRate(upNetworkInterface.getId(), "input");
			double upInputRate = 0.00;
			if (upInput != null) {
				upInputRate = Transformation.null2Double(upInput.getRate());
			}
			NetInterfaceInOutRate downOut = networkInterfaceService.findTopRate(downNetworkInterface.getId(), "output");
			double downOutRate = 0.00;
			if (downOut != null) {
				downOutRate = Transformation.null2Double(downOut.getRate());
			}
			upRateNum= (upInputRate + downOutRate) / 2;
			String upRate = initRate(String.valueOf(upRateNum));
			linkIndicator.setUpRate(upRate);
		}


		double downRateNum=0.00;
		if(gatherItemList.contains("22")) {
			NetInterfaceInOutRate upOut = networkInterfaceService.findTopRate(upNetworkInterface.getId(), "output");
			double upOutRate = 0.00;
			if (upOut != null) {
				upOutRate = Transformation.null2Double(upOut.getRate());
			}
			NetInterfaceInOutRate downInput = networkInterfaceService.findTopRate(downNetworkInterface.getId(), "input");
			double downInputRate = 0.00;
			if (downInput != null) {
				downInputRate = Transformation.null2Double(downInput.getRate());
			}
			 downRateNum = (upOutRate + downInputRate) / 2;
			String downRate = initRate(String.valueOf(downRateNum));
			linkIndicator.setDownRate(downRate);
		}



		String upUsedRate="";
		String downUsedRate="";
		if("0.00Mbps".equals(linkIndicator.getCapacity())){
			 upUsedRate= "0.00";
			 downUsedRate= "0.00";
		}else{
			if(gatherItemList.contains("31")) {
				upUsedRate = df.format(upRateNum / ((Transformation.null2Double(linkIndicator.getCapacity().replaceAll("Mbps", "")) * 1000 * 1000)) * 100);
			}
			if(gatherItemList.contains("32")) {
				downUsedRate = df.format(downRateNum / ((Transformation.null2Double(linkIndicator.getCapacity().replaceAll("Mbps", "")) * 1000 * 1000)) * 100);
			}
		}

		if(gatherItemList.contains("31")) {
			linkIndicator.setUpUsedRate(upUsedRate);
		}
		if(gatherItemList.contains("32")) {
			linkIndicator.setDownUsedRate(downUsedRate);
		}


		ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
		resourceGatherItem.setDescription("link");
		resourceGatherItem.setCollectionClass(LinkRateIndicator.class.getPackage().getName()+".LinkRateIndicator");


		resourceService.saveLink(resource, linkIndicator,resourceGatherItem);//保存



	}

	String initRate(String rate){
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
		double result= Transformation.null2Double(rate)/1024/1024;
		if(result>1.0){
			return df.format(result)+"Mbps";
		}else{
			result=result*1024;
			return df.format(result)+"Kbps";
		}
	}




	@ResponseBody
	@RequestMapping(value = "checkExist")
	public Json checkExist(String upLinkInterface,String downLinkInterface) throws Exception{
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("[{");
		NetworkInterface upNetworkInterface =networkInterfaceService.get(upLinkInterface);
		NetworkInterface downNetworkInterface =networkInterfaceService.get(downLinkInterface);
		if(upNetworkInterface==null||downNetworkInterface==null){
			stringBuffer.append("\"status\":401");
		}else {
			int upCount = linkIndicatorService.checkExist(upLinkInterface);
			int downCount = linkIndicatorService.checkExist(downLinkInterface);
			if (upCount > 0||downCount>0) {
				stringBuffer.append("\"status\":402");
			} else {
				stringBuffer.append("\"status\":200");
			}
		}
		stringBuffer.append("}]");
		return new Json(stringBuffer.toString());
	}



	/**
	 * 保存中间件resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "middlewareFormSave")
	public String middlewareFormSave(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}

		resourceBaseInfo.setPort(resourceBaseInfo.getAccessConfigPort());
		ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
		resourceGatherItem.setDescription("middleware");
		resourceGatherItem.setCollectionClass(MiddlewareIndicator.class.getPackage().getName()+".MiddlewareIndicator");

		//新增或编辑表单保存
		resourceService.save(resource, resourceBaseInfo,resourceGatherItem);//保存
		setDataBaseMiddle(resource);
		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/middleware?repage";
	}




	/**
	 * 保存安全设备resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "safetyEquipmentFormSave")
	public String safetyEquipmentFormSave(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}
		if("1".equals(resourceBaseInfo.getManagerType())||"2".equals(resourceBaseInfo.getManagerType())) {
			ResourceOidValue desc = GeneralMethod.getDescription(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setDescription((resource.getDescription() == null ? "" : resource.getDescription()) + desc.getValue() == null ? "" : desc.getValue());
			ResourceOidValue sysOid = GeneralMethod.getSysOid(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysOid(sysOid.getValue() == null ? "" : sysOid.getValue());
			ResourceOidValue subNetMask = GeneralMethod.getSubNetMask(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSubnetmask(subNetMask.getValue() == null ? "" : subNetMask.getValue());
			ResourceOidValue sysName = GeneralMethod.getSysName(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysName(sysName.getValue() == null ? "" : sysName.getValue());
		}
		//新增或编辑表单保存
		resourceService.save(resource, resourceBaseInfo,null);//保存
		savesafetyEquipmentIndicator(resource);
		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/safetyEquipmentList?repage";
	}



	void savesafetyEquipmentIndicator(Resource resource){
		if(resource.getId()!=null){
			resource=resourceService.get(resource.getId());
			ResourceType resourceType =resourceTypeService.getParentByChild(resource.getResourceType().getId());
			if(resourceType==null||StringUtils.isEmpty(resourceType.getCode()) ){
				return;
			}
			saveICMPTime(resource,true);

				if(resource.getResourceType().getCode().equals("61")) {        // 61 防火墙
					if("3".equals(resource.getManufacturer().getCode())){        //厂商 天融信
					    saveCpuFirewall(resource, true);
						saveMemoryFirewall(resource, true);
						saveNetworkInterfaceFirewall(resource,true);
					    ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
					    resourceGatherItem.setDescription("firewallIndicator");
					    resourceGatherItem.setResourceId(resource.getId());
					    resourceGatherItem.setCollectionClass(TopsecFirewallIndicator.class.getPackage().getName()+".TopsecFirewallIndicator");
						resourceGatherItemService.save(resourceGatherItem);
					}else if("5".equals(resource.getManufacturer().getCode())){        //厂商 华为
						saveCpuFirewallHuawei(resource, true);
						saveMemoryFirewallHuawei(resource, true);
						saveNetworkInterfaceFirewallHuawei(resource,true);
					}else if("6".equals(resource.getManufacturer().getCode())){        //厂商 H3C
						saveCpuFirewallH3C(resource, true);
						saveMemoryFirewallH3C(resource, true);
						saveNetworkInterfaceFirewallH3C(resource,true);
					}else if("8".equals(resource.getManufacturer().getCode())){        //厂商  绿盟 Nsfocus
						saveCpuNsfocus(resource, true);
						saveMemoryNsfocus(resource, true);
						saveNetworkInterfaceNsfocus(resource,true);


					}else if("9".equals(resource.getManufacturer().getCode())){        //厂商  天泰 待开发
						saveCpuFirewall(resource, true);
						saveMemoryFirewall(resource, true);
						saveNetworkInterfaceFirewall(resource,true);
					}

				}else if(resource.getResourceType().getCode().equals("62")){    // 62 入侵检测设备
					if("3".equals(resource.getManufacturer().getCode())){        //厂商 天融信
						saveCpuIDS(resource, true);
						saveMemoryIDS(resource, true);
						saveNetworkInterfaceIDS(resource,true);
						saveAttack(resource, true);
						ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
						resourceGatherItem.setDescription("idsIndicator");
						resourceGatherItem.setResourceId(resource.getId());
						resourceGatherItem.setCollectionClass(TopsecIDSIndicator.class.getPackage().getName()+".TopsecIDSIndicator");
						resourceGatherItemService.save(resourceGatherItem);

					}else if("4".equals(resource.getManufacturer().getCode())){              //厂商  sangfor
						saveCpuNGIDS(resource, true);
					    saveMemoryNGIDS(resource, true);
					    saveNetworkInterfaceNGIDS(resource,true);

					}else if("8".equals(resource.getManufacturer().getCode())){        //厂商  绿盟 Nsfocus
						saveCpuNsfocus(resource, true);
						saveMemoryNsfocus(resource, true);
						saveNetworkInterfaceNsfocus(resource,true);
					}
				}
				else if(resource.getResourceType().getCode().equals("63")){     //63 安全审计设备-临时添加，待开发完善
				}
				else if(resource.getResourceType().getCode().equals("64")){     //64 漏洞扫描设备
					if("3".equals(resource.getManufacturer().getCode())) {        //厂商 天融信
						saveCpuNessus(resource, true);
						saveMemoryNessus(resource, true);
						saveNetworkInterfaceNessus(resource, true);
						saveLoophole(resource, true);
						ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
						resourceGatherItem.setDescription("nessusIndicator");
						resourceGatherItem.setResourceId(resource.getId());
						resourceGatherItem.setCollectionClass(TopsecNessusIndicator.class.getPackage().getName() + ".TopsecNessusIndicator");
						resourceGatherItemService.save(resourceGatherItem);
					}
				}else if(resource.getResourceType().getCode().equals("65")){     //65 防毒墙-临时添加，待开发完善
				}
			setHealthDegreeValue(resource);
			setAvailabilityRateValue(resource);

		}
	}



	/**
	 * 保存存储设备resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "storageEquipmentFormSave")
	public String storageEquipmentFormSave(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}
		if("1".equals(resourceBaseInfo.getManagerType())||"2".equals(resourceBaseInfo.getManagerType())) {
			ResourceOidValue desc = GeneralMethod.getDescription(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setDescription((resource.getDescription() == null ? "" : resource.getDescription()) + desc.getValue() == null ? "" : desc.getValue());
			ResourceOidValue sysOid = GeneralMethod.getSysOid(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysOid(sysOid.getValue() == null ? "" : sysOid.getValue());
			ResourceOidValue subNetMask = GeneralMethod.getSubNetMask(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSubnetmask(subNetMask.getValue() == null ? "" : subNetMask.getValue());
			ResourceOidValue sysName = GeneralMethod.getSysName(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysName(sysName.getValue() == null ? "" : sysName.getValue());
		}

		//新增或编辑表单保存
		resourceService.save(resource, resourceBaseInfo,null);//保存
		saveICMPTime(resource,true);
		saveCpuStorageEquipment(resource,true);
		saveMemoryStorageEquipment(resource,true);
		saveNetworkInterfaceStorageEquipment(resource,true);
		saveDiskStorageEquipment(resource,true);
		setHealthDegreeValue(resource);
		setAvailabilityRateValue(resource);

		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/storageEquipmentList?repage";
	}


	/**
	 * 保存数据库resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "databaseFormSave")
	public String databaseFormSave(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}

		resourceBaseInfo.setPort(resourceBaseInfo.getAccessConfigPort());
		ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
		resourceGatherItem.setDescription("database");
		resourceGatherItem.setCollectionClass(DatabaseIndicator.class.getPackage().getName()+".DatabaseIndicator");

		//新增或编辑表单保存
		resourceService.save(resource, resourceBaseInfo,resourceGatherItem);//保存
		setDataBaseMiddle(resource);

		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/databaseList?repage";
	}



	/**
	 * 保存应用resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "applicationFormSave")
	public String applicationFormSave(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}
		ResourceGatherItem resourceGatherItem = new ResourceGatherItem();
		resourceGatherItem.setDescription("application");
		resourceGatherItem.setCollectionClass(ApplicationIndicator.class.getPackage().getName()+".ApplicationIndicator");
		//新增或编辑表单保存
		resourceService.saveApplication(resource, resourceBaseInfo,resourceGatherItem);//保存
		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/application?repage";
	}


	/**
	 * 保存网络设备resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}
		if("1".equals(resourceBaseInfo.getManagerType())||"2".equals(resourceBaseInfo.getManagerType())) {
			ResourceOidValue desc = GeneralMethod.getDescription(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setDescription((resource.getDescription() == null ? "" : resource.getDescription()) + desc.getValue() == null ? "" : desc.getValue());
			ResourceOidValue sysOid = GeneralMethod.getSysOid(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysOid(sysOid.getValue() == null ? "" : sysOid.getValue());
			ResourceOidValue subNetMask = GeneralMethod.getSubNetMask(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSubnetmask(subNetMask.getValue() == null ? "" : subNetMask.getValue());
			ResourceOidValue sysName = GeneralMethod.getSysName(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysName(sysName.getValue() == null ? "" : sysName.getValue());
		}
		//新增或编辑表单保存
		resourceService.save(resource, resourceBaseInfo,null);//保存
		saveIndicator(resource);
		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/list?repage";
	}



	/**
	 * 保存服务resource
	 */
	@RequiresPermissions(value={"resource:resource:add","resource:resource:edit"},logical=Logical.OR)
	@RequestMapping(value = "serverSave")
	public String serverSave(Resource resource, Model model, ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resource)){
			return form(resource, resourceBaseInfo, model);
		}
		if("1".equals(resourceBaseInfo.getManagerType())||"2".equals(resourceBaseInfo.getManagerType())) {
			ResourceOidValue desc = GeneralMethod.getDescription(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setDescription((resource.getDescription() == null ? "" : resource.getDescription()) + desc.getValue() == null ? "" : desc.getValue());
			ResourceOidValue sysOid = GeneralMethod.getSysOid(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysOid(sysOid.getValue() == null ? "" : sysOid.getValue());
			ResourceOidValue subNetMask = GeneralMethod.getSubNetMask(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSubnetmask(subNetMask.getValue() == null ? "" : subNetMask.getValue());
			ResourceOidValue sysName = GeneralMethod.getSysName(resource.getIp(), resourceBaseInfo.getRdcommunity(), resourceBaseInfo.getPort(), resourceBaseInfo.getDelay(), resourceBaseInfo.getRepeatnum());
			resource.setSysName(sysName.getValue() == null ? "" : sysName.getValue());
		}
		//新增或编辑表单保存
		resourceService.save(resource, resourceBaseInfo,null);//保存
		saveIndicator(resource);
		addMessage(redirectAttributes, "保存资源成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resource/server?repage";
	}

	void saveIndicator(Resource resource){
		if(resource.getId()!=null){
			resource=resourceService.get(resource.getId());
			ResourceType resourceType =resourceTypeService.getParentByChild(resource.getResourceType().getId());
			if(resourceType==null||StringUtils.isEmpty(resourceType.getCode()) ){
				return;
			}
			saveICMPTime(resource,true);
			if("1".equals(resourceType.getCode())) { //网络设备
				if(resource.getResourceType().getCode().equals("11")) {        // 11 路由器
					if("2".equals(resource.getManufacturer().getCode())){        //厂商思科
						saveCpuRouterCisco(resource, true);
						saveMemoryRouterCisco(resource, true);
						saveNetworkInterfaceRouterCisco(resource,true);
					}else if("5".equals(resource.getManufacturer().getCode())){        //厂商华为
						saveCpuRouterHuawei(resource, true);
						saveMemoryRouterHuawei(resource, true);
						saveNetworkInterfaceRouterHuawei(resource,true);
					}else if("6".equals(resource.getManufacturer().getCode())){        //厂商H3C
						saveCpuRouterH3C(resource, true);
						saveMemoryRouterH3C(resource, true);
						saveNetworkInterfaceRouterH3C(resource,true);
					}else{
						saveCpuRouter(resource,true);
						saveMemoryRouter(resource,true);
						saveNetworkInterfaceRouter(resource,true);
					}


				}else if(resource.getResourceType().getCode().equals("12")){    // 12 交换机
					if("2".equals(resource.getManufacturer().getCode())){        //厂商思科
						saveCpuExchangeCisco(resource, true);
						saveMemoryExchangeCisco(resource, true);
						saveNetworkInterfaceExchangeCisco(resource,true);
					}else if("5".equals(resource.getManufacturer().getCode())){        //厂商华为
						saveCpuExchangeHuawei(resource, true);
						saveMemoryExchangeHuawei(resource, true);
						saveNetworkInterfaceExchangeHuawei(resource,true);
					}else if("6".equals(resource.getManufacturer().getCode())){        //厂商H3C
						saveCpuExchangeH3C(resource, true);
						saveMemoryExchangeH3C(resource, true);
						saveNetworkInterfaceExchangeH3C(resource,true);
					}else if("7".equals(resource.getManufacturer().getCode())){        //厂商捷锐
						saveCpuExchangeRuijie(resource, true);
						saveMemoryExchangeRuijie(resource, true);
						saveNetworkInterfaceExchangeRuijie(resource,true);
					}

				}
			}else if("2".equals(resourceType.getCode())){ // 服务器
				if(resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
					saveProcessInfoWindows(resource,true);
					saveCpuWindows(resource,true);
					saveMemoryWindows(resource,true);
					saveNetworkInterfaceWindows(resource,true);
					saveSoftwareWindows(resource,true);
					saveDiskWindows(resource,true);
				}else if(resource.getResourceType().getCode().equals("22")){    // 22:Linux服务器
					OperatingSystem operatingSystem=null;
					if(StringUtils.isNotBlank(resource.getOperatingSystemId())){
						operatingSystem=operatingSystemService.get(resource.getOperatingSystemId());
					}
					if(operatingSystem!=null){
						if("11".equals(operatingSystem.getCode())){
							saveProcessInfoLinux(resource, true);
							saveCpuLinux(resource, true);
							saveMemoryLinux(resource, true);
							saveNetworkInterfaceLinux(resource, true);
							saveSoftwareLinux(resource, true);
							saveDiskLinux(resource, true);
						}else if("12".equals(operatingSystem.getCode())){

						}else{
							saveProcessInfoLinux(resource, true);
							saveCpuLinux(resource, true);
							saveMemoryLinux(resource, true);
							saveNetworkInterfaceLinux(resource, true);
							saveSoftwareLinux(resource, true);
							saveDiskLinux(resource, true);
						}

					}else {
						saveProcessInfoLinux(resource, true);
						saveCpuLinux(resource, true);
						saveMemoryLinux(resource, true);
						saveNetworkInterfaceLinux(resource, true);
						saveSoftwareLinux(resource, true);
						saveDiskLinux(resource, true);
					}
				}
			}

			setHealthDegreeValue(resource);
			setAvailabilityRateValue(resource);
		}
	}


	/**
	 * ICMP响应时间
	 * @param resource
	 * @param init
	 */
	synchronized void saveICMPTime(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;

				if(init) {
					resourceGatherItem  =new ResourceGatherItem();
					resourceGatherItem.setDescription("ICMP");
					resourceGatherItem.setType("9");
					resourceGatherItem.setResourceId(resource.getId());
					if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
						resourceGatherItem.setCollectionClass(Icmp.class.getPackage().getName()+".Icmp");
					}
					resourceGatherItemService.save(resourceGatherItem);
				}

		}


	/**
	 * 路由器CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuRouter(Resource resource,boolean init) {

		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;

				if(init) {
					resourceGatherItem  =new ResourceGatherItem();
					resourceGatherItem.setDescription("CPU");
					resourceGatherItem.setType("3");
					resourceGatherItem.setResourceId(resource.getId());
					if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
						resourceGatherItem.setCollectionClass(GeneralRouterCpuByV1V2.class.getPackage().getName()+".GeneralRouterCpuByV1V2");
					}
					resourceGatherItemService.save(resourceGatherItem);
				}
	}



	/**
	 * 思科路由器CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuRouterCisco(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;

		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(CiscoRouterCpuByV1V2.class.getPackage().getName()+".CiscoRouterCpuByV1V2");
			}

		}

		//CpuUsedRate cpuUsedRate = new CpuUsedRate();
		//cpuUsedRate.setCreateDate(date);
		//cpuUsedRate.setResourceId(resource.getId());
		//cpuUsedRate.setUsedRate(CpuIndicatorCalculateRouterCisco.getCpuUse(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())));
		//cpuService.saveList(resourceGatherItem, null, resource, cpuUsedRate);
		//System.out.println(resource.getName()+"获取CPU方法结束："+(System.currentTimeMillis()-star));
	}


	/**
	 * 华为路由器CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuRouterHuawei(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;

		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiRouterCpuByV1V2.class.getPackage().getName()+".HuaweiRouterCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}


	/**
	 * H3C路由器CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuRouterH3C(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;

		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CRouterCpuByV1V2.class.getPackage().getName()+".H3CRouterCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}




	/**
	 * 入侵检测设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuNGIDS(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if (Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()) == 1 || Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()) == 2) {
				resourceGatherItem.setCollectionClass(SangforIpsCpuByV1V2.class.getPackage().getName() + ".SangforIpsCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}




	/**
	 * 防火墙设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuFirewall(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecFireWallCpuByV1V2.class.getPackage().getName()+".TopsecFireWallCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}

	/**
	 * 入侵检测设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuIDS(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecIDSCpuByV1V2.class.getPackage().getName()+".TopsecIDSCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}




	/**
	 * 漏洞扫描设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuNessus(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecNessusCpuByV1V2.class.getPackage().getName()+".TopsecNessusCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}


	/**
	 * 华为防火墙设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuFirewallHuawei(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiFireWallCpuByV1V2.class.getPackage().getName()+".HuaweiFireWallCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
		//System.out.println(resource.getName()+"获取CPU方法结束："+(System.currentTimeMillis()-star));
	}

	/**
	 * 华为防火墙设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuFirewallH3C(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CFireWallCpuByV1V2.class.getPackage().getName()+".H3CFireWallCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}




	/**
	 * 绿盟设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuNsfocus(Resource resource,boolean init) {

		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;


		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(NsfocusCpuByV1V2.class.getPackage().getName()+".NsfocusCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * 思科交换机CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuExchangeCisco(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(CiscoExchangeCpuByV1V2.class.getPackage().getName()+".CiscoExchangeCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}

	/**
	 * Huawei 交换机CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuExchangeHuawei(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiExchangeCpuByV1V2.class.getPackage().getName()+".HuaweiExchangeCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}





	/**
	 * H3C交换机CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuExchangeH3C(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CExchangeCpuByV1V2.class.getPackage().getName()+".H3CExchangeCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * Ruijie交换机CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuExchangeRuijie(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(resource.getResourceType()==null){
			return;
		}
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(RuijieExchangeCpuByV1V2.class.getPackage().getName()+".RuijieExchangeCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * WindowsCPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuWindows(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(WindowsCpuByV1V2.class.getPackage().getName()+".WindowsCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}

	/**
	 * LinuxCPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuLinux(Resource resource,boolean init) {
		Date date=new Date();
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(LinuxCpuByV1V2.class.getPackage().getName()+".LinuxCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}




	/**
	 * 存储设备CPU信息
	 * @param resource
	 * @param init
	 */
	synchronized void saveCpuStorageEquipment(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem  =new ResourceGatherItem();
			resourceGatherItem.setDescription("CPU");
			resourceGatherItem.setType("3");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(StorageEquipmentCpuByV1V2.class.getPackage().getName()+".StorageEquipmentCpuByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}







	/**
	 * 进程列表
	 * @param resource
	 */
 	synchronized void saveProcessInfoWindows(Resource resource,boolean init){
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("process");
			resourceGatherItem.setType("6");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(WindowsProcesByV1V2.class.getPackage().getName()+".WindowsProcesByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}


	synchronized void saveProcessInfoLinux(Resource resource,boolean init){
		List<Process> list=null;
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("process");
			resourceGatherItem.setType("6");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(LinuxProcesByV1V2.class.getPackage().getName()+".LinuxProcesByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}



	/**
	 * 路由器网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceRouter(Resource resource,boolean init){
		ResourceGatherItem resourceGatherItem=null;
			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(GeneralRouterNetworkInterfaceByV1V2.class.getPackage().getName()+".GeneralRouterNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}
	}


	/**
	 * 思科路由器网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceRouterCisco(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if (init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());

			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(CiscoRouterNetworkInterfaceByV1V2.class.getPackage().getName()+".CiscoRouterNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}



	/**
	 * 华为路由器网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceRouterHuawei(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if (init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());

			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiRouterNetworkInterfaceByV1V2.class.getPackage().getName()+".HuaweiRouterNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * H3C路由器网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceRouterH3C(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if (init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());

			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CRouterNetworkInterfaceByV1V2.class.getPackage().getName()+".H3CRouterNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * 思科交换机网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceExchangeCisco(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(CiscoExchangeNetworkInterfaceByV1V2.class.getPackage().getName()+".CiscoExchangeNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}


	/**
	 * Huawei交换机网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceExchangeHuawei(Resource resource,boolean init){
		ResourceGatherItem resourceGatherItem=null;
			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(HuaweiExchangeNetworkInterfaceByV1V2.class.getPackage().getName()+".HuaweiExchangeNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}
	}



	/**
	 * 思科交换机网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceExchangeH3C(Resource resource,boolean init){
		ResourceGatherItem resourceGatherItem=null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CExchangeNetworkInterfaceByV1V2.class.getPackage().getName()+".H3CExchangeNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * Ruijie交换机网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceExchangeRuijie(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(RuijieExchangeNetworkInterfaceByV1V2.class.getPackage().getName()+".RuijieExchangeNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}




	/**
	 * 入侵检测设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceNGIDS(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(SangforIpsNetworkInterfaceByV1V2.class.getPackage().getName()+".SangforIpsNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * 防火墙设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceFirewall(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(TopsecFirewallNetworkInterfaceByV1V2.class.getPackage().getName()+".TopsecFirewallNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * 入侵检测设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceIDS(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecIDSNetworkInterfaceByV1V2.class.getPackage().getName()+".TopsecIDSNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * 漏洞扫描设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceNessus(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecNessusNetworkInterfaceByV1V2.class.getPackage().getName()+".TopsecNessusNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}
	/**
	 * 安全攻击总数
	 * @param resource
	 */
	synchronized void saveAttack(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("Attack");
			resourceGatherItem.setType("13");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass("com.colnman.moka.collector.deviceUpdateIndicator.safetyequipment.attack.AttackByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * 违规事件总数
	 * @param resource
	 */
	synchronized void saveViolations(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("Violations");
			resourceGatherItem.setType("14");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass("com.colnman.moka.collector.deviceUpdateIndicator.safetyequipment.violations.ViolationsByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * 安全漏洞总数
	 * @param resource
	 */
	synchronized void saveLoophole(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("Loophole");
			resourceGatherItem.setType("15");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass("com.colnman.moka.collector.deviceUpdateIndicator.safetyequipment.loophole.LoopholeByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}





	/**
	 * Huawei防火墙设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceFirewallHuawei(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(HuaweiFirewallNetworkInterfaceByV1V2.class.getPackage().getName()+".HuaweiFirewallNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * H3C防火墙设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceFirewallH3C(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(H3CFirewallNetworkInterfaceByV1V2.class.getPackage().getName()+".H3CFirewallNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * H3C防火墙设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceNsfocus(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(NsfocusNetworkInterfaceByV1V2.class.getPackage().getName()+".NsfocusNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}






	/**
	 * Windows网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceWindows(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(WindowsNetworkInterfaceByV1V2.class.getPackage().getName()+".WindowsNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}

	/**
	 * Linux网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceLinux(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("network_interface");
				resourceGatherItem.setType("5");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(LinuxNetworkInterfaceByV1V2.class.getPackage().getName()+".LinuxNetworkInterfaceByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}
	}





	/**
	 * 存储设备网络接口
	 * @param resource
	 */
	synchronized void saveNetworkInterfaceStorageEquipment(Resource resource,boolean init){

		ResourceGatherItem resourceGatherItem=null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("network_interface");
			resourceGatherItem.setType("5");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(StorageEquipmentNetworkInterfaceByV1V2.class.getPackage().getName()+".StorageEquipmentNetworkInterfaceByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	Map<String,NetworkInterface> initOldNetworkInterfaceMap(List<NetworkInterface> list){
		Map<String,NetworkInterface> map=new HashedMap();
		for(NetworkInterface networkInterface :list){
			map.put(networkInterface.getName(),networkInterface);
		}
		return map;
	}


	/**
	 * Windows安装软件列表
	 * @param resource
	 */
	synchronized void  saveSoftwareWindows(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		ResourceGatherItem resourceGatherItem=null;

				if(init) {
					resourceGatherItem =new ResourceGatherItem();
					resourceGatherItem.setDescription("software");
					resourceGatherItem.setType("7");
					resourceGatherItem.setResourceId(resource.getId());
					if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
						resourceGatherItem.setCollectionClass(WindowsSoftwareByV1V2.class.getPackage().getName()+".WindowsSoftwareByV1V2");
					}
					resourceGatherItemService.save(resourceGatherItem);
				}

	}




	/**
	 * Linux安装软件列表
	 * @param resource
	 */
	synchronized void  saveSoftwareLinux(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		ResourceGatherItem resourceGatherItem=null;

			if(init) {
				resourceGatherItem =new ResourceGatherItem();
				resourceGatherItem.setDescription("software");
				resourceGatherItem.setType("7");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(LinuxSoftwareByV1V2.class.getPackage().getName()+".LinuxSoftwareByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * Windows保存磁盘信息
	 * @param resource
	 */
	synchronized void saveDiskWindows(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("disk");
			resourceGatherItem.setType("4");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(WindowsDiskByV1V2.class.getPackage().getName()+".WindowsDiskByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * Linux保存磁盘信息
	 * @param resource
	 */
	synchronized void saveDiskLinux(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		ResourceGatherItem resourceGatherItem = null;

			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("disk");
				resourceGatherItem.setType("4");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(LinuxDiskByV1V2.class.getPackage().getName()+".LinuxDiskByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * 存储设备保存磁盘信息
	 * @param resource
	 */
	synchronized void saveDiskStorageEquipment(Resource resource,boolean init) {
		Long star=System.currentTimeMillis();
		ResourceGatherItem resourceGatherItem = null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("disk");
			resourceGatherItem.setType("4");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(StorageEquipmentDiskByV1V2.class.getPackage().getName()+".StorageEquipmentDiskByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}




	/**
	 * 保存路由器内存信息
	 * @param resource
	 */
	synchronized void saveMemoryRouter(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(GeneralRouterMemoryByV1V2.class.getPackage().getName()+".GeneralRouterMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * 保存思科路由器内存信息
	 * @param resource
	 */
	synchronized void saveMemoryRouterCisco(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("memory");
				resourceGatherItem.setType("8");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(CiscoRouterMemoryByV1V2.class.getPackage().getName()+".CiscoRouterMemoryByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}


	/**
	 * 保存华为路由器内存信息
	 * @param resource
	 */
	synchronized void saveMemoryRouterHuawei(Resource resource,boolean init) {

		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiRouterMemoryByV1V2.class.getPackage().getName()+".HuaweiRouterMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * 保存H3C路由器内存信息
	 * @param resource
	 */
	synchronized void saveMemoryRouterH3C(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CRouterMemoryByV1V2.class.getPackage().getName()+".H3CRouterMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}




	/**
	 * 思科交换机内存信息
	 * @param resource
	 */
	synchronized void saveMemoryExchangeCisco(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(CiscoExchangeMemoryByV1V2.class.getPackage().getName()+".CiscoExchangeMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * Huawei交换机内存信息
	 * @param resource
	 */
	synchronized void saveMemoryExchangeHuawei(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiExchangeMemoryByV1V2.class.getPackage().getName()+".HuaweiExchangeMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}


	/**
	 * H3C交换机内存信息
	 * @param resource
	 */
	synchronized void saveMemoryExchangeH3C(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CExchangeMemoryByV1V2.class.getPackage().getName()+".H3CExchangeMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * 锐捷交换机内存信息
	 * @param resource
	 */
	synchronized void saveMemoryExchangeRuijie(Resource resource,boolean init) {

		ResourceGatherItem resourceGatherItem = null;

		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());

			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(RuijieExchangeMemoryByV1V2.class.getPackage().getName()+".RuijieExchangeMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}



	/**
	 * 入侵检测设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryNGIDS(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("memory");
				resourceGatherItem.setType("8");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(SangforIpsMemoryByV1V2.class.getPackage().getName()+".SangforIpsMemoryByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * 防火墙设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryFirewall(Resource resource,boolean init) {

		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecFirewallMemoryByV1V2.class.getPackage().getName()+".TopsecFirewallMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}


	/**
	 * 入侵检测设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryIDS(Resource resource,boolean init) {

		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecIDSMemoryByV1V2.class.getPackage().getName()+".TopsecIDSMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}

	/**
	 * 漏洞扫描设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryNessus(Resource resource,boolean init) {

		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(TopsecNessusMemoryByV1V2.class.getPackage().getName()+".TopsecNessusMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}



	/**
	 * Huawei防火墙设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryFirewallHuawei(Resource resource,boolean init) {

		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(HuaweiFirewallMemoryByV1V2.class.getPackage().getName()+".HuaweiFirewallMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}

	/**
	 * Huawei防火墙设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryFirewallH3C(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(H3CFirewallMemoryByV1V2.class.getPackage().getName()+".H3CFirewallMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}



	/**
	 * 绿盟防火墙设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryNsfocus(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(NsfocusMemoryByV1V2.class.getPackage().getName()+".NsfocusMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}




	/**
	 * 保存Windows内存信息
	 * @param resource
	 */
	synchronized void saveMemoryWindows(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(WindowsMemoryByV1V2.class.getPackage().getName()+".WindowsMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}
	}



	/**
	 * 保存Linux内存信息
	 * @param resource
	 */
	synchronized void saveMemoryLinux(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
			if(init) {
				resourceGatherItem = new ResourceGatherItem();
				resourceGatherItem.setDescription("memory");
				resourceGatherItem.setType("8");
				resourceGatherItem.setResourceId(resource.getId());
				if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
					resourceGatherItem.setCollectionClass(LinuxMemoryByV1V2.class.getPackage().getName()+".LinuxMemoryByV1V2");
				}
				resourceGatherItemService.save(resourceGatherItem);
			}

	}



	/**
	 * 保存存储设备内存信息
	 * @param resource
	 */
	synchronized void saveMemoryStorageEquipment(Resource resource,boolean init) {
		ResourceGatherItem resourceGatherItem = null;
		if(init) {
			resourceGatherItem = new ResourceGatherItem();
			resourceGatherItem.setDescription("memory");
			resourceGatherItem.setType("8");
			resourceGatherItem.setResourceId(resource.getId());
			if(Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==1||Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())==2){
				resourceGatherItem.setCollectionClass(StorageEquipmentMemoryByV1V2.class.getPackage().getName()+".StorageEquipmentMemoryByV1V2");
			}
			resourceGatherItemService.save(resourceGatherItem);
		}

	}





	//查看资源详情
	@RequestMapping(value = "getInfo")
	public String getInfo(Resource resource ) {
		long start =System.currentTimeMillis();
		System.out.println("=====> "+(System.currentTimeMillis()-start));
		ResourceType resourceType =resourceTypeService.getParentByChild(resource.getResourceType().getId());
		if(resourceType==null||StringUtils.isEmpty(resourceType.getCode()) ){
			return "redirect:"+Global.getAdminPath()+"/resource/resource/list?repage";
		}
		if("1".equals(resourceType.getCode())) { //网络设备
			return "redirect:"+Global.getAdminPath()+"/resource/resource/getnetworkEquipmentDetail?id="+resource.getId();
		}
		if("2".equals(resourceType.getCode())) { //服务器
			System.out.println("=====> "+(System.currentTimeMillis()-start));
				return "redirect:"+Global.getAdminPath()+"/resource/resource/serverDetail?id="+resource.getId();
		}
		if("4".equals(resourceType.getCode())) { //数据库
			return "redirect:"+Global.getAdminPath()+"/resource/resource/dataBaseDetail?id="+resource.getId();
		}
		if("5".equals(resourceType.getCode())) { //中间件
			return "redirect:"+Global.getAdminPath()+"/resource/resource/middlewareDetail?id="+resource.getId();
		}
		if("6".equals(resourceType.getCode())) { //安全设备
			return "redirect:"+Global.getAdminPath()+"/resource/resource/safetyEquipmentdetail?id="+resource.getId();
		}
		if("7".equals(resourceType.getCode())) { //存储设备
			return "redirect:"+Global.getAdminPath()+"/resource/resource/storageEquipmentDetail?id="+resource.getId();
		}
		if("8".equals(resourceType.getCode())) { //web服务
			return "redirect:"+Global.getAdminPath()+"/resource/resource/applicationDetail?id="+resource.getId();
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resource/list?repage";
	}



    //服务器资源详情
	@RequestMapping(value = "serverDetail")
	public String serverDetail(Resource resource, Model model) {
		long start =System.currentTimeMillis();
		//List<Map<String, Object>> memoryList = MemoryIndicatorCalculateWindows.getVirtualPhysicalMemory(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));


		//System.out.println("+++++++000 "+(System.currentTimeMillis()-start));
		if(StringUtils.isNotBlank(resource.getOperatingSystemId())){
			model.addAttribute("operatingSystemId", operatingSystemService.get(resource.getOperatingSystemId()));
		}
		if(resource.getResourceType()!=null&&(resource.getResourceType().getCode().equals("21")||resource.getResourceType().getCode().equals("22"))) {  // 21：Windows服务器  22 Linux
			List<Disk> diskList  = diskService.findList(new Disk(resource.getId()));
			model.addAttribute("diskList", diskList);
			//System.out.println("+++++++111 "+(System.currentTimeMillis()-start));

			List<Memory> virtualPhysicalMemoryList = memoryService.findList(new Memory(resource.getId()));
			model.addAttribute("virtualPhysicalMemoryList", virtualPhysicalMemoryList);

			MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
			model.addAttribute("memoryUsedPercent", memoryUsedRate==null?"0.00":memoryUsedRate.getUsedRate());
			//System.out.println("+++++++222 "+(System.currentTimeMillis()-start));
			List<Process> processList=processService.findList(new Process(resource.getId()));
			model.addAttribute("processList", processList);

			List<Software> softwareList= softwareService.findList(new Software(resource.getId()));
			model.addAttribute("softwareList", softwareList);
			//System.out.println("+++++++333 "+(System.currentTimeMillis()-start));
		}

		String imgs=resource.getImg();
		if(StringUtils.isNotBlank(imgs)){
			String[] items=imgs.split(",");
			for(int i=0;i<items.length;i++){
				model.addAttribute("resourceImg"+i, Transformation.null2String(items[i]));
			}
		}else{
			model.addAttribute("resourceImg0", "");
			model.addAttribute("resourceImg1", "");
			model.addAttribute("resourceImg2", "");
			model.addAttribute("resourceImg3", "");
		}
		//System.out.println("+++++++444 "+(System.currentTimeMillis()-start));


		CpuUsedRate cpuUsedRate= cpuService.getTopCpuUsedRate(resource.getId());
		model.addAttribute("cpuUsedPercent",cpuUsedRate==null?"0.00":cpuUsedRate.getUsedRate());

		//可用率
		model.addAttribute("availabilityPercent",getAvailabilityRate(resource));
		//健康度
		model.addAttribute("healthPercent",getHealthDegree(resource));

		model.addAttribute("resourcePhysicInfo", resourcePhysicInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceConfigInfo", resourceConfigInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceInformation", resourceInformationService.getByResourceId(resource.getId()));

		model.addAttribute("resource", resource);
		//System.out.println("+++++++=====>"+(System.currentTimeMillis()-start));
		return "modules/resource/serverDetail";
	}



	/**
	 * 服务器承载应用
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "serverBearing")
	public Map<String,List<Resource>> serverBearing(Resource resource) {
		Map<String,List<Resource>> result=new HashedMap();
		if(resource==null){
			return result;
		}
		List<Resource> bearingApplication=new ArrayList<>();
		List<Resource> bearingServer=new ArrayList<>();
		Resource selectResource=new Resource();
		selectResource.setSelectResourceTypeCodeIds("'4','5','8'");
		List<Resource> list = resourceService.findList(selectResource);
		if(CheckObject.checkList(list)){
			for(Resource item:list){
				if(item.getIp().equals(resource.getIp())){
					if("4".equals(item.getResourceType().getParent().getCode())||"5".equals(item.getResourceType().getParent().getCode())){
						bearingApplication.add(item);
					}else if("8".equals(item.getResourceType().getParent().getCode())){
						bearingServer.add(item);
					}

				}
			}
			result.put("bearingApplication",bearingApplication);
			result.put("bearingServer",bearingServer);
		}
		return result;
	}










	//存储设备资源详情
	@RequestMapping(value = "storageEquipmentDetail")
	public String storageEquipmentdetail(Resource resource, Model model) {
		long start =System.currentTimeMillis();
		if(StringUtils.isNotBlank(resource.getOperatingSystemId())){
			model.addAttribute("operatingSystemId", operatingSystemService.get(resource.getOperatingSystemId()));
		}
		if(resource.getResourceType()!=null&&(resource.getResourceType().getCode().equals("71"))) {  // 71：存储设备
			List<Disk> diskList  = diskService.findList(new Disk(resource.getId()));
			model.addAttribute("diskList", diskList);

			List<Memory> virtualPhysicalMemoryList = memoryService.findList(new Memory(resource.getId()));
			model.addAttribute("virtualPhysicalMemoryList", virtualPhysicalMemoryList);

			MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
			model.addAttribute("memoryUsedPercent", memoryUsedRate==null?"0.00":memoryUsedRate.getUsedRate());
		}

		String imgs=resource.getImg();
		if(StringUtils.isNotBlank(imgs)){
			String[] items=imgs.split(",");
			for(int i=0;i<items.length;i++){
				model.addAttribute("resourceImg"+i, Transformation.null2String(items[i]));
			}
		}else{
			model.addAttribute("resourceImg0", "");
			model.addAttribute("resourceImg1", "");
			model.addAttribute("resourceImg2", "");
			model.addAttribute("resourceImg3", "");
		}



		CpuUsedRate cpuUsedRate= cpuService.getTopCpuUsedRate(resource.getId());
		model.addAttribute("cpuUsedPercent",cpuUsedRate==null?"0.00":cpuUsedRate.getUsedRate());

		//可用率
		model.addAttribute("availabilityPercent",getAvailabilityRate(resource));
		//健康度
		model.addAttribute("healthPercent",getHealthDegree(resource));

		model.addAttribute("resourcePhysicInfo", resourcePhysicInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceConfigInfo", resourceConfigInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceInformation", resourceInformationService.getByResourceId(resource.getId()));

		model.addAttribute("resource", resource);
		return "modules/resource/storageEquipmentDetail";
	}







	//安全设备资源详情
	@RequestMapping(value = "safetyEquipmentdetail")
	public String safetyEquipmentdetail(Resource resource, Model model) {
		if(StringUtils.isNotBlank(resource.getOperatingSystemId())){
			model.addAttribute("operatingSystemId", operatingSystemService.get(resource.getOperatingSystemId()));
		}


			MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
			model.addAttribute("memoryUsedPercent", memoryUsedRate==null?"0.00":memoryUsedRate.getUsedRate());


		String imgs=resource.getImg();
		if(StringUtils.isNotBlank(imgs)){
			String[] items=imgs.split(",");
			for(int i=0;i<items.length;i++){
				model.addAttribute("resourceImg"+i, Transformation.null2String(items[i]));
			}
		}else{
			model.addAttribute("resourceImg0", "");
			model.addAttribute("resourceImg1", "");
			model.addAttribute("resourceImg2", "");
			model.addAttribute("resourceImg3", "");
		}


		//List<NetworkInterface> networkInterfaceList=networkInterfaceService.findListBySort(resource.getId());
		//model.addAttribute("networkInterfaceList", networkInterfaceList);

		CpuUsedRate cpuUsedRate= cpuService.getTopCpuUsedRate(resource.getId());
		model.addAttribute("cpuUsedPercent",cpuUsedRate==null?"0.00":cpuUsedRate.getUsedRate());

		//可用率
		model.addAttribute("availabilityPercent",getAvailabilityRate(resource));
		//健康度
		model.addAttribute("healthPercent",getHealthDegree(resource));


		model.addAttribute("resourcePhysicInfo", resourcePhysicInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceConfigInfo", resourceConfigInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceInformation", resourceInformationService.getByResourceId(resource.getId()));

		model.addAttribute("resource", resource);
		return "modules/resource/safetyEquipmentDetail";
	}



	//链路资源详情
	@RequestMapping(value = "linkDetail")
	public String linkDetail(Resource resource, Model model) {
		LinkIndicator linkIndicator =linkIndicatorService.getByResource(resource.getId());
		if(linkIndicator!=null){
			linkIndicator.setStatus(linkIndicator.getStatus()==null?"-":linkIndicator.getStatus());
			linkIndicator.setUpRate(linkIndicator.getUpRate()==null?"-":linkIndicator.getUpRate());
			linkIndicator.setDownRate(linkIndicator.getDownRate()==null?"-":linkIndicator.getDownRate());
			linkIndicator.setUpUsedRate(linkIndicator.getUpUsedRate()==null?"-":(linkIndicator.getUpUsedRate()+"%"));
			linkIndicator.setDownUsedRate(linkIndicator.getDownUsedRate()==null?"-":(linkIndicator.getDownUsedRate()+"%"));
			linkIndicator.setHealthDegree(linkIndicator.getHealthDegree()==null?"-":(linkIndicator.getHealthDegree()+"%"));
			linkIndicator.setAvailability(linkIndicator.getAvailability()==null?"-":(linkIndicator.getAvailability()+"%"));
		}
		model.addAttribute("linkIndicator",linkIndicator);
		model.addAttribute("resource", resource);
		return "modules/resource/linkDetail";
	}



	@ResponseBody
	@RequestMapping(value = "initIndicatorList")
	public List<ResourceIndicatorlist> getNetInterfaceRateChart(String resourceId) {
		List<ResourceIndicatorlist> resourceIndicatorlistList = resourceIndicatorlistService.findListByResourceId(resourceId);
		for(ResourceIndicatorlist resourceIndicatorlist : resourceIndicatorlistList){
			resourceIndicatorlist.getIndicator().setEventType(DictUtils.getDictLabel(resourceIndicatorlist.getIndicator().getEventType(),"indicator_event_type",""));
		}
		return resourceIndicatorlistList;
	}


	//网络设备资源详情
	@RequestMapping(value = "getnetworkEquipmentDetail")
	public String networkEquipmentDetail(HttpServletRequest request,Resource resource, Model model) {
		long start =System.currentTimeMillis();
		//System.out.println("======000 "+(System.currentTimeMillis()-start));

		if(StringUtils.isNotBlank(resource.getOperatingSystemId())){
			model.addAttribute("operatingSystemId", operatingSystemService.get(resource.getOperatingSystemId()));
		}
		String imgs=resource.getImg();
		if(StringUtils.isNotBlank(imgs)){
			String[] items=imgs.split(",");
			for(int i=0;i<items.length;i++){
				model.addAttribute("resourceImg"+i, Transformation.null2String(items[i]));
			}
		}else{
			model.addAttribute("resourceImg0", "");
			model.addAttribute("resourceImg1", "");
			model.addAttribute("resourceImg2", "");
			model.addAttribute("resourceImg3", "");
		}

		//System.out.println("======111 "+(System.currentTimeMillis()-start));

		//model.addAttribute("indexTemplateList", indexTemplateService.list(resource.getResourceType().getId()));

		//List<NetworkInterface> networkInterfaceList=networkInterfaceService.findListBySort(resource.getId());
		//model.addAttribute("networkInterfaceList", networkInterfaceList);
		MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
		model.addAttribute("memoryUsedPercent", memoryUsedRate==null?"0.00":memoryUsedRate.getUsedRate());
		CpuUsedRate cpuUsedRate= cpuService.getTopCpuUsedRate(resource.getId());
		model.addAttribute("cpuUsedPercent",cpuUsedRate==null?"0.00":cpuUsedRate.getUsedRate());
		//可用率
		model.addAttribute("availabilityPercent",getAvailabilityRate(resource));
		//健康度
		model.addAttribute("healthPercent",getHealthDegree(resource));
		//System.out.println("======222 "+(System.currentTimeMillis()-start));
		model.addAttribute("resourcePhysicInfo", resourcePhysicInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceConfigInfo", resourceConfigInfoService.getByResourceId(resource.getId()));
		model.addAttribute("resourceInformation", resourceInformationService.getByResourceId(resource.getId()));

		model.addAttribute("backplaneView", getBackplane(resource.getSysOid(),request.getContextPath()+"/static"));
		model.addAttribute("resource", resource);
		//System.out.println("======333 "+(System.currentTimeMillis()-start));
		return "modules/resource/networkEquipmentDetail";
	}

	@ResponseBody
	@RequestMapping(value = "getNetworkInterfaceList")
	public List<NetworkInterface> getNetworkInterfaceList(String resourceId) {
		return networkInterfaceService.findListBySort(resourceId);
	}




	/**
	 * 获取网络设备链接服务
	 * @param resourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getnetworkEquipmentLink")
	public List<Map<String,Object>> networkEquipmentDetail(String resourceId) {
		List<Map<String,Object>> result=new ArrayList<>();
		if(StringUtils.isEmpty(resourceId)){
			return result;
		}

		Map<String ,String> map=new HashedMap();

		List<LinkIndicator>  linkIndicators= linkIndicatorService.getListByLinkEquequipment(resourceId);
		if(CheckObject.checkList(linkIndicators)) {
			for (LinkIndicator item : linkIndicators) {
				Resource resource=null;
				String networkInterfaceName=null;
				if (item.getUpLinkEquequipment().equals(resourceId)) {
					 resource=resourceService.get(item.getDownLinkEquequipment());
					 networkInterfaceName=item.getUpInterface().getName();
				} else {
					resource=resourceService.get(item.getUpLinkEquequipment());
					networkInterfaceName=item.getDownInterface().getName();
				}
				if(resource!=null&&!map.containsKey(resource.getId())){
					Map<String,Object> resultMap=new HashedMap();
					resultMap.put("networkInterfaceName",networkInterfaceName);
					resultMap.put("resource",resource);
					map.put(resource.getId(),resource.getId());
					result.add(resultMap);
				}

				/*if(resource!=null&&"2".equals(resource.getResourceType().getParent().getCode())){
					result.add(resource);
				}*/
			}
		}
		return result;
	}

	//获取背板视图
	@ResponseBody
	@RequestMapping(value = "getBackplane")
	String getBackplane(String  sysOid,String ctxStatic){
		if(StringUtils.isEmpty(sysOid)||StringUtils.isEmpty(ctxStatic)){
			return "";
		}
		String path = Global.class.getResource("/backplanePath.json").getPath();
		String JsonContext = Global.ReadFile(path);
		JSONArray json=new JSONArray(JsonContext);
		for(int  i = 0; i < json.length(); i++) {
			 JSONObject item = json.getJSONObject(i);
			 if(String.valueOf(item.get("sysOid")).equals(sysOid)){
				 path=Global.class.getResource(String.valueOf(item.get("path"))).getPath();
				 String view=Global.ReadFile(path);
				 view= view.replaceAll("ctxStatic",ctxStatic);
				 return view;
			 }
		}
		return "";
	}

	//数据库资源详情
	@RequestMapping(value = "dataBaseDetail")
	public String dataBaseDetail(Resource resource, Model model) {

		//可用率
		model.addAttribute("availabilityPercent",getAvailabilityRate(resource));
		//健康度
		model.addAttribute("healthPercent",getHealthDegree(resource));

		model.addAttribute("resource", resource);
		return "modules/resource/databaseDetail";
	}

	//中间件资源详情
	@RequestMapping(value = "middlewareDetail")
	public String middlewareDetail(Resource resource, Model model) {
		//可用率
		model.addAttribute("availabilityPercent",getAvailabilityRate(resource));
		//健康度
		model.addAttribute("healthPercent",getHealthDegree(resource));

		model.addAttribute("resource", resource);
		return "modules/resource/middlewareDetail";
	}

	//应用资源详情
	@RequestMapping(value = "applicationDetail")
	public String applicationDetail(Resource resource, Model model) {
		if(StringUtils.isNotBlank(resource.getDatabaseIp())){
			model.addAttribute("database",resourceService.getByIpType(resource.getDatabaseIp(),"4"));
		}
		if(StringUtils.isNotBlank(resource.getMiddlewareIp())){
			model.addAttribute("middleware",resourceService.getByIpType(resource.getMiddlewareIp(),"5"));
		}

		Resource serverResource=resourceService.getByIpType(resource.getIp(),"2");
		model.addAttribute("server",serverResource);

        if(serverResource!=null) {
			MemoryUsedRate memoryUsedRate = memoryService.getTopMemoryUsedRate(serverResource.getId());
			model.addAttribute("memoryUsedPercent", memoryUsedRate == null ? "0.00" : memoryUsedRate.getUsedRate());
			CpuUsedRate cpuUsedRate = cpuService.getTopCpuUsedRate(serverResource.getId());
			model.addAttribute("cpuUsedPercent", cpuUsedRate == null ? "0.00" : cpuUsedRate.getUsedRate());
		}

		model.addAttribute("applicationIndicatorList", applicationIndicatorService.findListByResource(resource.getId()));
		model.addAttribute("resource", resource);
		return "modules/resource/applicationDetail";
	}



	@ResponseBody
	@RequestMapping(value = "onlineNetInterface")
	public List<NetworkInterface> rr(String resourceId) {
		List<NetworkInterface> result=networkInterfaceService.onlineNetInterface(resourceId);
		return result;
	}


	@ResponseBody
	@RequestMapping(value = "getNetInterfaceRateChart")
	public Map<String, Object> getNetInterfaceRateChart(String netInterfaceId,String type) {
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0

		List<NetInterfaceInOutRate> list = networkInterfaceService.findRateList(netInterfaceId,type);
		Map<String, Object> data = new HashMap<String, Object>();

		if(CheckObject.checkList(list)){
			int countTimes=list.size();
			String[] time = new String[countTimes];
			String[] rate = new String[countTimes];

			for (int i = 0; i < countTimes; i++) {
				time[i] = getDateString(list.get(list.size() - countTimes + i).getCreateDate());
				rate[i] = df.format(Transformation.null2Double(list.get(list.size() - countTimes + i).getRate())/1024);

			}
			data.put("time",time);
			data.put("rate", rate);

		}else {
			data.put("time", new String[0]);
			data.put("rate", new String[0]);
		}
		return data;
	}


	@ResponseBody
	@RequestMapping(value = "realTimeAnalysis")
	public Map<String, Object> realTimeAnalysis(String resourceId,String type) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<CpuUsedRate> cpuUsedRateList=null;
		List<MemoryUsedRate> memoryUsedRateList=null;
		List<ResponseTime> responseTimeList=null;
		int countTimes;
		if("1".equals(type)){
			 cpuUsedRateList = cpuService.findUsedRateList(resourceId);
			if(CheckObject.checkList(cpuUsedRateList)){
				countTimes=cpuUsedRateList.size();
				String[] time = new String[countTimes];
				String[] percent = new String[countTimes];
				for (int i = 0; i < countTimes; i++) {
					time[i] = getDateString(cpuUsedRateList.get(cpuUsedRateList.size() - countTimes + i).getCreateDate());
					percent[i] = cpuUsedRateList.get(cpuUsedRateList.size() - countTimes + i).getUsedRate();
				}
				data.put("time",time);
				data.put("percent", percent);
				data.put("type",type);
			}else {
				data.put("time", new String[0]);
				data.put("percent", new String[0]);
				data.put("type",type);
			}
		}else if("2".equals(type)){
			 memoryUsedRateList = memoryService.findMemoryUsedRateList(resourceId);
			if(CheckObject.checkList(memoryUsedRateList)){
				 countTimes=memoryUsedRateList.size();
				String[] time = new String[countTimes];
				String[] percent = new String[countTimes];
				for (int i = 0; i < countTimes; i++) {
					time[i] = getDateString(memoryUsedRateList.get(memoryUsedRateList.size() - countTimes + i).getCreateDate());
					percent[i] = memoryUsedRateList.get(memoryUsedRateList.size() - countTimes + i).getUsedRate();
				}
				data.put("time",time);
				data.put("percent", percent);
				data.put("type",type);
			}else {
				data.put("time", new String[0]);
				data.put("percent", new String[0]);
				data.put("type",type);
			}
		}else if("9".equals(type)||"41003".equals(type)||"42003".equals(type)||"51004".equals(type)||"52008".equals(type)){
			responseTimeList = resourceService.findResponseTimeList(resourceId);
			if(CheckObject.checkList(responseTimeList)){
				countTimes=responseTimeList.size();
				String[] time = new String[countTimes];
				String[] percent = new String[countTimes];
				for (int i = 0; i < countTimes; i++) {
					time[i] = getDateString(responseTimeList.get(responseTimeList.size() - countTimes + i).getCreateDate());
					percent[i] = responseTimeList.get(responseTimeList.size() - countTimes + i).getTime();
				}
				data.put("time",time);
				data.put("percent", percent);
				data.put("type",type);
			}else {
				data.put("time", new String[0]);
				data.put("percent", new String[0]);
				data.put("type",type);
			}
		}else{
			data.put("time", new String[0]);
			data.put("percent", new String[0]);
			data.put("type",type);
			return data;
		}
		return data;
	}



	@ResponseBody
	@RequestMapping(value = "getCpuInfo")
	public Map<String, Object> getCpuInfo(Resource resource) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Cpu> cpuList= cpuService.findList(new Cpu(resource.getId()));
		if (CheckObject.checkList(cpuList)) {
			int countTimes=cpuList.size();
			String[] cpuIndex = new String[countTimes];
			String[] cpuValue = new String[countTimes];
			int[] cpuNum = new int[1];
			cpuNum[0]=cpuList.size();
			for (int i = 0; i < countTimes; i++) {
				cpuIndex[i] = cpuList.get(cpuList.size() - countTimes + i).getName();
				cpuValue[i] = cpuList.get(cpuList.size() - countTimes + i).getValue();
			}
			data.put("cpuIndex", cpuIndex);
			data.put("cpuValue", cpuValue);
			data.put("cpuNum", cpuNum);
		} else {
			int[] cpuNum = new int[1];
			cpuNum[0]=0;
			data.put("cpuIndex", new String[0]);
			data.put("cpuValue",  new String[0]);
			data.put("cpuNum", cpuNum);
		}
		return data;
	}




	@ResponseBody
	@RequestMapping(value = "getChart")
	public Map<String, Object> getChart(String resourceId) {
		List<CpuUsedRate> cpuUsedRateList = cpuService.findUsedRateList(resourceId);
		List<MemoryUsedRate> memoryUsedRateList = memoryService.findMemoryUsedRateList(resourceId);
		List<AvailabilityRate> availabilityRateList = resourceService.findAvailabilityRateList(resourceId);
		List<HealthDegree> healthDegreeList = resourceService.findHealthDegreeList(resourceId);
		Map<String, Object> data = new HashMap<String, Object>();

		if(CheckObject.checkList(cpuUsedRateList)&&CheckObject.checkList(memoryUsedRateList)&&CheckObject.checkList(availabilityRateList)&&CheckObject.checkList(healthDegreeList)){
			int countTimes=cpuUsedRateList.size()>memoryUsedRateList.size()?memoryUsedRateList.size():cpuUsedRateList.size();
			countTimes=availabilityRateList.size()>countTimes?countTimes:availabilityRateList.size();
			countTimes=healthDegreeList.size()>countTimes?countTimes:healthDegreeList.size();
			String[] time = new String[countTimes];
			String[] cpuUsedRate = new String[countTimes];
			String[] memoryUsedRate = new String[countTimes];
			String[] availabilityRate = new String[countTimes];
			String[] healthyRate = new String[countTimes];
			int index=1;
			for (int i = 0; i <countTimes; i++) {
				time[i] = getDateString(memoryUsedRateList.get(countTimes-index).getCreateDate());
				cpuUsedRate[i] = cpuUsedRateList.get(countTimes-index).getUsedRate();
				memoryUsedRate[i] = memoryUsedRateList.get(countTimes-index).getUsedRate();
				availabilityRate[i] = availabilityRateList.get(countTimes-index).getAvailabilityRate();
				healthyRate[i] =healthDegreeList.get(countTimes-index).getHealthDegree();
				index++;
			}
			data.put("time",time);
			data.put("cpuUsedRate", cpuUsedRate);
			data.put("memoryUsedRate", memoryUsedRate);
			data.put("availabilityRate", availabilityRate);
			data.put("healthyRate", healthyRate);

		}else {
			data.put("time", new String[0]);
			data.put("cpuUsedRate", new String[0]);
			data.put("memoryUsedRate", new String[0]);
			data.put("availabilityRate",new String[0]);
			data.put("healthyRate",new String[0]);
		}
		return data;
	}




	@ResponseBody
	@RequestMapping(value = "getMiddlewareChart")
	public Map<String, Object> getMiddlewareChart(String resourceId) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<AvailabilityRate> availabilityRateList = resourceService.findAvailabilityRateList(resourceId);
		List<HealthDegree> healthDegreeList = resourceService.findHealthDegreeList(resourceId);

		if(CheckObject.checkList(availabilityRateList)&&CheckObject.checkList(healthDegreeList)){
			int countTimes=availabilityRateList.size()>healthDegreeList.size()?healthDegreeList.size():availabilityRateList.size();
			String[] time = new String[countTimes];
			String[] availabilityRate = new String[countTimes];
			String[] healthyRate = new String[countTimes];

			int index=1;

			for (int i = 0; i < countTimes; i++) {
				time[i] = getDateString(availabilityRateList.get(countTimes-index).getCreateDate());
				availabilityRate[i] = availabilityRateList.get(countTimes-index).getAvailabilityRate();
				healthyRate[i] =healthDegreeList.get(countTimes-index).getHealthDegree();
				index++;
			}
			data.put("time",time);
			data.put("availabilityRate", availabilityRate);
			data.put("healthyRate", healthyRate);

		}else {
			data.put("time", new String[0]);
			data.put("availabilityRate",new String[0]);
			data.put("healthyRate",new String[0]);
		}
		return data;
	}



	public String  getDateString(Date date) {
		DateFormat df= DateFormat.getDateTimeInstance();
		String result = df.format(date);
		String[] results=result.replaceAll("-","/").split(" ");
	    String[] times = results[1].split(":");
		result= results[0]+" "+times[0]+":"+times[1];
		//result=times[0]+":"+times[1];
		return  result;
	}



	@ResponseBody
	@RequestMapping(value = "findListByResource")
	public List<Map<String,String>> findListByResource(String resourceId,String exceptionClass) {
		List<ResourceException> list=null;
		List<Map<String,String>> result=new ArrayList<>();
		if(StringUtils.isNotBlank(resourceId)) {
			list = resourceExceptionService.findListByResource(resourceId,exceptionClass);
		}
		if(CheckObject.checkList(list)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(ResourceException resourceException:list){
					Map<String,String> map=new HashedMap();
					map.put("id",resourceException.getId());
					map.put("indicatorName",resourceException.getIndicatorName());
					map.put("exceptionSource",resourceException.getExceptionSource());
					map.put("exceptionClass", DictUtils.getDictLabels(resourceException.getExceptionClass(),"exception_class", "-"));

					map.put("firstTriggerTime",sdf.format(resourceException.getFirstTriggerTime()));
					map.put("currentStatus",DictUtils.getDictLabels(resourceException.getCurrentStatus(),"exception_current_status", "-"));

				    map.put("currentStatusDict", resourceException.getCurrentStatus());
				    map.put("confirmStatusDict", resourceException.getConfirmStatus());

					map.put("duration",getDuration(resourceException.getCreateDate()));
					map.put("currentValue",resourceException.getCurrentValue());
				    map.put("unit",resourceException.getIndicatorItem().getUnit());
					if(resourceException.getConfirmUser()!=null){
						map.put("confirmUserName",resourceException.getConfirmUser().getName());
					}else{
						map.put("confirmUserName","-");
					}
				    map.put("area","-");
					if(resourceException.getOffice()!=null) {
						Area area = areaService.getByOffice(resourceException.getOffice().getId());
						if (area != null) {
							map.put("area", area.getName());
						}
					}
				    map.put("searchExceptionClass",exceptionClass);

				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 手动恢复
	 */
	@ResponseBody
	@RequestMapping(value = "manualRecovery")
	public boolean manualRecovery(String resourceExceptionId) {
		if(StringUtils.isEmpty(resourceExceptionId)) {
			return false;
		}
		ResourceException resourceException=resourceExceptionService.get(resourceExceptionId);
		if(resourceException==null){
			return false;
		}
		if(resourceException!=null&&"0".equals(resourceException.getCurrentStatus())){ //未恢复
			resourceExceptionService.manualRecovery(resourceException.getId(),new Date(),"1","1", UserUtils.getUser().getId());
			return true;
		}
		return false;
	}


	@ResponseBody
	@RequestMapping(value = "confirmException")
	public boolean confirmException(String resourceExceptionId) {
		if(StringUtils.isEmpty(resourceExceptionId)) {
			return false;
		}
		ResourceException resourceException=resourceExceptionService.get(resourceExceptionId);
		if(resourceException==null){
			return false;
		}
		if(resourceException!=null&&"0".equals(resourceException.getConfirmStatus())){//未确认
			resourceExceptionService.confirmException(resourceException.getId(),new Date(),"1", UserUtils.getUser().getId());
			return true;
		}
		return false;
	}


	@ResponseBody
	@RequestMapping(value = "delException")
	public boolean delException(String resourceExceptionId) {
		if(StringUtils.isEmpty(resourceExceptionId)) {
			return false;
		}
		resourceExceptionService.delById(resourceExceptionId);
		return true;
	}





	//计算持续时间
	String getDuration(Date date){
		String result="";
		Date now=new Date();
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = now.getTime() - date.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		if(day>0){
			result+=day + "天";
		}
		if(hour>0){
			result+=hour + "小时";
		}
		if(min>0){
			result+=min + "分钟";
		}

		if(result.length()==0){
			result+="1分钟";
		}

		return  result;
	}

	/**
	 * 删除resource
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Resource resource, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceService.delete(resource);
		j.setMsg("删除资源成功");
		return j;
	}
	
	/**
	 * 批量删除resource
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceService.delete(resourceService.get(id));
		}
		j.setMsg("删除资源成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resource:resource:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Resource resource, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "resource"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Resource> page = resourceService.findPage(new Page<Resource>(request, response, -1), resource);
    		new ExportExcel("resource", Resource.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resource:resource:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Resource> list = ei.getDataList(Resource.class);
			for (Resource resource : list){
				try{
					resourceService.save(resource);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resource/list?repage";
    }
	
	/**
	 * 下载导入resource数据模板
	 */
	@RequiresPermissions("resource:resource:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源数据导入模板.xlsx";
    		List<Resource> list = Lists.newArrayList(); 
    		new ExportExcel("资源数据", Resource.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resource/list?repage";
    }


/*
    void updateResourceIndicator(){
		System.out.println("方法开始=====================");
		Long star=System.currentTimeMillis();
		ResourceType resourceType=new ResourceType();
		Resource initResource=new Resource();
		initResource.setResourceType(resourceType);
		List<Resource> resourceList= resourceService.findList(initResource);
		if(CheckObject.checkList(resourceList)){
			for(Resource resource:resourceList) {
				*//*if("9d0447ddf2574dc5bf8bc1b657bd0366".equals(resource.getId())){
					continue;
				}*//*
				ResourceType parentResourceType =resourceTypeService.getParentByChild(resource.getResourceType().getId());

				//网络设备或服务器
				if("1".equals(parentResourceType.getCode())||"2".equals(parentResourceType.getCode())){
				List<ResourceGatherItem> list = resourceGatherItemService.findListByResource(resource.getId());
					if(CheckObject.checkList(list)) {
						for(ResourceGatherItem resourceGatherItem:list) {
							//CPU
							if ("3".equals(resourceGatherItem.getType())) {
								if (resource.getResourceType().getCode().equals("11")) {  //11：路由器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveCpuRouter(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("12")) {  //12：交换机
									if("2".equals(resource.getManufacturer().getCode())) {       //思科
										new Thread(new Runnable() {
											@Override
											public void run() {
												saveCpuExchangeCisco(resource, false);
											}
										}).start();
									}
								}else if (resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveCpuWindows(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("22")) {    // 22:Linux服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveCpuLinux(resource, false);
										}
									}).start();
								}

							//磁盘
							} else if ("4".equals(resourceGatherItem.getType())) {
								if (resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveDiskWindows(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("22")) {    // 22:Linux服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveDiskLinux(resource, false);
										}
									}).start();
								}


							//网络接口
							} else if ("5".equals(resourceGatherItem.getType())) {
								if (resource.getResourceType().getCode().equals("11")) {  //11：路由器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveNetworkInterfaceRouter(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("12")) {  //12：交换机
									if("2".equals(resource.getManufacturer().getCode())) {       //思科
										new Thread(new Runnable() {
											@Override
											public void run() {
												saveNetworkInterfaceExchangeCisco(resource, false);
											}
										}).start();
									}
								}else if (resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveNetworkInterfaceWindows(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("22")) {    // 22:Linux服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveNetworkInterfaceLinux(resource, false);
										}
									}).start();
								}

							//进程
							} else if ("6".equals(resourceGatherItem.getType())) {

								if (resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveProcessInfoWindows(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("22")) {    // 22:Linux服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveProcessInfoLinux(resource, false);
										}
									}).start();
								}

							//软件
							} else if ("7".equals(resourceGatherItem.getType())) {

								if (resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveSoftwareWindows(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("22")) {    // 22:Linux服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveSoftwareLinux(resource, false);
										}
									}).start();
								}

							//内存
							} else if ("8".equals(resourceGatherItem.getType())) {
								if (resource.getResourceType().getCode().equals("11")) {  //11：路由器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveMemoryRouter(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("12")) {  //12交换机
									if("2".equals(resource.getManufacturer().getCode())) {       //思科
										new Thread(new Runnable() {
											@Override
											public void run() {
												saveMemoryExchangeCisco(resource, false);
											}
										}).start();
									}
								}else if (resource.getResourceType().getCode().equals("21")) {  //21：Windows服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveMemoryWindows(resource, false);
										}
									}).start();
								} else if (resource.getResourceType().getCode().equals("22")) {    // 22:Linux服务器
									new Thread(new Runnable() {
										@Override
										public void run() {
											saveMemoryLinux(resource, false);
										}
									}).start();
								}

							//ICMPTime响应时间
							} else if ("9".equals(resourceGatherItem.getType())) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										saveICMPTime(resource, false);
									}
								}).start();
							}
						}
					 }
				}
				else  if("3".equals(parentResourceType.getCode())){ //链路
					   if("31".equals(resource.getResourceType().getCode())){
						   setLinkRate(resource,null);
					   }
				}
				else  if("4".equals(parentResourceType.getCode())){ //数据库
					new Thread(new Runnable() {
						@Override
						public void run() {
							saveDatabase(resource);
						}
					}).start();
				}else  if("5".equals(parentResourceType.getCode())){  //中间件
					new Thread(new Runnable() {
						@Override
						public void run() {
							saveMiddleware(resource);
						}
					}).start();
				}
			}
		}
		System.out.println("方法结束=====================："+(System.currentTimeMillis()-star));
	}*/


	/**
	 * 保存数据库指标信息
	 * @param resource
	 */
	synchronized void saveDatabase(Resource resource) {
         Date date=new Date();
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"4");
		if(CheckObject.checkList(updateList)){
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				String value="";
				if("41".equals(resource.getResourceType().getCode())){ //mySql
					if("41001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //mySql数据库连接数
						value= ConnectionsCalculateMysql.getMysqlConnections(resource);
					}else if("41002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //mySql数据库连续工作时长
						value= UpTimeCalculateMysql.getMysqlUptime(resource);
					}else if("41003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //mySql数据库连接响应时间
                        value= ResponseTimeCalculateMysql.getMysqlResponseTime(resource);
						ResponseTime responseTime = new ResponseTime();
						responseTime.setCreateDate(date);
						responseTime.setResourceId(resource.getId());
						responseTime.setTime(value);
						resourceService.saveMiddlewareResponseTime(responseTime);
					}
				}else if("42".equals(resource.getResourceType().getCode())){   //sqlserver
					if("42001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sqlServer数据库连接数
						value= ConnectionsCalculateSqlserver.getSqlserverConnections(resource);
					}else if("42002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sqlServer数据文件大小
						value= DataSizeCalculateSqlserver.getSqlserverDataSize(resource);
					}else if("42003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sqlServer数据库连接响应时间
						value= ResponseTimeCalculateSqlserver.getSqlserverResponseTime(resource);
						ResponseTime responseTime = new ResponseTime();
						responseTime.setCreateDate(date);
						responseTime.setResourceId(resource.getId());
						responseTime.setTime(value);
						resourceService.saveMiddlewareResponseTime(responseTime);
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
					resourceIndicatorlistService.setValue(value,resourceIndicatorlist.getId());
				}
			}
		}
	}


	/**
	 * 保存中间件指标信息
	 * @param resource
	 */
	synchronized void saveMiddleware(Resource resource) {
		Date date=new Date();
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
		List<ResourceIndicatorlist> updateList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"5");
		if(CheckObject.checkList(updateList)){
			for(ResourceIndicatorlist resourceIndicatorlist : updateList){
				String value="";
			    if("51".equals(resource.getResourceType().getCode())){   //tomcat
					GeneralTomcatIndicator generalTomcatIndicator=new GeneralTomcatIndicator(resource);
					TomcatInfo tomcatInfo=generalTomcatIndicator.getTomcatInfo();
					if(tomcatInfo!=null) {
						double mem=0.00;
						if("51001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //tomcat内存总量
							if(tomcatInfo.getTotalMemory()>0){
								mem=tomcatInfo.getTotalMemory()/1024/1024.00;
								value = df.format(mem);
							}
						}else if("51002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //tomcat内存剩余量
							if(tomcatInfo.getFreeMemory()>0){
								mem=tomcatInfo.getFreeMemory()/1024/1024.00;
								value = df.format(mem);
							}
						}else if("51003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //tomcat内存已用量
							if(tomcatInfo.getTotalMemory()>0&&tomcatInfo.getFreeMemory()>0){
								mem=(tomcatInfo.getTotalMemory()-tomcatInfo.getFreeMemory())/1024/1024.00;
								value = df.format(mem);
							}
						}else if("51004".equals(resourceIndicatorlist.getIndicator().getItemType())){  //Tomcat连接响应时间
								value = String.valueOf(tomcatInfo.getResponseTime());
								ResponseTime responseTime = new ResponseTime();
								responseTime.setCreateDate(date);
								responseTime.setResourceId(resource.getId());
								responseTime.setTime(value);
								resourceService.saveMiddlewareResponseTime(responseTime);
						}
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
					resourceIndicatorlistService.setValue(value,resourceIndicatorlist.getId());
				}
			}
		}
	}





	@ResponseBody
	@RequestMapping(value = "topoResource")
	public AjaxJson topoResource() {
		AjaxJson ajaxJson=new AjaxJson();
		ajaxJson.setSuccess(false);
		List<ResourceType> resourceTypeList =resourceTypeService.getAllChilds();
		List<Map<String,Object>> result=new ArrayList<>();
		if(CheckObject.checkList(resourceTypeList)) {
			for (ResourceType resourceType : resourceTypeList) {
				List<Resource> list=resourceService.getListByCode(resourceType.getCode());
				if(CheckObject.checkList(list)){
					Map<String,Object> item=new HashedMap();
					List<Map<String,String>> child=new ArrayList<>();
                    //过滤掉网络链路
					if(!resourceType.getCode().equals("31")){
						for(Resource resource:list){
							Map<String,String> map=new HashedMap();
							map.put("id",resource.getId());
							map.put("title",resource.getName());
							map.put("name",resource.getName());
							map.put("object_class",resourceType.getCode());
							map.put("iconSkin","node n3003");
							child.add(map);
						}
						item.put("id",resourceType.getCode());
						item.put("title",resourceType.getName());
						item.put("name",resourceType.getName());
						item.put("isParent",true);
						item.put("children",child);
						result.add(item);
					}
				}
			}
			ajaxJson.setSuccess(true);
			ajaxJson.put("resourceNodes",result);
		}
		return ajaxJson;
	}





	@ResponseBody
	@RequestMapping(value = "linkSelect",method = RequestMethod.POST)
	public Map<String,String> treeDataMiddlewareForm(String code) {
		Long start=System.currentTimeMillis();
		if(StringUtils.isEmpty(code)){
			code="1";
		}
		Map<String,String> resultMap=new HashedMap();


		List<NetworkInterface> list=networkInterfaceService.list();
		System.out.println("link 1 -> "+(System.currentTimeMillis()-start));
		Map<String,List<NetworkInterface>> map2=new HashedMap();
		for(NetworkInterface networkInterface:list){
			if(map2.get(networkInterface.getResourceId())==null){
				List<NetworkInterface> list2=new ArrayList<>();
				list2.add(networkInterface);
				map2.put(networkInterface.getResourceId(),list2);
			}else{
				map2.get(networkInterface.getResourceId()).add(networkInterface);
			}
		}

		System.out.println("link 2 -> "+(System.currentTimeMillis()-start));
       Map<String,List<Map<String,Object>>> result=new HashedMap();
         for (Map.Entry<String,List<NetworkInterface>> entry : map2.entrySet()) {
                Resource resource=resourceService.get(entry.getKey());
                if(resource==null){
                    continue;
                }
                if(result.get(resource.getResourceType().getParent().getCode())!=null){
                    Map<String, Object> map=new HashedMap();
                    map.put("list",entry.getValue());
                    map.put("name",resource.getName());
                    map.put("id",resource.getId());
                    result.get(resource.getResourceType().getParent().getCode()).add(map);
                }else{
                    List<Map<String,Object>> ll=new ArrayList<>();
                    Map<String, Object> map=new HashedMap();
                    map.put("list",entry.getValue());
                    map.put("name",resource.getName());
                    map.put("id",resource.getId());
                    ll.add(map);
                    result.put(resource.getResourceType().getParent().getCode(),ll);
                }
             }
		int i=0;

		System.out.println("link 3 -> "+(System.currentTimeMillis()-start));

		StringBuffer option=new StringBuffer();
		option.append("<select id='resourceTypeSelect' onchange='selectOption()'>");

		for(Map.Entry<String,List<Map<String,Object>>> entry : result.entrySet()){
			ResourceType resourceType=resourceTypeService.getByCode(entry.getKey());
			if(resourceType==null){
				continue;
			}
			if(code.equals(resourceType.getCode())) {
				option.append("<option value='" + resourceType.getCode() + "' selected='selected' >" + resourceType.getName() + "</option>");
			}else{
				option.append("<option value='" + resourceType.getCode() + "' >" + resourceType.getName() + "</option>");
			}

			StringBuffer stringBuffer=new StringBuffer();
				List<Map<String, Object>> list1 = entry.getValue();
				stringBuffer.append("<li> <img src='images/upload_success.png' style='display: none;height: 20px;width: 20px' >  <img src='/web/static/common/images/file.png' style='width: 14px;margin-right:4px;'><a id='" + i + "' class='item' href='#' ref='ckgl'  '>" + resourceType.getName() + "</a></li><ul>");
				for (int j = 0; j < list1.size(); j++) {
					Map<String, Object> map = list1.get(j);
					stringBuffer.append("<li><img src='/web/static/common/images/file.png' style='width: 14px;margin-right:4px;'><a class='item'  href='#' ref='fhgl' >" + map.get("name") + "</a></li><ul>");
					String name=String.valueOf(map.get("name"));
					List<NetworkInterface> list2 = (List<NetworkInterface>) map.get("list");
					for (int k = 0; k < list2.size(); k++) {
						NetworkInterface networkInterface = list2.get(k);
						String itemName=name+"["+networkInterface.getName()+"]";
						stringBuffer.append("<li><img src='/web/static/images/icon/unselected_icon.png' style='height: 15px;width: 15px' class='selectImg' onclick='selectThis(this)' > <input type='hidden' value='" + itemName + "' ><input type='hidden' value='" + networkInterface.getId() + "' > <a class='item' href='#' ref='rzck'  >" + networkInterface.getName() + "</a></li>");
					}
					stringBuffer.append("</ul>");
				}
				stringBuffer.append("</ul>");
			resultMap.put(entry.getKey(),"<div class='st_tree'><ul>"+stringBuffer.toString()+"</ul></div>");
			i++;
		}
		System.out.println("link 4 -> "+(System.currentTimeMillis()-start));
		resultMap.put("select",option.toString()+"</select>");

		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "dataSelect",method = RequestMethod.POST)
	public Map<String,String> treeMiddlewareForm(String code) {
		if(StringUtils.isEmpty(code)){
			code="1";
		}
		Map<String,String> resultTree=new HashedMap();
		Long start=System.currentTimeMillis();
		List<ResourceIndicatorlist> indicatorlist=resourceIndicatorlistService.findList(new ResourceIndicatorlist());
		Map<String,List<ResourceIndicatorlist>> indicatorMap=new HashedMap();
		System.out.println("dataSelect 1 -> "+(System.currentTimeMillis()-start));
		for(ResourceIndicatorlist indicator:indicatorlist){
			if(indicatorMap.get(indicator.getResourceId())==null){
				List<ResourceIndicatorlist>  indicatorlist2=new ArrayList<>();
				indicatorlist2.add(indicator);
				indicatorMap.put(indicator.getResourceId(),indicatorlist2);
			}else{
				indicatorMap.get(indicator.getResourceId()).add(indicator);
			}
		}


		System.out.println("dataSelect 2 -> "+(System.currentTimeMillis()-start));
		Map<String,List<Map<String,Object>>> result=new HashedMap();
		for (Map.Entry<String,List<ResourceIndicatorlist>> entry : indicatorMap.entrySet()) {
			Resource resource=resourceService.get(entry.getKey());
			if(resource==null){
				continue;
			}
			if(result.get(resource.getResourceType().getParent().getCode())!=null){
				Map<String, Object> map=new HashedMap();
				map.put("list",entry.getValue());
				map.put("name",resource.getName());
				map.put("id",resource.getId());
				result.get(resource.getResourceType().getParent().getCode()).add(map);
			}else{
				List<Map<String,Object>> ll=new ArrayList<>();
				Map<String, Object> map=new HashedMap();
				map.put("list",entry.getValue());
				map.put("name",resource.getName());
				map.put("id",resource.getId());
				ll.add(map);
				result.put(resource.getResourceType().getParent().getCode(),ll);
			}
		}
		int i=0;
		System.out.println("dataSelect 3 -> "+(System.currentTimeMillis()-start));
		StringBuffer option=new StringBuffer();
		option.append("<label style='display:inline-block;width:76px;'>资源类型：</label><select style='display:inline-block;width:146px;' id='resourceTypeSelect' onchange='selectOption()'>");


		for(Map.Entry<String,List<Map<String,Object>>> entry : result.entrySet()){
			ResourceType resourceType=resourceTypeService.getByCode(entry.getKey());
			if(resourceType==null){
				continue;
			}
			if(code.equals(resourceType.getCode())) {
				option.append("<option value='" + resourceType.getCode() + "' selected='selected' >" + resourceType.getName() + "</option>");
			}else{
				option.append("<option value='" + resourceType.getCode() + "' >" + resourceType.getName() + "</option>");
			}

			StringBuffer stringBuffer=new StringBuffer();
			List<Map<String, Object>> list1 = entry.getValue();
			stringBuffer.append("<li> <img src='images/upload_success.png' style='display: none;height: 20px;width: 20px' >  <img src='/web/static/common/images/file.png' style='width: 14px;margin-right:4px;'><a id='" + i + "' class='item' href='#' ref='ckgl'  '>" + resourceType.getName() + "</a></li><ul>");
			for (int j = 0; j < list1.size(); j++) {
				Map<String, Object> map = list1.get(j);
				stringBuffer.append("<li><img src='/web/static/common/images/file.png' style='width: 14px;margin-right:4px;'><a class='item'  href='#' ref='fhgl' >" + map.get("name") + "</a></li><ul>");
				String name=String.valueOf(map.get("name"));
				List<ResourceIndicatorlist> list2 = (List<ResourceIndicatorlist>) map.get("list");
				for (int k = 0; k < list2.size(); k++) {
					ResourceIndicatorlist resourceIndicatorlist = list2.get(k);
					String  itemName="";
					if(resourceIndicatorlist==null||resourceIndicatorlist.getIndicator()==null||resourceIndicatorlist.getIndicator().getName()==null){
						  itemName = "(" + name + ")";
						  stringBuffer.append("<li><img src='/web/static/images/icon/unselected_icon.png' style='height: 15px;width: 15px' class='selectImg' onclick='selectThis(this)' > <input type='hidden' value='" + itemName + "' ><input type='hidden' value='' > <a class='item' href='#' ref='rzck'  ></a></li>");
					}else{
						  itemName = "(" + name + ")" +resourceIndicatorlist.getIndicator().getName();
						  stringBuffer.append("<li><img src='/web/static/images/icon/unselected_icon.png' style='height: 15px;width: 15px' class='selectImg' onclick='selectThis(this)' > <input type='hidden' value='" + itemName + "' ><input type='hidden' value='" + resourceIndicatorlist.getId() + "' > <a class='item' href='#' ref='rzck'  >" + resourceIndicatorlist.getIndicator().getName() + "</a></li>");

					}

					}
				stringBuffer.append("</ul>");
			}
			stringBuffer.append("</ul>");
			resultTree.put(entry.getKey(),"<div class='st_tree'><ul>"+stringBuffer.toString()+"</ul></div>");
			i++;
		}
		System.out.println("dataSelect 4 -> "+(System.currentTimeMillis()-start));
		resultTree.put("select",option.toString()+"</select>");
		return resultTree;
	}


	/**
	 * 初始化发现
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("resource:resource:add")
	@RequestMapping(value = "initDiscovery")
	public String initDiscovery(HttpServletResponse response, RedirectAttributes redirectAttributes) {

		return "modules/resource/initdiscovery";
	}

	/**
	 * 单个资源一览
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "singleResourceSelect",method = RequestMethod.POST)
	public Map<String,Object> resourceTreeForm(String code) {
		if(StringUtils.isEmpty(code)){
			code="1";
		}
		Map<String,Object> resultTree=new HashedMap();

	List<Resource> list=resourceService.findList(new Resource());

		Map<String,List<Map<String,Object>>> result=new HashedMap();
		for (Resource resource :list) {
			if(resource==null){
				continue;
			}
			if(result.get(resource.getResourceType().getParent().getCode())!=null){
				Map<String, Object> map=new HashedMap();
				map.put("name",resource.getName());
				map.put("id",resource.getId());
				result.get(resource.getResourceType().getParent().getCode()).add(map);
			}else{
				List<Map<String,Object>> ll=new ArrayList<>();
				Map<String, Object> map=new HashedMap();
				map.put("name",resource.getName());
				map.put("id",resource.getId());
				ll.add(map);
				result.put(resource.getResourceType().getParent().getCode(),ll);
			}
		}
		int i=0;

		StringBuffer option=new StringBuffer();
		option.append("<label style='display:inline-block;width:76px;'>资源类型：</label><select style='display:inline-block;width:146px;' id='resourceTypeSelect' onchange='selectOption()'>");

		for(Map.Entry<String,List<Map<String,Object>>> entry : result.entrySet()){
			ResourceType resourceType=resourceTypeService.getByCode(entry.getKey());
			if(resourceType==null){
				continue;
			}
			if(!("3".equals(resourceType.getCode())||"8".equals(resourceType.getCode()))) {
				if (code.equals(resourceType.getCode())) {
					option.append("<option value='" + resourceType.getCode() + "' selected='selected' >" + resourceType.getName() + "</option>");
				} else {
					option.append("<option value='" + resourceType.getCode() + "' >" + resourceType.getName() + "</option>");
				}
			}

			StringBuffer stringBuffer=new StringBuffer();
			List<Map<String, Object>> list1 = entry.getValue();
			stringBuffer.append("<li> <img src='images/upload_success.png' style='display: none;height: 20px;width: 20px' >  <img src='/web/static/common/images/file.png' style='width: 14px;margin-right:4px;'><a id='" + i + "' class='item' href='#' ref='ckgl'  '>" + resourceType.getName() + "</a></li><ul>");
			for (int j = 0; j < list1.size(); j++) {
				Map<String, Object> map = list1.get(j);
				stringBuffer.append("<li><img src='/web/static/images/icon/unselected_icon.png' style='height: 15px;width: 15px' class='selectImg' onclick='selectThis(this)' ><input  type='hidden' value='"+map.get("id")+"'> <a class='item'  href='#' ref='fhgl' >" + map.get("name") + "</a></li><ul>");
				stringBuffer.append("</ul>");
			}
			stringBuffer.append("</ul>");
			resultTree.put(entry.getKey(),"<div class='st_tree'><ul>"+stringBuffer.toString()+"</ul></div>");
			i++;
		}
		resultTree.put("select",option.toString()+"</select>");
		resultTree.put("indicators","");
		return resultTree;
	}


	/**
	 * 更新资源名称
	 * @param name
	 * @param resourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateResourceName",method = RequestMethod.POST)
	public Boolean updateResourceName(String name,String resourceId) {
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(resourceId)){
			return false;
		}
		int num=resourceService.updateResourceName(resourceId,name);
		if(num==1){
			return true;
		}

		return false;
	}


	/**
	 * 更新资源读共同体
	 * @param rdcommunity
	 * @param resourceBaseInfoId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateResourceRdcommunity",method = RequestMethod.POST)
	public Boolean updateResourceRdcommunity(String rdcommunity,String resourceBaseInfoId) {
		if(StringUtils.isEmpty(rdcommunity)||StringUtils.isEmpty(resourceBaseInfoId)){
			return false;
		}
		int num=resourceService.updateResourceRdcommunity(resourceBaseInfoId,rdcommunity);
		if(num==1){
			return true;
		}
		return false;
	}





	/**
	 * 更新资源ip
	 * @param ip
	 * @param resourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateResourceIp",method = RequestMethod.POST)
	public Boolean updateResourceIp(String ip,String resourceId) {
		if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(resourceId)){
			return false;
		}
		int num=resourceService.updateResourceIp(resourceId,ip);
		if(num==1){
			return true;
		}
		return false;
	}






	/**
	 * 计算网络设备、服务器、安全设备健康度
	 * @param resource
	 */
	void setHealthDegreeValue(Resource resource){
		DecimalFormat df = new DecimalFormat("######0.00");
		int successNum=0;
		String value = "0";
		List<ResponseTime> list=resourceService.findResponseTimeList(resource.getId());
		if(CheckObject.checkList(list)){
			for(ResponseTime item:list){
                if(Integer.parseInt(item.getTime())>0){
					successNum++;
				}
			}
			value=df.format(Transformation.null2Double((successNum* 100.00)/list.size()));
			HealthDegree healthDegree=new HealthDegree();
			healthDegree.setResourceId(resource.getId());
			healthDegree.setHealthDegree(value);
			healthDegree.setCreateDate(new Date());
			resourceService.saveHealthDegree(healthDegree);
		}
	}


	/**
	 * 计算网络设备、服务器、安全设备可用率
	 * @param resource
	 */
	void setAvailabilityRateValue(Resource resource) {
		DecimalFormat df = new DecimalFormat("######0.00");
		String value = "0";
		MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
		CpuUsedRate cpuUsedRate=cpuService.getTopCpuUsedRate(resource.getId());

		if(memoryUsedRate!=null&&memoryUsedRate.getUsedRate()!=null&&cpuUsedRate!=null&&cpuUsedRate.getUsedRate()!=null) {
			double total=200.00-(Transformation.null2Double(memoryUsedRate.getUsedRate())+Transformation.null2Double(cpuUsedRate.getUsedRate()));
			value=df.format(Transformation.null2Double(total/2.00));
			AvailabilityRate availabilityRate = new AvailabilityRate();
			availabilityRate.setResourceId(resource.getId());
			availabilityRate.setAvailabilityRate(value);
			availabilityRate.setCreateDate(new Date());
			resourceService.saveAvailabilityRate(availabilityRate);
		}
	}



	void setDataBaseMiddle(Resource resource){
		Boolean conn=false;
		DecimalFormat df = new DecimalFormat("######0.00");
		int successNum=0;
		String value = "0";
		List<ResponseTime> list = resourceService.findResponseTimeList(resource.getId());
		if (CheckObject.checkList(list)) {
			for (ResponseTime item : list) {
				if (Integer.parseInt(item.getTime()) > 0) {
					successNum++;
				}
			}
			value = df.format(Transformation.null2Double((successNum * 100.00) / list.size() ));
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




     String getHealthDegree(Resource resource){
		 ResponseTime responseTime=resourceService.getTopResponseTime(resource.getId());
		 if(responseTime!=null&&responseTime.getTime().equals("-1")){
			 return "0";
		 }
		 HealthDegree healthDegree=resourceService.getTopHealthDegree(resource.getId());
		 if(healthDegree!=null&&healthDegree.getHealthDegree()!=null){
			 return String.valueOf(Transformation.null2Double(healthDegree.getHealthDegree()));
		 }

		return "0";
	 }

	String getAvailabilityRate(Resource resource){
		AvailabilityRate availabilityRate=resourceService.getTopAvailabilityRate(resource.getId());
		if(availabilityRate!=null&&availabilityRate.getAvailabilityRate()!=null){
			return String.valueOf(Transformation.null2Double(availabilityRate.getAvailabilityRate()));
		}
		return "0";
	}


//=======================================摩卡二期========================================

	@RequestMapping(value = "panorama",method = RequestMethod.GET)
	public String Panorama(){
		return "/modules/resource/resourcePanorama";
	}

	@RequestMapping(value="collector" ,method = RequestMethod.GET)
	public String collector(){
		return "/modules/collector/collectorList";
	}

	@RequestMapping(value="editResource" ,method = RequestMethod.GET)
	public String editResource(){
		return "/modules/collector/editResource";
	}


	/**
	 * 全景视图需要的每个CPU平均使用率
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cpuPanorama",method = RequestMethod.GET)
	public List<CpuUsedRate> getCpuPanorama(){

		List<CpuUsedRate> cpuUsedRates = cpuService.getCpuPanorama();
		List<CpuUsedRate>  newCpuUsedRates = new ArrayList<>();

		cpuUsedRates.stream()
				.collect(Collectors.groupingBy(CpuUsedRate::getResourceId,Collectors.toList()))
				.forEach((resourceId,listByResourceId)->{
					CpuUsedRate cpuUsedRate = listByResourceId.stream()
                            .max(Comparator.comparingLong(c->c.getCreateDate().getTime())).get();
					newCpuUsedRates.add(cpuUsedRate);
		});

		return newCpuUsedRates;
	}
	/**
	 * 全景视图需要的内存利用率
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "memoryPanorama",method = RequestMethod.GET)
	public List<MemoryUsedRate> getMemoryPanorama(){

		List<MemoryUsedRate> memoryUsedRates = memoryService.getMemoryPanorama();
		List<MemoryUsedRate> newMemoryUsedRates = new ArrayList<>();

		memoryUsedRates.stream()
				.collect(Collectors.groupingBy(MemoryUsedRate::getResourceId,Collectors.toList()))
				.forEach((resourceId,listByResourceId)->{
					MemoryUsedRate memoryUsedRate = listByResourceId.stream()
							.max(Comparator.comparingLong(c->c.getCreateDate().getTime())).get();
					newMemoryUsedRates.add(memoryUsedRate);
				});

		return newMemoryUsedRates;

	}






	@GetMapping("/exportData")
	@ResponseBody
	public void exportData( HttpServletResponse response,String data) {
		try {
			Long start =System.currentTimeMillis();
			String title = "服务器";
			Resource resource=new Resource();
			resource.setSelectResourceTypeCodeIds("2");
			List<Resource> list = resourceService.findList(resource);
			String[] head = {"服务器名称", "管理IP","平均健康度","可用率","非健康时长","非健康起始时间","异常等级",
					"异常项","CPU平均值","CPU最高值","CPU最高值时刻","内存平均值","内存最高值","内存最高值时刻" ,"最高利用率磁盘名称" };
			HSSFWorkbook workbook = new HSSFWorkbook(); //产生工作簿对象
			HSSFSheet sheet = workbook.createSheet("服务器"); //产生工作表对象
			setSheetStyle(sheet);
			// 设置单元格类型
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			setHeadStyle(cellStyle, workbook);

			HSSFCellStyle cellStyleRed = workbook.createCellStyle();
			setHeadStyleRed(cellStyleRed, workbook);


			HSSFCellStyle cellStyleYellow = workbook.createCellStyle();
			setHeadStyleYellow(cellStyleYellow, workbook);


			//产生一行
			HSSFRow row1 = sheet.createRow((short) 0);
			//添加表头
			for (int i = 0; i < head.length; i++) {
				Cell cell1 = row1.createCell(i);
				cell1.setCellValue(head[i]);
				cell1.setCellStyle(cellStyle);
			}

			SimpleDateFormat sdfDay=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			DecimalFormat df = new DecimalFormat("######0.00");

			Date startDate=null;
			Date endDate=null;
			int b = 0;
			if(StringUtils.isNotBlank(data)){
				data=data+" 00:00:00";
				try {
					b = sdf.parse(data).getDay();
					Long fTime = sdf.parse(data).getTime() - b * 24 * 3600000;
					startDate=new Date();
					startDate.setTime(fTime + (1 * 24 * 3600000));

					endDate=new Date();
					endDate.setTime(fTime + (7 * 24 * 3600000));

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				b = new Date().getDay();
				Long fTime = new Date().getTime() - b * 24 * 3600000;
				startDate=new Date();
				startDate.setTime(fTime + (1 * 24 * 3600000));

				endDate=new Date();
				endDate.setTime(fTime + (7 * 24 * 3600000));
			}


			for (int i = 0; i < list.size(); i++) {
				row1 = sheet.createRow(i + 1);
				Resource r = list.get(i);
				Cell cellName =row1.createCell(0);
				cellName.setCellValue(r.getName());

				row1.createCell(1).setCellValue(r.getIp());
				Long startTime=System.currentTimeMillis();
				/*List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeListDate(r.getId(),startDate,endDate);
				String healthDegreeTotal=resourceService.findHealthDegreeListDateTotal(r.getId(),startDate,endDate);
				List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateListDate(r.getId(),startDate,endDate);
				String availabilityRateTotal=resourceService.findAvailabilityRateListDateTotal(r.getId(),startDate,endDate);*/

				List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeListDate(r.getId(),startDate,endDate);
				Double healthDegreeTotal=0.0;
				for(HealthDegree h:healthDegreeList){
					healthDegreeTotal+=Transformation.null2Double(h.getHealthDegree());
				}
				System.out.println("000000002222  ====> "+(System.currentTimeMillis()-startTime));
				List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateListDate(r.getId(),startDate,endDate);

				Double availabilityRateTotal=0.0;
				for(AvailabilityRate h:availabilityRateList){
					availabilityRateTotal+=Transformation.null2Double(h.getAvailabilityRate());
				}

				System.out.println("00000000  ====> "+(System.currentTimeMillis()-startTime));

				if(healthDegreeList.size()>0){
					row1.createCell(2).setCellValue(df.format(Transformation.null2Double(healthDegreeTotal)/healthDegreeList.size()));
				}else{
					row1.createCell(2).setCellValue("-");
				}


				if(healthDegreeList.size()>0){
					row1.createCell(3).setCellValue(df.format(Transformation.null2Double(availabilityRateTotal)/availabilityRateList.size()));
				}else{
					row1.createCell(3).setCellValue("-");
				}

				System.out.println("11  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
				String unHealthStartTime=null;
				Long unHealthTotalTime=0L;
				Long time=0L;
				for(HealthDegree h:healthDegreeList){
					if(Transformation.null2Double(h.getHealthDegree())<100.00){
						if(unHealthStartTime==null){
							unHealthStartTime=sdf.format(h.getCreateDate());
						}
						//unHealth=true;
						if(time>0L){
							unHealthTotalTime+=h.getCreateDate().getTime()-time;
							time=0l;
						}
						time=h.getCreateDate().getTime();
					}else{
						if(time>0L){
							unHealthTotalTime+=h.getCreateDate().getTime()-time;
							time=0l;
						}
					}
				}

				if(time>0L){
					unHealthTotalTime+=System.currentTimeMillis()-time;
					time=0l;
				}



				row1.createCell(4).setCellValue(formatTime(unHealthTotalTime));
				row1.createCell(5).setCellValue(unHealthStartTime);

				List<ResourceException> exceptionList=resourceExceptionService.findListByResourceDate(r.getId(),null,startDate,endDate);
				if(exceptionList.size()>0){
					row1.createCell(6).setCellValue(exceptionList.get(0).getExceptionClass());
					row1.createCell(7).setCellValue(exceptionList.get(0).getIndicatorName());
				}else {
					row1.createCell(6).setCellValue("正常");
					row1.createCell(7).setCellValue("-");
				}

				System.out.println("22  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");



				List<CpuUsedRate> cpuUsedRateList=cpuService.findUsedRateListOfTime(r.getId(),startDate,endDate);
				if(cpuUsedRateList.size()>0){
					Cell usedRateAvg = row1.createCell(8);
					Double avg=Transformation.null2Double(cpuService.findTotalUsedRateListOfTime(r.getId(),startDate,endDate))/cpuUsedRateList.size();
					usedRateAvg.setCellValue(df.format(avg));
					if(avg>80){
						usedRateAvg.setCellStyle(cellStyleRed);
					}
					if(avg>60&&avg<=80){
						usedRateAvg.setCellStyle(cellStyleYellow);
					}

					Cell cellCpuUsedRate = row1.createCell(9);
					cellCpuUsedRate.setCellValue(cpuUsedRateList.get(0).getUsedRate());
					if(Transformation.null2Double(cpuUsedRateList.get(0).getUsedRate())>80){
						cellCpuUsedRate.setCellStyle(cellStyleRed);
					}
					if(Transformation.null2Double(cpuUsedRateList.get(0).getUsedRate())>60&&Transformation.null2Double(cpuUsedRateList.get(0).getUsedRate())<=80){
						cellCpuUsedRate.setCellStyle(cellStyleYellow);
					}

					row1.createCell(10).setCellValue(sdf.format(cpuUsedRateList.get(0).getCreateDate()));
				}else{
					row1.createCell(8).setCellValue("-");
					row1.createCell(9).setCellValue("-");
					row1.createCell(10).setCellValue("-");
				}



				System.out.println("33  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
				List<MemoryUsedRate> memoryUsedRateList=memoryService.findUsedRateListOfTime(r.getId(),startDate,endDate);
				if(memoryUsedRateList.size()>0){


					Cell usedRateAvg = row1.createCell(11);
					Double avg=Transformation.null2Double(cpuService.findTotalUsedRateListOfTime(r.getId(),startDate,endDate))/memoryUsedRateList.size();
					usedRateAvg.setCellValue(df.format(avg));
					if(avg>80){
						usedRateAvg.setCellStyle(cellStyleRed);
					}
					if(avg>60&&avg<=80){
						usedRateAvg.setCellStyle(cellStyleYellow);
					}


					Cell cellCpuUsedRate = row1.createCell(9);
					cellCpuUsedRate.setCellValue(memoryUsedRateList.get(0).getUsedRate());
					if(Transformation.null2Double(memoryUsedRateList.get(0).getUsedRate())>80){
						cellCpuUsedRate.setCellStyle(cellStyleRed);
					}
					if(Transformation.null2Double(memoryUsedRateList.get(0).getUsedRate())>60&&Transformation.null2Double(memoryUsedRateList.get(0).getUsedRate())<=80){
						cellCpuUsedRate.setCellStyle(cellStyleYellow);
					}






					row1.createCell(13).setCellValue(sdf.format(memoryUsedRateList.get(0).getCreateDate()));
				}else{
					row1.createCell(11).setCellValue("-");
					row1.createCell(12).setCellValue("-");
					row1.createCell(13).setCellValue("-");
				}



				System.out.println("44  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
				Disk disk=diskService.getTopUsed(r.getId(),startDate,endDate);
				row1.createCell(14).setCellValue(disk==null?"-":disk.getName());


				System.out.println("55  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
			}
			OutputStream os = response.getOutputStream();
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(title + ".xls", "UTF-8"));
			// 将工作簿进行输出
			workbook.write(os);
			os.flush();
			// 关闭输出流
			os.close();
			System.out.println("导出总耗时 =======> "+(System.currentTimeMillis()-start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


















	@GetMapping("/exportNetworkData")
	@ResponseBody
	public void exportNetworkData( HttpServletResponse response,String data) {
		try {
			Long start =System.currentTimeMillis();
			String title = "网络设备";
			Resource resource=new Resource();
			resource.setSelectResourceTypeCodeIds("1");
			List<Resource> list = resourceService.findList(resource);
			String[] head = {"网络设备名称", "管理IP","平均健康度","可用率","非健康时长","非健康起始时间","异常等级",
					"异常项","CPU平均值","CPU最高值","CPU最高值时刻","内存平均值","内存最高值","内存最高值时刻" ,"最高速率端口" };
			HSSFWorkbook workbook = new HSSFWorkbook(); //产生工作簿对象
			HSSFSheet sheet = workbook.createSheet("服务器"); //产生工作表对象
			setSheetStyle(sheet);
			// 设置单元格类型
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			setHeadStyle(cellStyle, workbook);

			HSSFCellStyle cellStyleRed = workbook.createCellStyle();
			setHeadStyleRed(cellStyleRed, workbook);


			HSSFCellStyle cellStyleYellow = workbook.createCellStyle();
			setHeadStyleYellow(cellStyleYellow, workbook);


			//产生一行
			HSSFRow row1 = sheet.createRow((short) 0);
			//添加表头
			for (int i = 0; i < head.length; i++) {
				Cell cell1 = row1.createCell(i);
				cell1.setCellValue(head[i]);
				cell1.setCellStyle(cellStyle);
			}

			SimpleDateFormat sdfDay=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			DecimalFormat df = new DecimalFormat("######0.00");

			Date startDate=null;
			Date endDate=null;
			int b = 0;
			if(StringUtils.isNotBlank(data)){
				data=data+" 00:00:00";
				try {
					b = sdf.parse(data).getDay();
					Long fTime = sdf.parse(data).getTime() - b * 24 * 3600000;
					startDate=new Date();
					startDate.setTime(fTime + (1 * 24 * 3600000));

					endDate=new Date();
					endDate.setTime(fTime + (7 * 24 * 3600000));

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				b = new Date().getDay();
				Long fTime = new Date().getTime() - b * 24 * 3600000;
				startDate=new Date();
				startDate.setTime(fTime + (1 * 24 * 3600000));

				endDate=new Date();
				endDate.setTime(fTime + (7 * 24 * 3600000));
			}


			for (int i = 0; i < list.size(); i++) {
				row1 = sheet.createRow(i + 1);
				Resource r = list.get(i);
				Cell cellName =row1.createCell(0);
				cellName.setCellValue(r.getName());

				row1.createCell(1).setCellValue(r.getIp());
				Long startTime=System.currentTimeMillis();
				/*List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeListDate(r.getId(),startDate,endDate);
				String healthDegreeTotal=resourceService.findHealthDegreeListDateTotal(r.getId(),startDate,endDate);
				List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateListDate(r.getId(),startDate,endDate);
				String availabilityRateTotal=resourceService.findAvailabilityRateListDateTotal(r.getId(),startDate,endDate);*/

				List<HealthDegree> healthDegreeList=resourceService.findHealthDegreeListDate(r.getId(),startDate,endDate);
				Double healthDegreeTotal=0.0;
				for(HealthDegree h:healthDegreeList){
					healthDegreeTotal+=Transformation.null2Double(h.getHealthDegree());
				}
				System.out.println("000000002222  ====> "+(System.currentTimeMillis()-startTime));
				List<AvailabilityRate> availabilityRateList=resourceService.findAvailabilityRateListDate(r.getId(),startDate,endDate);

				Double availabilityRateTotal=0.0;
				for(AvailabilityRate h:availabilityRateList){
					availabilityRateTotal+=Transformation.null2Double(h.getAvailabilityRate());
				}

				System.out.println("00000000  ====> "+(System.currentTimeMillis()-startTime));

				if(healthDegreeList.size()>0){
					row1.createCell(2).setCellValue(df.format(Transformation.null2Double(healthDegreeTotal)/healthDegreeList.size()));
				}else{
					row1.createCell(2).setCellValue("-");
				}


				if(healthDegreeList.size()>0){
					row1.createCell(3).setCellValue(df.format(Transformation.null2Double(availabilityRateTotal)/availabilityRateList.size()));
				}else{
					row1.createCell(3).setCellValue("-");
				}

				System.out.println("11  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
				String unHealthStartTime=null;
				Long unHealthTotalTime=0L;
				Long time=0L;
				for(HealthDegree h:healthDegreeList){
					if(Transformation.null2Double(h.getHealthDegree())<100.00){
						if(unHealthStartTime==null){
							unHealthStartTime=sdf.format(h.getCreateDate());
						}
						//unHealth=true;
						if(time>0L){
							unHealthTotalTime+=h.getCreateDate().getTime()-time;
							time=0l;
						}
						time=h.getCreateDate().getTime();
					}else{
						if(time>0L){
							unHealthTotalTime+=h.getCreateDate().getTime()-time;
							time=0l;
						}
					}
				}

				if(time>0L){
					unHealthTotalTime+=System.currentTimeMillis()-time;
					time=0l;
				}



				row1.createCell(4).setCellValue(formatTime(unHealthTotalTime));
				row1.createCell(5).setCellValue(unHealthStartTime);

				List<ResourceException> exceptionList=resourceExceptionService.findListByResourceDate(r.getId(),null,startDate,endDate);
				if(exceptionList.size()>0){
					row1.createCell(6).setCellValue(exceptionList.get(0).getExceptionClass());
					row1.createCell(7).setCellValue(exceptionList.get(0).getIndicatorName());
				}else {
					row1.createCell(6).setCellValue("正常");
					row1.createCell(7).setCellValue("-");
				}

				System.out.println("22  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");



				List<CpuUsedRate> cpuUsedRateList=cpuService.findUsedRateListOfTime(r.getId(),startDate,endDate);
				if(cpuUsedRateList.size()>0){
					Cell usedRateAvg = row1.createCell(8);
					Double avg=Transformation.null2Double(cpuService.findTotalUsedRateListOfTime(r.getId(),startDate,endDate))/cpuUsedRateList.size();
					usedRateAvg.setCellValue(df.format(avg));
					if(avg>80){
						usedRateAvg.setCellStyle(cellStyleRed);
					}
					if(avg>60&&avg<=80){
						usedRateAvg.setCellStyle(cellStyleYellow);
					}

					Cell cellCpuUsedRate = row1.createCell(9);
					cellCpuUsedRate.setCellValue(cpuUsedRateList.get(0).getUsedRate());
					if(Transformation.null2Double(cpuUsedRateList.get(0).getUsedRate())>80){
						cellCpuUsedRate.setCellStyle(cellStyleRed);
					}
					if(Transformation.null2Double(cpuUsedRateList.get(0).getUsedRate())>60&&Transformation.null2Double(cpuUsedRateList.get(0).getUsedRate())<=80){
						cellCpuUsedRate.setCellStyle(cellStyleYellow);
					}

					row1.createCell(10).setCellValue(sdf.format(cpuUsedRateList.get(0).getCreateDate()));
				}else{
					row1.createCell(8).setCellValue("-");
					row1.createCell(9).setCellValue("-");
					row1.createCell(10).setCellValue("-");
				}



				System.out.println("33  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
				List<MemoryUsedRate> memoryUsedRateList=memoryService.findUsedRateListOfTime(r.getId(),startDate,endDate);
				if(memoryUsedRateList.size()>0){


					Cell usedRateAvg = row1.createCell(11);
					Double avg=Transformation.null2Double(cpuService.findTotalUsedRateListOfTime(r.getId(),startDate,endDate))/memoryUsedRateList.size();
					usedRateAvg.setCellValue(df.format(avg));
					if(avg>80){
						usedRateAvg.setCellStyle(cellStyleRed);
					}
					if(avg>60&&avg<=80){
						usedRateAvg.setCellStyle(cellStyleYellow);
					}


					Cell cellCpuUsedRate = row1.createCell(9);
					cellCpuUsedRate.setCellValue(memoryUsedRateList.get(0).getUsedRate());
					if(Transformation.null2Double(memoryUsedRateList.get(0).getUsedRate())>80){
						cellCpuUsedRate.setCellStyle(cellStyleRed);
					}
					if(Transformation.null2Double(memoryUsedRateList.get(0).getUsedRate())>60&&Transformation.null2Double(memoryUsedRateList.get(0).getUsedRate())<=80){
						cellCpuUsedRate.setCellStyle(cellStyleYellow);
					}






					row1.createCell(13).setCellValue(sdf.format(memoryUsedRateList.get(0).getCreateDate()));
				}else{
					row1.createCell(11).setCellValue("-");
					row1.createCell(12).setCellValue("-");
					row1.createCell(13).setCellValue("-");
				}



				System.out.println("44  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
				NetInterfaceInOutRate netInterfaceInOutRate=networkInterfaceService.getTopUsed(r.getId(),startDate,endDate);
				row1.createCell(14).setCellValue(netInterfaceInOutRate==null?"-":netInterfaceInOutRate.getNetworkInterface().getName());


				System.out.println("55  ====> "+(System.currentTimeMillis()-startTime));
				System.out.println("");
			}
			OutputStream os = response.getOutputStream();
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(title + ".xls", "UTF-8"));
			// 将工作簿进行输出
			workbook.write(os);
			os.flush();
			// 关闭输出流
			os.close();
			System.out.println("导出总耗时 =======> "+(System.currentTimeMillis()-start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void setSheetStyle(HSSFSheet sheet) {
		sheet.setColumnWidth(0,100 * 50);
		sheet.setColumnWidth(1,100 * 50);
		sheet.setColumnWidth(2,100 * 50);
		sheet.setColumnWidth(3,100 * 50);
		sheet.setColumnWidth(4,100 * 50);
		sheet.setColumnWidth(5,100 * 50);
		sheet.setColumnWidth(6,100 * 50);
		sheet.setColumnWidth(7,100 * 50);
		sheet.setColumnWidth(8,100 * 50);
	}

	private void setHeadStyle(HSSFCellStyle cellStyle,HSSFWorkbook workbook ) {
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
	}

	private void setHeadStyleYellow(HSSFCellStyle cellStyle,HSSFWorkbook workbook ) {
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
	}


	private void setHeadStyleRed(HSSFCellStyle cellStyle,HSSFWorkbook workbook ) {
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellStyle.setFont(font);
	}






	public static String formatTime(Long ms) {
		if(ms==0){
			return "-";
		}
		Integer ss = 1000;

		Integer mi = ss * 60;

		Integer hh = mi * 60;

		Integer dd = hh * 24;

		Long day = ms / dd;

		Long hour = (ms - day * dd) / hh;

		Long minute = (ms - day * dd - hour * hh) / mi;

		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;


		StringBuffer sb = new StringBuffer();

		if(day > 0) {
			sb.append(day+"天");
		}
		if(hour > 0) {
			sb.append(hour+"小时");
		}
		if(minute > 0) {
			sb.append(minute+"分");
		}
		if(second > 0) {
			sb.append(second+"秒");
		}
		if(milliSecond > 0) {
			sb.append(milliSecond+"毫秒");
		}
		return sb.toString();

	}









	@ResponseBody
	@RequestMapping(value = "getCpuChartData")
	public Map<String, Object> getCpuChartData(String data,String resourceTypeId) {
		// 服务器 "1987289b07894de3b89a78f44d5ee8a9"
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate=null;
		Date endDate=null;
		int b = 0;
		if(StringUtils.isNotBlank(data)){
			data=data+" 00:00:00";
			try {
				b = sdf.parse(data).getDay();
				Long fTime = sdf.parse(data).getTime() - b * 24 * 3600000;
				startDate=new Date();
				startDate.setTime(fTime + (1 * 24 * 3600000));

				endDate=new Date();
				endDate.setTime(fTime + (7 * 24 * 3600000));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			b = new Date().getDay();
			Long fTime = new Date().getTime() - b * 24 * 3600000;
			startDate=new Date();
			startDate.setTime(fTime + (1 * 24 * 3600000));

			endDate=new Date();
			endDate.setTime(fTime + (7 * 24 * 3600000));
		}


		System.out.println(sdf.format(startDate));
		System.out.println(sdf.format(endDate));

		DecimalFormat df = new DecimalFormat("######0.00");
		List<CpuUsedRate> list=cpuService.findUsedRateListOfTimeType(resourceTypeId,startDate,endDate);
		Map<String, Object> result = new HashMap<String, Object>();
		String[] name = new String[10];
		String[] cpuAvg = new String[10];
		String[] cpuTop = new String[10];
		List<String> ids=new ArrayList<>();
		List<CpuUsedRate> cpus=new ArrayList<>();
		for (CpuUsedRate cpuUsedRate:list) {
			if(!ids.contains(cpuUsedRate.getResourceId())&&resourceService.get(cpuUsedRate.getResourceId())!=null){
				ids.add(cpuUsedRate.getResourceId());
				cpus.add(cpuUsedRate);
			}
			if(ids.size()==10){
				break;
			}
		}

		int i=0;
		for(CpuUsedRate cpuUsedRate:cpus){
			Resource resource=resourceService.get(cpuUsedRate.getResourceId());
			name[i]=resource.getName();
			List<CpuUsedRate> cpuUsedRateList=cpuService.findUsedRateListOfTime(cpuUsedRate.getResourceId(),startDate,endDate);
			Double total=0.0;
			for(CpuUsedRate c: cpuUsedRateList){
				total+=Transformation.null2Double(c.getUsedRate());
			}
			if(cpuUsedRateList.size()>0){
				cpuAvg[i]=df.format(Transformation.null2Double(total/cpuUsedRateList.size()));
			}else{
				cpuAvg[i]="0";
			}

			cpuTop[i]=cpuUsedRate.getUsedRate();
			i++;
		}
		result.put("name",name);
		result.put("cpuAvg", cpuAvg);
		result.put("cpuTop", cpuTop);
		return result;
	}





	@ResponseBody
	@RequestMapping(value = "getMemChartData")
	public Map<String, Object> getMemChartData(String data,String resourceTypeId) {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate=null;
		Date endDate=null;
		int b = 0;
		if(StringUtils.isNotBlank(data)){
			data=data+" 00:00:00";
			try {
				b = sdf.parse(data).getDay();
				Long fTime = sdf.parse(data).getTime() - b * 24 * 3600000;
				startDate=new Date();
				startDate.setTime(fTime + (1 * 24 * 3600000));

				endDate=new Date();
				endDate.setTime(fTime + (7 * 24 * 3600000));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			b = new Date().getDay();
			Long fTime = new Date().getTime() - b * 24 * 3600000;
			startDate=new Date();
			startDate.setTime(fTime + (1 * 24 * 3600000));

			endDate=new Date();
			endDate.setTime(fTime + (7 * 24 * 3600000));
		}






		DecimalFormat df = new DecimalFormat("######0.00");
		List<MemoryUsedRate> list=memoryService.findUsedRateListOfTimetype(resourceTypeId,startDate,endDate);
		Map<String, Object> result = new HashMap<String, Object>();
		String[] name = new String[10];
		String[] memAvg = new String[10];
		String[] memTop = new String[10];
		List<String> ids=new ArrayList<>();
		List<MemoryUsedRate> cpus=new ArrayList<>();
		for (MemoryUsedRate memoryUsedRate:list) {
			if(!ids.contains(memoryUsedRate.getResourceId())&&resourceService.get(memoryUsedRate.getResourceId())!=null){
				ids.add(memoryUsedRate.getResourceId());
				cpus.add(memoryUsedRate);
			}
			if(ids.size()==10){
				break;
			}
		}

		int i=0;
		for(MemoryUsedRate memoryUsedRate:cpus){
			Resource resource=resourceService.get(memoryUsedRate.getResourceId());
			name[i]=resource.getName();
			List<MemoryUsedRate> memoryUsedList=memoryService.findUsedRateListOfTime(memoryUsedRate.getResourceId(),startDate,endDate);
			Double total=0.0;
			for(MemoryUsedRate c: memoryUsedList){
				total+=Transformation.null2Double(c.getUsedRate());
			}
			if(memoryUsedList.size()>0){
				memAvg[i]=df.format(Transformation.null2Double(total/memoryUsedList.size()));
			}else{
				memAvg[i]="0";
			}

			memTop[i]=memoryUsedRate.getUsedRate();
			i++;
		}
		result.put("name",name);
		result.put("memAvg", memAvg);
		result.put("memTop", memTop);
		return result;
	}



	@ResponseBody
	@RequestMapping(value = "getServerCpuChartData")
	public Map<String, Object> getServerCpuChartData(String data,String resourceTypeId) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate=null;
		Date endDate=null;
		int b = 0;
		if(StringUtils.isNotBlank(data)){
			data=data+" 00:00:00";
			try {
				b = sdf.parse(data).getDay();
				Long fTime = sdf.parse(data).getTime() - b * 24 * 3600000;
				startDate=new Date();
				startDate.setTime(fTime + (1 * 24 * 3600000));

				endDate=new Date();
				endDate.setTime(fTime + (7 * 24 * 3600000));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			b = new Date().getDay();
			Long fTime = new Date().getTime() - b * 24 * 3600000;
			startDate=new Date();
			startDate.setTime(fTime + (1 * 24 * 3600000));

			endDate=new Date();
			endDate.setTime(fTime + (7 * 24 * 3600000));
		}


		System.out.println(sdf.format(startDate));
		System.out.println(sdf.format(endDate));

		DecimalFormat df = new DecimalFormat("######0.00");
		List<CpuUsedRate> list=cpuService.findUsedRateListOfTimeType(resourceTypeId,startDate,endDate);
		Map<String, Object> result = new HashMap<String, Object>();
		String[] name = new String[10];
		String[] cpuAvg = new String[10];
		String[] cpuTop = new String[10];
		List<String> ids=new ArrayList<>();
		List<CpuUsedRate> cpus=new ArrayList<>();
		for (CpuUsedRate cpuUsedRate:list) {
			if(!ids.contains(cpuUsedRate.getResourceId())&&resourceService.get(cpuUsedRate.getResourceId())!=null){
				ids.add(cpuUsedRate.getResourceId());
				cpus.add(cpuUsedRate);
			}
			if(ids.size()==10){
				break;
			}
		}

		int i=0;
		for(CpuUsedRate cpuUsedRate:cpus){
			Resource resource=resourceService.get(cpuUsedRate.getResourceId());
			name[i]=resource.getName();
			List<CpuUsedRate> cpuUsedRateList=cpuService.findUsedRateListOfTime(cpuUsedRate.getResourceId(),startDate,endDate);
			Double total=0.0;
			for(CpuUsedRate c: cpuUsedRateList){
				total+=Transformation.null2Double(c.getUsedRate());
			}
			if(cpuUsedRateList.size()>0){
				cpuAvg[i]=df.format(Transformation.null2Double(total/cpuUsedRateList.size()));
			}else{
				cpuAvg[i]="0";
			}

			cpuTop[i]=cpuUsedRate.getUsedRate();
			i++;
		}
		result.put("name",name);
		result.put("cpuAvg", cpuAvg);
		result.put("cpuTop", cpuTop);
		return result;
	}





	@ResponseBody
	@RequestMapping(value = "getServerMemChartData")
	public Map<String, Object> getServerMemChartData(String data,String resourceTypeId) {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate=null;
		Date endDate=null;
		int b = 0;
		if(StringUtils.isNotBlank(data)){
			data=data+" 00:00:00";
			try {
				b = sdf.parse(data).getDay();
				Long fTime = sdf.parse(data).getTime() - b * 24 * 3600000;
				startDate=new Date();
				startDate.setTime(fTime + (1 * 24 * 3600000));

				endDate=new Date();
				endDate.setTime(fTime + (7 * 24 * 3600000));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			b = new Date().getDay();
			Long fTime = new Date().getTime() - b * 24 * 3600000;
			startDate=new Date();
			startDate.setTime(fTime + (1 * 24 * 3600000));

			endDate=new Date();
			endDate.setTime(fTime + (7 * 24 * 3600000));
		}






		DecimalFormat df = new DecimalFormat("######0.00");
		List<MemoryUsedRate> list=memoryService.findUsedRateListOfTimetype(resourceTypeId,startDate,endDate);
		Map<String, Object> result = new HashMap<String, Object>();
		String[] name = new String[10];
		String[] memAvg = new String[10];
		String[] memTop = new String[10];
		List<String> ids=new ArrayList<>();
		List<MemoryUsedRate> cpus=new ArrayList<>();
		for (MemoryUsedRate memoryUsedRate:list) {
			if(!ids.contains(memoryUsedRate.getResourceId())&&resourceService.get(memoryUsedRate.getResourceId())!=null){
				ids.add(memoryUsedRate.getResourceId());
				cpus.add(memoryUsedRate);
			}
			if(ids.size()==10){
				break;
			}
		}

		int i=0;
		for(MemoryUsedRate memoryUsedRate:cpus){
			Resource resource=resourceService.get(memoryUsedRate.getResourceId());
			name[i]=resource.getName();
			List<MemoryUsedRate> memoryUsedList=memoryService.findUsedRateListOfTime(memoryUsedRate.getResourceId(),startDate,endDate);
			Double total=0.0;
			for(MemoryUsedRate c: memoryUsedList){
				total+=Transformation.null2Double(c.getUsedRate());
			}
			if(memoryUsedList.size()>0){
				memAvg[i]=df.format(Transformation.null2Double(total/memoryUsedList.size()));
			}else{
				memAvg[i]="0";
			}

			memTop[i]=memoryUsedRate.getUsedRate();
			i++;
		}
		result.put("name",name);
		result.put("memAvg", memAvg);
		result.put("memTop", memTop);
		return result;
	}




}