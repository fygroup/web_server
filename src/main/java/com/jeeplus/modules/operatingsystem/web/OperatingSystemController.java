/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.operatingsystem.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.service.ResourceTypeService;
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
import com.jeeplus.modules.operatingsystem.entity.OperatingSystem;
import com.jeeplus.modules.operatingsystem.service.OperatingSystemService;

/**
 * 操作系统Controller
 * @author le
 * @version 2017-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/operatingsystem/operatingSystem")
public class OperatingSystemController extends BaseController {

	@Autowired
	private OperatingSystemService operatingSystemService;

	@Autowired
	private ResourceTypeService resourceTypeService;
	
	@ModelAttribute
	public OperatingSystem get(@RequestParam(required=false) String id) {
		OperatingSystem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = operatingSystemService.get(id);
		}
		if (entity == null){
			entity = new OperatingSystem();
		}
		return entity;
	}
	
	/**
	 * 操作系统列表页面
	 */
	@RequiresPermissions("operatingsystem:operatingSystem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/operatingsystem/operatingSystemList";
	}
	
		/**
	 * 操作系统列表数据
	 */
	@ResponseBody
	@RequiresPermissions("operatingsystem:operatingSystem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OperatingSystem operatingSystem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OperatingSystem> page = operatingSystemService.findPage(new Page<OperatingSystem>(request, response), operatingSystem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑操作系统表单页面
	 */
	@RequiresPermissions(value={"operatingsystem:operatingSystem:view","operatingsystem:operatingSystem:add","operatingsystem:operatingSystem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OperatingSystem operatingSystem, Model model) {
		model.addAttribute("serverList", resourceTypeService.getChildren("1987289b07894de3b89a78f44d5ee8a9"));
		model.addAttribute("operatingSystem", operatingSystem);
		if(StringUtils.isBlank(operatingSystem.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/operatingsystem/operatingSystemForm";
	}





	@ResponseBody

	@RequestMapping(value = "selectOperatingSystem")
	public List<OperatingSystem> selectOperatingSystem(String id) {
		List<OperatingSystem> list=new ArrayList<>();
		if(StringUtils.isNotBlank(id)){
			ResourceType resourceType = resourceTypeService.get(id);
			if(resourceType!=null&&resourceType.getParent().getCode().equals("2")){
				OperatingSystem operatingSystem=new OperatingSystem();
				operatingSystem.setServerId(id);
				list=operatingSystemService.findList(operatingSystem);
			}
		}
		return list;
	}


	/**
	 * 保存操作系统
	 */
	@RequiresPermissions(value={"operatingsystem:operatingSystem:add","operatingsystem:operatingSystem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OperatingSystem operatingSystem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, operatingSystem)){
			return form(operatingSystem, model);
		}
		//新增或编辑表单保存
		operatingSystemService.save(operatingSystem);//保存
		addMessage(redirectAttributes, "保存操作系统成功");
		return "redirect:"+Global.getAdminPath()+"/operatingsystem/operatingSystem/?repage";
	}
	
	/**
	 * 删除操作系统
	 */
	@ResponseBody
	@RequiresPermissions("operatingsystem:operatingSystem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OperatingSystem operatingSystem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		operatingSystemService.delete(operatingSystem);
		j.setMsg("删除操作系统成功");
		return j;
	}
	
	/**
	 * 批量删除操作系统
	 */
	@ResponseBody
	@RequiresPermissions("operatingsystem:operatingSystem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			operatingSystemService.delete(operatingSystemService.get(id));
		}
		j.setMsg("删除操作系统成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("operatingsystem:operatingSystem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OperatingSystem operatingSystem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "操作系统"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OperatingSystem> page = operatingSystemService.findPage(new Page<OperatingSystem>(request, response, -1), operatingSystem);
    		new ExportExcel("操作系统", OperatingSystem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出操作系统记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("operatingsystem:operatingSystem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OperatingSystem> list = ei.getDataList(OperatingSystem.class);
			for (OperatingSystem operatingSystem : list){
				try{
					operatingSystemService.save(operatingSystem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条操作系统记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条操作系统记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入操作系统失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/operatingsystem/operatingSystem/?repage";
    }
	
	/**
	 * 下载导入操作系统数据模板
	 */
	@RequiresPermissions("operatingsystem:operatingSystem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "操作系统数据导入模板.xlsx";
    		List<OperatingSystem> list = Lists.newArrayList(); 
    		new ExportExcel("操作系统数据", OperatingSystem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/operatingsystem/operatingSystem/?repage";
    }

}