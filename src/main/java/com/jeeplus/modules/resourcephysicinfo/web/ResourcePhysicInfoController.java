/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcephysicinfo.web;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
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
import com.jeeplus.modules.resourcephysicinfo.entity.ResourcePhysicInfo;
import com.jeeplus.modules.resourcephysicinfo.service.ResourcePhysicInfoService;

/**
 * 资源物理信息Controller
 * @author le
 * @version 2017-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/resourcephysicinfo/resourcePhysicInfo")
public class ResourcePhysicInfoController extends BaseController {

	@Autowired
	private ResourcePhysicInfoService resourcePhysicInfoService;
	
	@ModelAttribute
	public ResourcePhysicInfo get(@RequestParam(required=false) String id) {
		ResourcePhysicInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourcePhysicInfoService.get(id);
		}
		if (entity == null){
			entity = new ResourcePhysicInfo();
		}
		return entity;
	}
	
	/**
	 * 资源物理信息列表页面
	 */
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resourcephysicinfo/resourcePhysicInfoList";
	}
	
		/**
	 * 资源物理信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourcePhysicInfo resourcePhysicInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourcePhysicInfo> page = resourcePhysicInfoService.findPage(new Page<ResourcePhysicInfo>(request, response), resourcePhysicInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资源物理信息表单页面
	 */
	@RequiresPermissions(value={"resourcephysicinfo:resourcePhysicInfo:view","resourcephysicinfo:resourcePhysicInfo:add","resourcephysicinfo:resourcePhysicInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourcePhysicInfo resourcePhysicInfo, Model model) {
		model.addAttribute("resourcePhysicInfo", resourcePhysicInfo);
		if(StringUtils.isBlank(resourcePhysicInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/resourcephysicinfo/resourcePhysicInfoForm";
	}

	/**
	 * 保存资源物理信息
	 */
	@RequiresPermissions(value={"resourcephysicinfo:resourcePhysicInfo:add","resourcephysicinfo:resourcePhysicInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourcePhysicInfo resourcePhysicInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourcePhysicInfo)){
			return form(resourcePhysicInfo, model);
		}
		//新增或编辑表单保存
		resourcePhysicInfoService.save(resourcePhysicInfo);//保存
		addMessage(redirectAttributes, "保存资源物理信息成功");
		return "redirect:"+Global.getAdminPath()+"/resourcephysicinfo/resourcePhysicInfo/?repage";
	}


	@ResponseBody
	@RequestMapping(value = "edit",method = RequestMethod.POST)
	public String edit(ResourcePhysicInfo resourcePhysicInfo) {
		resourcePhysicInfoService.save(resourcePhysicInfo);//保存
		return resourcePhysicInfo.getId();
	}


	@ResponseBody
	@RequestMapping(value = "getResourcePhysicInfo")
	public AjaxJson getResourcePhysicInfo(ResourcePhysicInfo resourcePhysicInfo) {
		AjaxJson result = new AjaxJson();
		result.put("resourcePhysicInfo",resourcePhysicInfo);
		result.setSuccess(true);
		return result;
	}


	/**
	 * 删除资源物理信息
	 */
	@ResponseBody
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourcePhysicInfo resourcePhysicInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourcePhysicInfoService.delete(resourcePhysicInfo);
		j.setMsg("删除资源物理信息成功");
		return j;
	}
	
	/**
	 * 批量删除资源物理信息
	 */
	@ResponseBody
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourcePhysicInfoService.delete(resourcePhysicInfoService.get(id));
		}
		j.setMsg("删除资源物理信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourcePhysicInfo resourcePhysicInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资源物理信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourcePhysicInfo> page = resourcePhysicInfoService.findPage(new Page<ResourcePhysicInfo>(request, response, -1), resourcePhysicInfo);
    		new ExportExcel("资源物理信息", ResourcePhysicInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源物理信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourcePhysicInfo> list = ei.getDataList(ResourcePhysicInfo.class);
			for (ResourcePhysicInfo resourcePhysicInfo : list){
				try{
					resourcePhysicInfoService.save(resourcePhysicInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源物理信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源物理信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源物理信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcephysicinfo/resourcePhysicInfo/?repage";
    }
	
	/**
	 * 下载导入资源物理信息数据模板
	 */
	@RequiresPermissions("resourcephysicinfo:resourcePhysicInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源物理信息数据导入模板.xlsx";
    		List<ResourcePhysicInfo> list = Lists.newArrayList(); 
    		new ExportExcel("资源物理信息数据", ResourcePhysicInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcephysicinfo/resourcePhysicInfo/?repage";
    }

}