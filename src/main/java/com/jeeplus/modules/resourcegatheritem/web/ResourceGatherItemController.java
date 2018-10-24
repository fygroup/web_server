/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcegatheritem.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;

/**
 * 资源指标采集项Controller
 * @author le
 * @version 2017-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/resourcegatheritem/resourceGatherItem")
public class ResourceGatherItemController extends BaseController {

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;
	
	@ModelAttribute
	public ResourceGatherItem get(@RequestParam(required=false) String id) {
		ResourceGatherItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceGatherItemService.get(id);
		}
		if (entity == null){
			entity = new ResourceGatherItem();
		}
		return entity;
	}
	
	/**
	 * 资源指标采集项列表页面
	 */
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resourcegatheritem/resourceGatherItemList";
	}
	
		/**
	 * 资源指标采集项列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourceGatherItem resourceGatherItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceGatherItem> page = resourceGatherItemService.findPage(new Page<ResourceGatherItem>(request, response), resourceGatherItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资源指标采集项表单页面
	 */
	@RequiresPermissions(value={"resourcegatheritem:resourceGatherItem:view","resourcegatheritem:resourceGatherItem:add","resourcegatheritem:resourceGatherItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceGatherItem resourceGatherItem, Model model) {
		model.addAttribute("resourceGatherItem", resourceGatherItem);
		if(StringUtils.isBlank(resourceGatherItem.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/resourcegatheritem/resourceGatherItemForm";
	}

	/**
	 * 保存资源指标采集项
	 */
	@RequiresPermissions(value={"resourcegatheritem:resourceGatherItem:add","resourcegatheritem:resourceGatherItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceGatherItem resourceGatherItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceGatherItem)){
			return form(resourceGatherItem, model);
		}
		//新增或编辑表单保存
		resourceGatherItemService.save(resourceGatherItem);//保存
		addMessage(redirectAttributes, "保存资源指标采集项成功");
		return "redirect:"+Global.getAdminPath()+"/resourcegatheritem/resourceGatherItem/?repage";
	}
	
	/**
	 * 删除资源指标采集项
	 */
	@ResponseBody
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceGatherItem resourceGatherItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceGatherItemService.delete(resourceGatherItem);
		j.setMsg("删除资源指标采集项成功");
		return j;
	}
	
	/**
	 * 批量删除资源指标采集项
	 */
	@ResponseBody
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceGatherItemService.delete(resourceGatherItemService.get(id));
		}
		j.setMsg("删除资源指标采集项成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourceGatherItem resourceGatherItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资源指标采集项"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceGatherItem> page = resourceGatherItemService.findPage(new Page<ResourceGatherItem>(request, response, -1), resourceGatherItem);
    		new ExportExcel("资源指标采集项", ResourceGatherItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源指标采集项记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceGatherItem> list = ei.getDataList(ResourceGatherItem.class);
			for (ResourceGatherItem resourceGatherItem : list){
				try{
					resourceGatherItemService.save(resourceGatherItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源指标采集项记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源指标采集项记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源指标采集项失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcegatheritem/resourceGatherItem/?repage";
    }
	
	/**
	 * 下载导入资源指标采集项数据模板
	 */
	@RequiresPermissions("resourcegatheritem:resourceGatherItem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源指标采集项数据导入模板.xlsx";
    		List<ResourceGatherItem> list = Lists.newArrayList(); 
    		new ExportExcel("资源指标采集项数据", ResourceGatherItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcegatheritem/resourceGatherItem/?repage";
    }

}