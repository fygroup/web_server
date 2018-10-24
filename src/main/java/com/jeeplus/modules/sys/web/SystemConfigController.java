package com.jeeplus.modules.sys.web;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.Gson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.collect.GeneralMethod;
import com.jeeplus.modules.manufacturer.entity.Manufacturer;
import com.jeeplus.modules.manufacturer.service.ManufacturerService;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.SystemConfig;
import com.jeeplus.modules.sys.service.SystemConfigService;

/**
 * 系统配置Controller
 * @author liugf
 * @version 2016-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/systemConfig")
public class SystemConfigController extends BaseController {

	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private NetworkInterfaceService networkInterfaceService;

	@Autowired
	private ResourceService resourceService;
	
	@ModelAttribute
	public SystemConfig get(@RequestParam(required=false) String id) {
		SystemConfig entity = systemConfigService.get("1");;
		return entity;
	}

	/**
	 * 接收网强发现器发送的全网拓扑发现数据
	 */

	@ResponseBody
	@RequestMapping(value = "discoveryData")
	public boolean discoveryData(String discoveryTxt) {
	//	System.out.println(discoveryTxt);
		String transportTxt = discoveryTxt;
		String linkTxt=discoveryTxt;
		String chinese = "[\u4e00-\u9fa5]";
		List<String> ips=new ArrayList<>();
		List<String> stringList = new ArrayList<String>();
		while (true){
			if (transportTxt.indexOf("管理IP")>0){
				String temp = transportTxt;
				int i = temp.indexOf("使用共同体")+7;
				while (true){
					String a = temp.substring(i,i+1);
					if (a.matches(chinese)){
						break;
					}else {
						i++;
					}
				}
				stringList.add(temp.substring(temp.indexOf("管理IP"),i)) ;
				transportTxt = transportTxt.substring(i,transportTxt.length());
			}else {
				break;
			}
		}
		String result="";
		for (int i = 0; i < stringList.size(); i++) {
			HashMap<String,String> discoveryEntry = new HashMap<String,String>();
			String temp = stringList.get(i);
			discoveryEntry.put(temp.substring(0,temp.indexOf(":")),temp.substring(temp.indexOf(":")+1,temp.indexOf("OID")).trim());
			discoveryEntry.put(temp.substring(temp.indexOf("使用共同体"),temp.indexOf(":",temp.indexOf("使用共同体"))),temp.substring(temp.indexOf(":",temp.indexOf("使用共同体"))+1,temp.length()).trim());
			ips.add(temp.substring(temp.indexOf(":")+1,temp.indexOf("OID")).trim());
			if(i==(stringList.size()-1)){
				result+=JSONUtils.toJSONString(discoveryEntry);
			}else{
				result+=JSONUtils.toJSONString(discoveryEntry)+",";
			}
		}

		String linkResult="";
		String itemResult="";
		while (true){
			if(linkTxt.indexOf("发现链路，链路信息：")!=-1) {
				itemResult = linkTxt.substring(linkTxt.indexOf("发现链路，链路信息：")+10);
				if(itemResult.indexOf("发现链路，链路信息：")!=-1) {
					itemResult = itemResult.substring(itemResult.indexOf("发现链路，链路信息："));
				}else{
					itemResult=itemResult.substring(1) ;
				}
				linkTxt= linkTxt.substring(linkTxt.indexOf("发现链路，链路信息：")+10);
				int i = 1;
				while (true) {
					if (i + 1 > linkTxt.length()) {
						break;
					}
					String a = linkTxt.substring(i, i + 1);
					if (a.matches(chinese)) {
						break;
					} else {
						i++;
					}
				}
				linkTxt = linkTxt.substring(0, i);
				String[] value = linkTxt.split("-&gt;");
				if(value.length == 2){
					HashMap<String,String> linkMap = new HashMap<String,String>();
					String upIp=(value[0]).substring(0,value[0].indexOf(":"));
					String downIp=(value[1]).substring(0,value[1].indexOf(":"));
					if(ips.contains(upIp)&&ips.contains(downIp)) {
						linkMap.put("up", upIp);
						linkMap.put("down", downIp);
						linkResult += JSONUtils.toJSONString(linkMap) + ",";
					}
				}
				linkTxt = itemResult;
			}else{
				break;
			}
		}
		if(linkResult.length()>0){
			linkResult="["+linkResult.substring(0,linkResult.length()-1)+"]";
		}

		result=result.replaceAll("管理IP","ip").replaceAll("使用共同体","rdcommunity").replaceAll("连接类型","snmpType");
		result="["+result+"]";
		String path = Global.class.getResource("/discoverydata.txt").getPath();
		File file=new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(result); // 往文件里写入字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		String linkPath = Global.class.getResource("/discoverylinkdata.txt").getPath();
		File linkFile=new File(linkPath);
		if(!linkFile.exists()){
			linkFile.mkdir();
		}
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(linkFile));
			ps.println(linkResult); // 往文件里写入字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@RequestMapping(value = {"confirmDiscovery", ""})
	public String confirmDiscovery(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String,HashMap<String,String>> discoveryEntrys = new HashMap<String, HashMap<String,String>>();
		List<HashMap<String,String>> linkEntrys = new ArrayList<>();
		try {
			String discoverydata = Global.class.getResource("/discoverydata.txt").getPath();
			String JsonContext = Global.ReadFile(discoverydata);
			JSONArray json = new JSONArray(JsonContext);
			for (int i = 0; i < json.length(); i++) {
				JSONObject item = json.getJSONObject(i);
				HashMap<String, String> discoveryEntry = new HashMap<String, String>();
				discoveryEntry.put("rdcommunity", item.getString("rdcommunity"));
				discoveryEntry.put("ip", item.getString("ip"));
				discoveryEntry.put("desc", GeneralMethod.getDescriptionValue(item.getString("ip"), item.getString("rdcommunity"), "161", "500", "2"));
				discoveryEntrys.put(item.getString("ip"), discoveryEntry);
			}
		}catch (Exception e){
			e.printStackTrace();
			 return "modules/sys/login/sysIndex";
		}
		if(discoveryEntrys.isEmpty()){
			return "modules/sys/login/sysIndex";
		}
		model.addAttribute("manufacturerList", manufacturerService.findList(new Manufacturer()));
		model.addAttribute("discoveryEntrys", discoveryEntrys);
		model.addAttribute("linkEntrys", linkEntrys);
		return "modules/sys/config/confirmDiscovery";
	}



	@RequestMapping(value = {"confirmLinkDiscovery", ""})
	public String confirmLinkDiscovery(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<HashMap<String,Object>> linkEntrys = new ArrayList<>();
		HashMap<String,List<NetworkInterface>> interfaceMap = new HashMap<>();
		try {
			String discoverydata = Global.class.getResource("/discoverylinkdata.txt").getPath();
				String JsonLink = Global.ReadFile(discoverydata);
				JSONArray links = new JSONArray(JsonLink);
				for (int i = 0; i < links.length(); i++) {
					JSONObject item = links.getJSONObject(i);
					HashMap<String, Object> linkEntry = new HashMap<String, Object>();
					linkEntry.put("up", item.getString("up"));
					Resource upResource=resourceService.getByIpTypeOfLink(item.getString("up"));
					if(upResource==null){
						continue;
					}
					linkEntry.put("upResource", upResource);
					Resource downResource=resourceService.getByIpTypeOfLink(item.getString("down"));
					if(downResource==null){
						continue;
					}
					linkEntry.put("down", item.getString("down"));
					linkEntry.put("downResource", downResource);
					linkEntrys.add(linkEntry);
					if(!interfaceMap.containsKey(item.getString("up"))){
						interfaceMap.put(item.getString("up"),networkInterfaceService.getListByIpType(item.getString("up")));
					}
					if(!interfaceMap.containsKey(item.getString("down"))){
						interfaceMap.put(item.getString("down"),networkInterfaceService.getListByIpType(item.getString("down")));
					}
				}
		}catch (Exception e){
			e.printStackTrace();
			return "modules/sys/login/sysIndex";
		}
		if(linkEntrys.size()==0){
			return "modules/sys/login/sysIndex";
		}
		model.addAttribute("linkEntrys", linkEntrys);
		model.addAttribute("interfaceMap", interfaceMap);
		return "modules/sys/config/confirmLinkDiscovery";
	}



	/**
	 * 系统配置列表页面
	 */
	@RequiresPermissions("sys:systemConfig:index")
	@RequestMapping(value = {"index", ""})
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemConfig systemConfig = systemConfigService.get("1");
		model.addAttribute("systemConfig", systemConfig);
		return "modules/sys/config/systemConfig";
	}


	/**
	 * 保存系统配置
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(SystemConfig systemConfig, Model model, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String message = "保存系统配置成功";
        systemConfigService.save(systemConfig);
        j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除系统配置
	 */
	@RequiresPermissions("sys:systemConfig:del")
	@RequestMapping(value = "delete")
	public String delete(SystemConfig systemConfig, RedirectAttributes redirectAttributes) {
		systemConfigService.delete(systemConfig);
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
	}
	
	/**
	 * 批量删除系统配置
	 */
	@RequiresPermissions("sys:systemConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			systemConfigService.delete(systemConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:systemConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SystemConfig systemConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SystemConfig> page = systemConfigService.findPage(new Page<SystemConfig>(request, response, -1), systemConfig);
    		new ExportExcel("系统配置", SystemConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:systemConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SystemConfig> list = ei.getDataList(SystemConfig.class);
			for (SystemConfig systemConfig : list){
				systemConfigService.save(systemConfig);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统配置记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }
	
	/**
	 * 下载导入系统配置数据模板
	 */
	@RequiresPermissions("sys:systemConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置数据导入模板.xlsx";
    		List<SystemConfig> list = Lists.newArrayList(); 
    		new ExportExcel("系统配置数据", SystemConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }
	

}