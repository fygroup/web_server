/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourceinformation.web;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.resourceconfiginfo.entity.ResourceConfigInfo;
import com.jeeplus.modules.resourceinformation.entity.ResourceInformation;
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
import com.jeeplus.modules.resourceinformation.service.ResourceInformationService;

/**
 * 资源信息表Controller
 * @author le
 * @version 2017-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/resourceinformation/resourceInformation")
public class ResourceInformationController extends BaseController {

	@Autowired
	private ResourceInformationService resourceInformationService;
	
	@ModelAttribute
	public ResourceInformation get(@RequestParam(required=false) String id) {
		ResourceInformation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceInformationService.get(id);
		}
		if (entity == null){
			entity = new ResourceInformation();
		}
		return entity;
	}
	
	/**
	 * 资源信息表列表页面
	 */
	@RequiresPermissions("resourceinformation:resourceInformation:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resourceinformation/resourceInformationList";
	}
	
		/**
	 * 资源信息表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resourceinformation:resourceInformation:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourceInformation resourceInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceInformation> page = resourceInformationService.findPage(new Page<ResourceInformation>(request, response), resourceInformation);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资源信息表表单页面
	 */
	@RequiresPermissions(value={"resourceinformation:resourceInformation:view","resourceinformation:resourceInformation:add","resourceinformation:resourceInformation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceInformation resourceInformation, Model model) {
		model.addAttribute("resourceInformation", resourceInformation);
		if(StringUtils.isBlank(resourceInformation.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/resourceinformation/resourceInformationForm";
	}

	/**
	 * 保存资源信息表
	 */
	@RequiresPermissions(value={"resourceinformation:resourceInformation:add","resourceinformation:resourceInformation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceInformation resourceInformation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceInformation)){
			return form(resourceInformation, model);
		}
		//新增或编辑表单保存
		resourceInformationService.save(resourceInformation);//保存
		addMessage(redirectAttributes, "保存资源信息表成功");
		return "redirect:"+Global.getAdminPath()+"/resourceinformation/resourceInformation/?repage";
	}




	@ResponseBody
	@RequestMapping(value = "edit",method = RequestMethod.POST)
	public String edit(ResourceInformation resourceInformation) {
		resourceInformationService.save(resourceInformation);//保存
		return resourceInformation.getId();
	}


	@ResponseBody
	@RequestMapping(value = "getResourceInformation")
	public AjaxJson getResourcePhysicInfo(ResourceInformation resourceInformation) {
		AjaxJson result = new AjaxJson();
		result.put("resourceInformation",resourceInformation);
		result.setSuccess(true);
		return result;
	}




	/**
	 * 删除资源信息表
	 */
	@ResponseBody
	@RequiresPermissions("resourceinformation:resourceInformation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceInformation resourceInformation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceInformationService.delete(resourceInformation);
		j.setMsg("删除资源信息表成功");
		return j;
	}
	
	/**
	 * 批量删除资源信息表
	 */
	@ResponseBody
	@RequiresPermissions("resourceinformation:resourceInformation:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceInformationService.delete(resourceInformationService.get(id));
		}
		j.setMsg("删除资源信息表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resourceinformation:resourceInformation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourceInformation resourceInformation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资源信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceInformation> page = resourceInformationService.findPage(new Page<ResourceInformation>(request, response, -1), resourceInformation);
    		new ExportExcel("资源信息表", ResourceInformation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源信息表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourceinformation:resourceInformation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceInformation> list = ei.getDataList(ResourceInformation.class);
			for (ResourceInformation resourceInformation : list){
				try{
					resourceInformationService.save(resourceInformation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源信息表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源信息表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源信息表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourceinformation/resourceInformation/?repage";
    }
	
	/**
	 * 下载导入资源信息表数据模板
	 */
	@RequiresPermissions("resourceinformation:resourceInformation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源信息表数据导入模板.xlsx";
    		List<ResourceInformation> list = Lists.newArrayList();
    		new ExportExcel("资源信息表数据", ResourceInformation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourceinformation/resourceInformation/?repage";
    }

}