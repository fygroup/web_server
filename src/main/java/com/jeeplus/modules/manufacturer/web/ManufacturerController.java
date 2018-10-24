/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.manufacturer.web;

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
import com.jeeplus.modules.manufacturer.entity.Manufacturer;
import com.jeeplus.modules.manufacturer.service.ManufacturerService;

/**
 * 厂商信息Controller
 * @author le
 * @version 2017-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/manufacturer/manufacturer")
public class ManufacturerController extends BaseController {

	@Autowired
	private ManufacturerService manufacturerService;
	
	@ModelAttribute
	public Manufacturer get(@RequestParam(required=false) String id) {
		Manufacturer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = manufacturerService.get(id);
		}
		if (entity == null){
			entity = new Manufacturer();
		}
		return entity;
	}
	
	/**
	 * 厂商信息列表页面
	 */
	@RequiresPermissions("manufacturer:manufacturer:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/manufacturer/manufacturerList";
	}
	
		/**
	 * 厂商信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("manufacturer:manufacturer:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Manufacturer manufacturer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Manufacturer> page = manufacturerService.findPage(new Page<Manufacturer>(request, response), manufacturer); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑厂商信息表单页面
	 */
	@RequiresPermissions(value={"manufacturer:manufacturer:view","manufacturer:manufacturer:add","manufacturer:manufacturer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Manufacturer manufacturer, Model model) {
		model.addAttribute("manufacturer", manufacturer);
		if(StringUtils.isBlank(manufacturer.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/manufacturer/manufacturerForm";
	}

	/**
	 * 保存厂商信息
	 */
	@RequiresPermissions(value={"manufacturer:manufacturer:add","manufacturer:manufacturer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Manufacturer manufacturer, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, manufacturer)){
			return form(manufacturer, model);
		}
		//新增或编辑表单保存
		manufacturerService.save(manufacturer);//保存
		addMessage(redirectAttributes, "保存厂商信息成功");
		return "redirect:"+Global.getAdminPath()+"/manufacturer/manufacturer/?repage";
	}
	
	/**
	 * 删除厂商信息
	 */
	@ResponseBody
	@RequiresPermissions("manufacturer:manufacturer:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Manufacturer manufacturer, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		manufacturerService.delete(manufacturer);
		j.setMsg("删除厂商信息成功");
		return j;
	}
	
	/**
	 * 批量删除厂商信息
	 */
	@ResponseBody
	@RequiresPermissions("manufacturer:manufacturer:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			manufacturerService.delete(manufacturerService.get(id));
		}
		j.setMsg("删除厂商信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("manufacturer:manufacturer:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Manufacturer manufacturer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "厂商信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Manufacturer> page = manufacturerService.findPage(new Page<Manufacturer>(request, response, -1), manufacturer);
    		new ExportExcel("厂商信息", Manufacturer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出厂商信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("manufacturer:manufacturer:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Manufacturer> list = ei.getDataList(Manufacturer.class);
			for (Manufacturer manufacturer : list){
				try{
					manufacturerService.save(manufacturer);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条厂商信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条厂商信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入厂商信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/manufacturer/manufacturer/?repage";
    }
	
	/**
	 * 下载导入厂商信息数据模板
	 */
	@RequiresPermissions("manufacturer:manufacturer:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "厂商信息数据导入模板.xlsx";
    		List<Manufacturer> list = Lists.newArrayList(); 
    		new ExportExcel("厂商信息数据", Manufacturer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/manufacturer/manufacturer/?repage";
    }

}