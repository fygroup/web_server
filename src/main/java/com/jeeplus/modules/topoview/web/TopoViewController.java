/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoview.web;

import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;

import com.jeeplus.common.utils.*;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResponseTime;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.topoline.service.TopoLineService;
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import com.jeeplus.modules.toposymbols.entity.TopoSymbolsResource;
import com.jeeplus.modules.toposymbols.service.TopoSymbolsService;


import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.Logical;
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
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.topoview.entity.TopoView;
import com.jeeplus.modules.topoview.service.TopoViewService;

/**
 * 拓扑视图Controller
 * @author le
 * @version 2017-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/topoview/topoView")
public class TopoViewController extends BaseController {

	@Autowired
	private ApplicationIndicatorService applicationIndicatorService;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private TopoViewService topoViewService;


	@Autowired
	private TopoSymbolsService topoSymbolsService;

	@Autowired
	private TopoLineService topoLineService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private MemoryService memoryService;


	@Autowired
	private CpuService cpuService;

	@Autowired
	private ResourceExceptionService resourceExceptionService;


	@Autowired
	private AreaService areaService;

	@Autowired
	private LinkIndicatorService linkIndicatorService;

	
	@ModelAttribute
	public TopoView get(@RequestParam(required=false) String id) {
		TopoView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = topoViewService.get(id);
		}
		if (entity == null){
			entity = new TopoView();
		}
		return entity;
	}
	
	/**
	 * 拓扑视图列表页面
	 */
	@RequiresPermissions("topoview:topoView:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/topoview/topoViewList";
	}
	
		/**
	 * 拓扑视图列表数据
	 */
	@ResponseBody
	@RequiresPermissions("topoview:topoView:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TopoView topoView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TopoView> page = topoViewService.findPage(new Page<TopoView>(request, response), topoView); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑拓扑视图表单页面
	 */
	@RequiresPermissions(value={"topoview:topoView:view","topoview:topoView:add","topoview:topoView:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TopoView topoView, Model model) {
		model.addAttribute("topoView", topoView);
		if(StringUtils.isBlank(topoView.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/topoview/topoViewForm";
	}

	/**
	 * 保存拓扑视图
	 */
	@RequiresPermissions(value={"topoview:topoView:add","topoview:topoView:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TopoView topoView, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, topoView)){
			return form(topoView, model);
		}
		//新增或编辑表单保存
		topoViewService.save(topoView);//保存
		addMessage(redirectAttributes, "保存拓扑视图成功");
		return "redirect:"+Global.getAdminPath()+"/topoview/topoView/?repage";
	}

	/**
	 * 删除拓扑视图
	 */
	@ResponseBody
	@RequiresPermissions("topoview:topoView:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TopoView topoView, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		topoViewService.delete(topoView);
		j.setMsg("删除拓扑视图成功");
		return j;
	}
	
	/**
	 * 批量删除拓扑视图
	 */
	@ResponseBody
	@RequiresPermissions("topoview:topoView:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			topoViewService.delete(topoViewService.get(id));
		}
		j.setMsg("删除拓扑视图成功");
		return j;
	}


	/**
	 * 批量删除拓扑视图中的资源和线路
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTopoSymbolsOrLines")
	public AjaxJson deleteTopoSymbolsOrLines(HttpServletRequest request,String[] symbolIds, String[] lineIds) {
		AjaxJson j = new AjaxJson();
		topoSymbolsService.deleteSymbolsLinkLine(symbolIds,lineIds);
		j.setMsg("success");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("topoview:topoView:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TopoView topoView, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "拓扑视图"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TopoView> page = topoViewService.findPage(new Page<TopoView>(request, response, -1), topoView);
    		new ExportExcel("拓扑视图", TopoView.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出拓扑视图记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("topoview:topoView:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TopoView> list = ei.getDataList(TopoView.class);
			for (TopoView topoView : list){
				try{
					topoViewService.save(topoView);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条拓扑视图记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条拓扑视图记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入拓扑视图失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/topoview/topoView/?repage";
    }
	
	/**
	 * 下载导入拓扑视图数据模板
	 */
	@RequiresPermissions("topoview:topoView:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "拓扑视图数据导入模板.xlsx";
    		List<TopoView> list = Lists.newArrayList(); 
    		new ExportExcel("拓扑视图数据", TopoView.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/topoview/topoView/?repage";
    }



	@ResponseBody
	@RequestMapping(value = "topoResouceSave")
	public AjaxJson topoResouceSave(TopoView topo) {

		AjaxJson ajaxJson=new AjaxJson();
		ajaxJson.setSuccess(false);
		ajaxJson.setMsg("操作失败");
		if (topo != null){
			TopoView findTopo = topoViewService.findUniqueByProperty("name",topo.getName());
			if (findTopo == null){
				topo.setTimeStamp(new Date());
				topo.setUser(UserUtils.getUser());
				topo.setIsCache("1");
				topo.setIsInstance("1");
				topo.setIsHidden("0");
				topoViewService.save(topo);
				ajaxJson.setSuccess(true);
				ajaxJson.put("data",topo);
				return ajaxJson;
			}else{
				ajaxJson.setMsg("当前拓扑图名称已经存在");
			}
		}
		return ajaxJson;
	}

	@ResponseBody
	@RequestMapping(value = "getTopoViewList",method = RequestMethod.GET)
	public List<TopoView> getTopoViewList(String topoViewId) {
		TopoView topoView=new TopoView();
		topoView.setUser(UserUtils.getUser());
		topoView.setId(topoViewId);
		return topoViewService.findList(topoView);
	}


	@ResponseBody
	@RequestMapping(value = "topoViewList",method = RequestMethod.GET)
	public List<TopoView> topoViewList() {
		TopoView topoView=new TopoView();
		topoView.setUser(UserUtils.getUser());
		return topoViewService.getList(topoView);
	}




	@ResponseBody
	@RequestMapping(value = "topoResouceInfo")
	public AjaxJson topoResouceInfo(String topoViewId ) {
		Date now=new Date();
		AjaxJson ajaxJson=new AjaxJson();
		TopoSymbols initTopoSymbols=new TopoSymbols();
		if(StringUtils.isNotBlank(topoViewId)){
			initTopoSymbols.setView(new TopoView(topoViewId));
		}
		List<TopoSymbols> symbolsList=topoSymbolsService.getList(initTopoSymbols);
		Map<String,List<Resource>> resourceMap=new HashedMap();
		if(CheckObject.checkList(symbolsList)){
			for(TopoSymbols topoSymbols:symbolsList){
				Resource resource=resourceService.get(topoSymbols.getObjectId());
				if(resource!=null){
				    if(resourceMap.containsKey(topoSymbols.getView().getId())){
						resourceMap.get(topoSymbols.getView().getId()).add(resource);
					}else{
				    	List<Resource> list=new ArrayList<>();
						list.add(resource);
						resourceMap.put(topoSymbols.getView().getId(),list);
					}
				}
			}
		}
		Map<String,List<TopoSymbolsResource>> resultMap=new HashedMap();
		for (Map.Entry<String,List<Resource>> entry : resourceMap.entrySet()) {
			List<Resource> list=entry.getValue();
			if(CheckObject.checkList(list)){
				List<TopoSymbolsResource> result=new ArrayList<>();
				for(Resource resource:list){
					TopoSymbolsResource topoSymbolsResource=new TopoSymbolsResource();
					topoSymbolsResource.setResource(resource);
					topoSymbolsResource.setIp(resource.getIp());
					topoSymbolsResource.setResourceName(resource.getName());
					topoSymbolsResource.setModel(resource.getModel());
					topoSymbolsResource.setSysDesc(resource.getDescription());
					topoSymbolsResource.setResourceType(resource.getResourceType().getName());
					topoSymbolsResource.setResourceId((resource.getId()));
					topoSymbolsResource.setTopoSymbolId(topoSymbolsService.getSymbolIdByResourceIdAndTopoViewId(resource.getId(),entry.getKey()));
					if(resource.getManufacturer()!=null){
						topoSymbolsResource.setVendor(resource.getManufacturer().getName());
					}
					topoSymbolsResource.setSysUpTime(String.valueOf(now.getTime()-resource.getCreateDate().getTime()));
					MemoryUsedRate memoryUsedRate=memoryService.getTopMemoryUsedRate(resource.getId());
					if(memoryUsedRate!=null){
						topoSymbolsResource.setMemRate(memoryUsedRate.getUsedRate());
					}
					CpuUsedRate cpuUsedRate=cpuService.getTopCpuUsedRate(resource.getId());
					if(cpuUsedRate!=null){
						topoSymbolsResource.setCpuRate(cpuUsedRate.getUsedRate());
					}

					Map<String,String> map=new HashedMap();
					if("1".equals(resource.getResourceType().getParent().getCode())||"2".equals(resource.getResourceType().getParent().getCode())||"6".equals(resource.getResourceType().getParent().getCode())){

						if(memoryUsedRate!=null){
							map.put("memRate",memoryUsedRate.getUsedRate());
						}

						if(cpuUsedRate!=null){
							topoSymbolsResource.setCpuRate(cpuUsedRate.getUsedRate());
							map.put("cpuRate",cpuUsedRate.getUsedRate());
						}

						ResponseTime responseTime=resourceService.getTopResponseTime(resource.getId());
						if(responseTime!=null){
							map.put("icmp",responseTime.getTime());
						}
						map.put("healthDegree","100");

					}else if("4".equals(resource.getResourceType().getParent().getCode())){
						List<ResourceIndicatorlist> indicatorList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"4");
						if(CheckObject.checkList(indicatorList)){
							for(ResourceIndicatorlist item:indicatorList){
								if("41001".equals(item.getIndicator().getItemType())||"42001".equals(item.getIndicator().getItemType())){
									map.put("currentConnNum",item.getValue());
								}

							}
						}
					}else if("5".equals(resource.getResourceType().getParent().getCode())){
						/*List<ResourceIndicatorlist> indicatorList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"5");
						if(CheckObject.checkList(indicatorList)){
							for(ResourceIndicatorlist item:indicatorList){
								map.put(item.getIndicator().getName(),item.getValue()+item.getIndicator().getUnit());
							}
						}*/
					}/*else if("8".equals(resource.getResourceType().getParent().getCode())){
						List<ApplicationIndicator> applicationIndicatorList=applicationIndicatorService.findListByResource(resource.getId());
						if(CheckObject.checkList(applicationIndicatorList)){
							for(ApplicationIndicator item:applicationIndicatorList){
								map.put(item.getIndicatorName(),item.getApplicationIndicatorValue().getValue()+item.getUnit());
							}
						}
					}*/
					topoSymbolsResource.setIndicators(map);
					result.add(topoSymbolsResource);
				}

				List<TopoLine> lines=topoLineService.lineIdsByClass(entry.getKey());
				if(CheckObject.checkList(list)){
					for(TopoLine topoLine:lines) {
						TopoSymbolsResource topoSymbolsResource = new TopoSymbolsResource();
						topoSymbolsResource.setId(topoLine.getId());
						LinkIndicator linkIndicator=linkIndicatorService.get(topoLine.getObjectId());
						if(linkIndicator!=null){
							linkIndicator.setUpSymbolsId(topoSymbolsService.getSymbolIdByResourceIdAndTopoViewId(linkIndicator.getUpLinkEquequipment(),entry.getKey()));
							linkIndicator.setDownSymbolsId(topoSymbolsService.getSymbolIdByResourceIdAndTopoViewId(linkIndicator.getDownLinkEquequipment(),entry.getKey()));
						}
						topoSymbolsResource.setLinkIndicator(linkIndicator);
						result.add(topoSymbolsResource);
					}
				}

				resultMap.put(entry.getKey(),result);
			}
		}
		ajaxJson.setSuccess(true);
		ajaxJson.put("data",resultMap);
		return ajaxJson;
	}


	/**
	 * 拓扑图异常列表
	 * @param exceptionClass
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findExceptionListByTopo")
	public Map<String,List<Map<String,String>>>findExceptionListByTopo(String exceptionClass,String topoVIewId) {

		//获取拓扑图中资源的显示
		List<TopoSymbols> symbolsList=topoSymbolsService.findListByUser(UserUtils.getUser().getId(), topoVIewId);
		List<ResourceException> list=null;
		Map<String,List<Map<String,String>>> result=new HashMap<>();
		if(CheckObject.checkList(symbolsList)){
			for(TopoSymbols topoSymbols:symbolsList){
				String resourceId = topoSymbols.getObjectId();
				String topoViewId = topoSymbols.getView().getId();
				List<Map<String,String>> viewExceptionList=null;
				if(StringUtils.isNotBlank(resourceId)) {
					list = resourceExceptionService.findListByResource(resourceId,exceptionClass);
				}
				if(CheckObject.checkList(list)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(result.get(topoViewId)==null){
						viewExceptionList = new ArrayList<>();
					}else{
						viewExceptionList = result.get(topoViewId);
					}
					for(ResourceException resourceException:list){
						Map<String,String> map=new HashedMap();
						map.put("id",resourceException.getId());
						map.put("indicatorName",resourceException.getIndicatorName());
						map.put("exceptionSource",resourceException.getExceptionSource());
						map.put("exceptionClass", resourceException.getExceptionClass());
						/*map.put("exceptionClass", DictUtils.getDictLabels(resourceException.getExceptionClass(),"exception_class", "-"));*/
						map.put("indicatorEventType",DictUtils.getDictLabels(resourceException.getIndicatorItem().getEventType(),"indicator_event_type", "-"));
						map.put("firstTriggerTime",String.valueOf(resourceException.getFirstTriggerTime().getTime()));
						map.put("lastTriggerTime",String.valueOf(resourceException.getLastTriggerTime().getTime()));
						map.put("currentStatus",resourceException.getCurrentStatus());
						map.put("currentStatusValue",DictUtils.getDictLabels(resourceException.getCurrentStatus(),"exception_current_status", "-"));
						map.put("topoSymbolId", topoSymbols.getId());
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
						viewExceptionList.add(map);
						result.put(topoViewId,viewExceptionList);
					}
				}
			}
		}
		return result;
	}


	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequestMapping(value = "exportPng", method=RequestMethod.POST)
	public AjaxJson exportPng(TopoView topoView, HttpServletRequest request, HttpServletResponse response, String code,String id, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String realPath = "/static/images/thumbnail/";
		try {

			 code = Encodes.unescapeXml(code);
			//InetAddress.getLocalHost();
			/* String URL  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();

			 String code1 = code.replaceAll("xlink:href=\"","xlink:href=\""+URL+"/web/static/" ).replaceAll("fill=\"null\"","fill=\"#68228b\"");
			 System.out.println(code1);*/
			convertToPng(code,Global.getUserfilesBaseDir()+realPath+id+".png");
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg("error");
			return j;
		}

		j.setMsg("succss");
		return j;
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
	 * 将svg字符串转换为png
	 *
	 * @param svgCode svg代码
	 * @param pngFilePath 保存的路径
	 * @throws TranscoderException svg代码异常
	 * @throws IOException io错误
	 */
	public static void convertToPng(String svgCode, String pngFilePath) throws IOException,TranscoderException {

		File file = new File(pngFilePath);

		FileOutputStream outputStream = null;
		try {
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			convertToPng(svgCode, outputStream);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将svgCode转换成png文件，直接输出到流中
	 *
	 * @param svgCode svg代码
	 * @param outputStream 输出流
	 * @throws TranscoderException 异常
	 * @throws IOException io异常
	 */
	public static void convertToPng(String svgCode, OutputStream outputStream)throws TranscoderException, IOException {
		try {
			byte[] bytes = svgCode.getBytes("utf-8");
			PNGTranscoder t = new PNGTranscoder();
			TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
			TranscoderOutput output = new TranscoderOutput(outputStream);
			t.transcode(input, output);
			outputStream.flush();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 删除拓扑视图
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTopoView")
	public AjaxJson deleteTopoView(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String[] idArray = StringUtils.split(ids,",");
		for(String id : idArray){
			topoViewService.delete(new TopoView(id));
		}
		return j;
	}


	/**
	 * 修改拓扑视图名称
	 */
	@ResponseBody
	@RequestMapping(value = "modifyTopoViewName")
	public AjaxJson modifyTopoViewName(String viewId,String name) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		j.setMsg("参数错误");
		if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(viewId)){
			TopoView topoView=	topoViewService.findUniqueByProperty("name",name.trim());
			if(topoView==null){
				int num=topoViewService.modifyTopoViewName(viewId,name);
				if(num==1){
					j.setSuccess(true);
					j.setMsg("修改成功");
				}else{
					j.setMsg("拓补图名称修改失败");
				}
			}else{
				j.setMsg("当前拓补图名称已存在");
			}
		}
		return j;
	}
}