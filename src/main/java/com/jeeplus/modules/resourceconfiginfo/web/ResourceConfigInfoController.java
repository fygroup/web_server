/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceconfiginfo.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.resourcephysicinfo.entity.ResourcePhysicInfo;
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
import com.jeeplus.modules.resourceconfiginfo.entity.ResourceConfigInfo;
import com.jeeplus.modules.resourceconfiginfo.service.ResourceConfigInfoService;

/**
 * 资源配置信息Controller
 * @author le
 * @version 2017-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/resourceconfiginfo/resourceConfigInfo")
public class ResourceConfigInfoController extends BaseController {

	@Autowired
	private ResourceConfigInfoService resourceConfigInfoService;
	
	@ModelAttribute
	public ResourceConfigInfo get(@RequestParam(required=false) String id) {
		ResourceConfigInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceConfigInfoService.get(id);
		}
		if (entity == null){
			entity = new ResourceConfigInfo();
		}
		return entity;
	}
	
	/**
	 * 资源配置信息列表页面
	 */
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resourceconfiginfo/resourceConfigInfoList";
	}
	
		/**
	 * 资源配置信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourceConfigInfo resourceConfigInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceConfigInfo> page = resourceConfigInfoService.findPage(new Page<ResourceConfigInfo>(request, response), resourceConfigInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资源配置信息表单页面
	 */
	@RequiresPermissions(value={"resourceconfiginfo:resourceConfigInfo:view","resourceconfiginfo:resourceConfigInfo:add","resourceconfiginfo:resourceConfigInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceConfigInfo resourceConfigInfo, Model model) {
		model.addAttribute("resourceConfigInfo", resourceConfigInfo);
		if(StringUtils.isBlank(resourceConfigInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/resourceconfiginfo/resourceConfigInfoForm";
	}

	/**
	 * 保存资源配置信息
	 */
	@RequiresPermissions(value={"resourceconfiginfo:resourceConfigInfo:add","resourceconfiginfo:resourceConfigInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceConfigInfo resourceConfigInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceConfigInfo)){
			return form(resourceConfigInfo, model);
		}
		//新增或编辑表单保存
		resourceConfigInfoService.save(resourceConfigInfo);//保存
		addMessage(redirectAttributes, "保存资源配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/resourceconfiginfo/resourceConfigInfo/?repage";
	}




	@ResponseBody
	@RequestMapping(value = "edit",method = RequestMethod.POST)
	public String edit(ResourceConfigInfo resourceConfigInfo) {
		resourceConfigInfoService.save(resourceConfigInfo);//保存
		return resourceConfigInfo.getId();
	}


	@ResponseBody
	@RequestMapping(value = "getResourceConfigInfo")
	public AjaxJson getResourcePhysicInfo(ResourceConfigInfo resourceConfigInfo) {
		AjaxJson result = new AjaxJson();
		result.put("resourceConfigInfo",resourceConfigInfo);
		result.setSuccess(true);
		return result;
	}




	/**
	 * 删除资源配置信息
	 */
	@ResponseBody
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceConfigInfo resourceConfigInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceConfigInfoService.delete(resourceConfigInfo);
		j.setMsg("删除资源配置信息成功");
		return j;
	}
	
	/**
	 * 批量删除资源配置信息
	 */
	@ResponseBody
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceConfigInfoService.delete(resourceConfigInfoService.get(id));
		}
		j.setMsg("删除资源配置信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourceConfigInfo resourceConfigInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资源配置信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceConfigInfo> page = resourceConfigInfoService.findPage(new Page<ResourceConfigInfo>(request, response, -1), resourceConfigInfo);
    		new ExportExcel("资源配置信息", ResourceConfigInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源配置信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceConfigInfo> list = ei.getDataList(ResourceConfigInfo.class);
			for (ResourceConfigInfo resourceConfigInfo : list){
				try{
					resourceConfigInfoService.save(resourceConfigInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源配置信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源配置信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源配置信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourceconfiginfo/resourceConfigInfo/?repage";
    }
	
	/**
	 * 下载导入资源配置信息数据模板
	 */
	@RequiresPermissions("resourceconfiginfo:resourceConfigInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源配置信息数据导入模板.xlsx";
    		List<ResourceConfigInfo> list = Lists.newArrayList(); 
    		new ExportExcel("资源配置信息数据", ResourceConfigInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourceconfiginfo/resourceConfigInfo/?repage";
    }

}