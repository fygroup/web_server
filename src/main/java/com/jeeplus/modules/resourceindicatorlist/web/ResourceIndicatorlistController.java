/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceindicatorlist.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourceindicatorlist.entity.UserResourceIndicatorlist;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.commons.collections.list.AbstractLinkedList;
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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;

/**
 * 资源指标列表Controller
 * @author le
 * @version 2017-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/resourceindicatorlist/resourceIndicatorlist")
public class ResourceIndicatorlistController extends BaseController {

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private ResourceService resourceService;
	
	@ModelAttribute
	public ResourceIndicatorlist get(@RequestParam(required=false) String id) {
		ResourceIndicatorlist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceIndicatorlistService.get(id);
		}
		if (entity == null){
			entity = new ResourceIndicatorlist();
		}
		return entity;
	}
	
	/**
	 * 资源指标列表列表页面
	 */
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resourceindicatorlist/resourceIndicatorlistList";
	}
	
		/**
	 * 资源指标列表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourceIndicatorlist resourceIndicatorlist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceIndicatorlist> page = resourceIndicatorlistService.findPage(new Page<ResourceIndicatorlist>(request, response), resourceIndicatorlist); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资源指标列表表单页面
	 */
	@RequiresPermissions(value={"resourceindicatorlist:resourceIndicatorlist:view","resourceindicatorlist:resourceIndicatorlist:add","resourceindicatorlist:resourceIndicatorlist:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceIndicatorlist resourceIndicatorlist, Model model) {
		model.addAttribute("resourceIndicatorlist", resourceIndicatorlist);
		if(StringUtils.isBlank(resourceIndicatorlist.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/resourceindicatorlist/resourceIndicatorlistForm";
	}

	/**
	 * 保存资源指标列表
	 */
	@RequiresPermissions(value={"resourceindicatorlist:resourceIndicatorlist:add","resourceindicatorlist:resourceIndicatorlist:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceIndicatorlist resourceIndicatorlist, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceIndicatorlist)){
			return form(resourceIndicatorlist, model);
		}
		//新增或编辑表单保存
		resourceIndicatorlistService.save(resourceIndicatorlist);//保存
		addMessage(redirectAttributes, "保存资源指标列表成功");
		return "redirect:"+Global.getAdminPath()+"/resourceindicatorlist/resourceIndicatorlist/?repage";
	}
	
	/**
	 * 删除资源指标列表
	 */
	@ResponseBody
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceIndicatorlist resourceIndicatorlist, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceIndicatorlistService.delete(resourceIndicatorlist);
		j.setMsg("删除资源指标列表成功");
		return j;
	}
	
	/**
	 * 批量删除资源指标列表
	 */
	@ResponseBody
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceIndicatorlistService.delete(resourceIndicatorlistService.get(id));
		}
		j.setMsg("删除资源指标列表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourceIndicatorlist resourceIndicatorlist, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资源指标列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceIndicatorlist> page = resourceIndicatorlistService.findPage(new Page<ResourceIndicatorlist>(request, response, -1), resourceIndicatorlist);
    		new ExportExcel("资源指标列表", ResourceIndicatorlist.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源指标列表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceIndicatorlist> list = ei.getDataList(ResourceIndicatorlist.class);
			for (ResourceIndicatorlist resourceIndicatorlist : list){
				try{
					resourceIndicatorlistService.save(resourceIndicatorlist);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源指标列表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源指标列表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源指标列表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourceindicatorlist/resourceIndicatorlist/?repage";
    }
	
	/**
	 * 下载导入资源指标列表数据模板
	 */
	@RequiresPermissions("resourceindicatorlist:resourceIndicatorlist:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源指标列表数据导入模板.xlsx";
    		List<ResourceIndicatorlist> list = Lists.newArrayList(); 
    		new ExportExcel("资源指标列表数据", ResourceIndicatorlist.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourceindicatorlist/resourceIndicatorlist/?repage";
    }


    //保存异常等级
	@ResponseBody
	@RequestMapping(value = "editThresholdSave")
	public boolean editSave(String resourceIndicatorlistId,String thresholdType,String thresholdValue) {
		if(StringUtils.isEmpty(resourceIndicatorlistId)||StringUtils.isEmpty(thresholdType)){
			return false;
		}
		ResourceIndicatorlist resourceIndicatorlist=resourceIndicatorlistService.get(resourceIndicatorlistId);
		if(StringUtils.isEmpty(thresholdValue)){
			thresholdValue=null;
		}
		if(resourceIndicatorlist!=null){
			if("1".equals(thresholdType)){
				resourceIndicatorlist.setHighUrgentThreshold(thresholdValue);
			}else if("2".equals(thresholdType)){
				resourceIndicatorlist.setMiddleUrgentThreshold(thresholdValue);
			}else if("3".equals(thresholdType)){
				resourceIndicatorlist.setNormalUrgentThreshold(thresholdValue);
			}else if("4".equals(thresholdType)){
				resourceIndicatorlist.setTipThreshold(thresholdValue);
			}
			resourceIndicatorlistService.save(resourceIndicatorlist);
			return true;
		}

		return false;
	}




	//资源添加指标保存
	@ResponseBody
	@RequestMapping(value = "addIndicatorSave")
	public boolean  addIndicatorSave(String resourceId, String addIndicatorIds, String gatherTypes,String eventType) {
		List<ResourceIndicatorlist>  list=new ArrayList<>();
		if(StringUtils.isNotBlank(resourceId)&&StringUtils.isNotBlank(addIndicatorIds)&&StringUtils.isNotBlank(gatherTypes)){
			Date now=new Date();
			String[] ids=addIndicatorIds.split(",");
			String[] types=gatherTypes.split(",");
			if(ids.length>0&&types.length>0&&ids.length==types.length){
				for(int i=0;i<ids.length;i++) {
						ResourceIndicatorlist resourceIndicatorlist = new ResourceIndicatorlist();
					    ResourceIndicatorlist original=resourceIndicatorlistService.findResourceIndicator(resourceId,ids[i]);
					    if(original!=null){
							list.add(original);
						}else{
							resourceIndicatorlist.setId(IdGen.uuid());
							resourceIndicatorlist.setCreateDate(now);
							resourceIndicatorlist.setUpdateDate(now);
							resourceIndicatorlist.setDelFlag("0");
							resourceIndicatorlist.setResourceId(resourceId);
							resourceIndicatorlist.setGatherType(types[i]);
							resourceIndicatorlist.setIndicatorId(ids[i]);
							list.add(resourceIndicatorlist);
						}
				}
				if(CheckObject.checkList(list)){
					//保存
					resourceIndicatorlistService.saveList(resourceId,list,eventType);
				}
				return true;
			}else {
				return false;
			}
		}
		return false;
	}


	//删除
	@ResponseBody
	@RequestMapping(value = "delById")
	public boolean delById(String resourceIndicatorlistId) {
		if(StringUtils.isEmpty(resourceIndicatorlistId)){
			return false;
		}
		resourceIndicatorlistService.delById(resourceIndicatorlistId);
		return true;
	}

	//保存用户指标
	@ResponseBody
	@RequestMapping(value = "saveUserIndicator")
	public boolean saveUserIndicator(String resourceIndicatorlistId,String type,String title) {
		if(StringUtils.isEmpty(type)){
			return false;
		}
		ResourceIndicatorlist resourceIndicatorlist=null;
		List<UserResourceIndicatorlist> list=new ArrayList<>();
		if(resourceIndicatorlistId==null){
			resourceIndicatorlistId="";
		}
		String ids[]=resourceIndicatorlistId.split(",");
		for(String item:ids){
			if(StringUtils.isNotBlank(item)){
				if(type.equals("2")&&resourceIndicatorlist==null){
					resourceIndicatorlist=resourceIndicatorlistService.get(item);
					title=resourceIndicatorlist.getResource().getName();
				}
				list.add(new UserResourceIndicatorlist(UserUtils.getUser().getId(),item,type,title));
			}
		}

		resourceIndicatorlistService.saveUserIndicatorList(list,UserUtils.getUser().getId(),type);


		return true;
	}

	//获取用户指标列表
	@ResponseBody
	@RequestMapping(value = "getUserIndicator")
	public Map<String,Object> getUserIndicator(String type) {
		Map<String,Object> map=new HashedMap();
		if(StringUtils.isEmpty(type)){
			return map;
		}
		List<ResourceIndicatorlist> list=resourceIndicatorlistService.findListByUser(UserUtils.getUser().getId(),type);
		map.put("list",list);
		if(CheckObject.checkList(list)) {
			map.put("title", resourceIndicatorlistService.getTitle(type, UserUtils.getUser().getId()));
		}
		return map;
	}



	//获取用户单一资源指标列表
	@ResponseBody
	@RequestMapping(value = "getUserSingleIndicator")
	public Map<String,Object> getUserSingleIndicator(String type) {
		Map<String,Object> map=new HashedMap();
		if(StringUtils.isEmpty(type)){
			return map;
		}
		List<ResourceIndicatorlist> list=resourceIndicatorlistService.findListByUser(UserUtils.getUser().getId(),type);
		map.put("list",list);
		if(CheckObject.checkList(list)) {
			map.put("title", resourceIndicatorlistService.getTitle(type, UserUtils.getUser().getId()));
		}
		return map;
	}


	//获取用户单一资源指标列表
	@ResponseBody
	@RequestMapping(value = "getSingleUserIndicator")
	public Map<String,Object> getSingleUserIndicator() {
		Map<String,Object> map=new HashedMap();
		map.put("title",resourceIndicatorlistService.getTitle("2",UserUtils.getUser().getId()));
		List<ResourceIndicatorlist> list=resourceIndicatorlistService.findListByUser(UserUtils.getUser().getId(),"2");
		if(CheckObject.checkList(list)){
			String resourceId=list.get(0).getResourceId();
			Resource resource=resourceService.get(resourceId);
			if(resource!=null){
				map.put("code",resource.getResourceType().getParent().getCode());
			}else{
				map.put("code","1");
			}
			map.put("selectedResourceId",resourceId);
			List<ResourceIndicatorlist> allList=resourceIndicatorlistService.findListByResourceId(resourceId);
			String html = "";
			String selected = "";
			for(ResourceIndicatorlist item:allList){
				String img="/web/static/images/icon/unselected_icon.png";
				for(ResourceIndicatorlist item2:list){
					if(item2.getId().equals(item.getId())){
						img="/web/static/images/icon/selected_icon.png";
						selected+=item.getId()+",";
						break;
					}
				}
				html +="<div><img src='"+img+"' style='height: 15px;width: 15px' class='indicatorSelectImg' onclick='indicatorSelect(this)' ><input type='text' hidden value='"+ item.getId() +"'/><a href='#'> "+ item.getIndicator().getName() +" </a></div>";
			}
			map.put("html",html);
			map.put("selected",selected);
		}else{
			map.put("code","1");
			map.put("html","");
			map.put("selected","");
			map.put("selectedResourceId","");
		}
		return map;
	}




	/**
	 *
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "resourceIndicatorlist",method = RequestMethod.POST)
	public List<ResourceIndicatorlist> resourceIndicatorlist(String resourceId) {
		return  resourceIndicatorlistService.findListByResourceId(resourceId);
	}


}