/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memory.web;

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
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.service.MemoryService;

/**
 * 内存Controller
 * @author le
 * @version 2017-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/memory/memory")
public class MemoryController extends BaseController {

	@Autowired
	private MemoryService memoryService;
	
	@ModelAttribute
	public Memory get(@RequestParam(required=false) String id) {
		Memory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memoryService.get(id);
		}
		if (entity == null){
			entity = new Memory();
		}
		return entity;
	}
	
	/**
	 * 内存列表页面
	 */
	@RequiresPermissions("memory:memory:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/memory/memoryList";
	}
	
		/**
	 * 内存列表数据
	 */
	@ResponseBody
	@RequiresPermissions("memory:memory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Memory memory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Memory> page = memoryService.findPage(new Page<Memory>(request, response), memory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑内存表单页面
	 */
	@RequiresPermissions(value={"memory:memory:view","memory:memory:add","memory:memory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Memory memory, Model model) {
		model.addAttribute("memory", memory);
		if(StringUtils.isBlank(memory.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/memory/memoryForm";
	}

	/**
	 * 保存内存
	 */
	@RequiresPermissions(value={"memory:memory:add","memory:memory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Memory memory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memory)){
			return form(memory, model);
		}
		//新增或编辑表单保存
		memoryService.save(memory);//保存
		addMessage(redirectAttributes, "保存内存成功");
		return "redirect:"+Global.getAdminPath()+"/memory/memory/?repage";
	}
	
	/**
	 * 删除内存
	 */
	@ResponseBody
	@RequiresPermissions("memory:memory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Memory memory, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memoryService.delete(memory);
		j.setMsg("删除内存成功");
		return j;
	}
	
	/**
	 * 批量删除内存
	 */
	@ResponseBody
	@RequiresPermissions("memory:memory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memoryService.delete(memoryService.get(id));
		}
		j.setMsg("删除内存成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("memory:memory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Memory memory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "内存"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Memory> page = memoryService.findPage(new Page<Memory>(request, response, -1), memory);
    		new ExportExcel("内存", Memory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出内存记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("memory:memory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Memory> list = ei.getDataList(Memory.class);
			for (Memory memory : list){
				try{
					memoryService.save(memory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条内存记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条内存记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入内存失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memory/memory/?repage";
    }
	
	/**
	 * 下载导入内存数据模板
	 */
	@RequiresPermissions("memory:memory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "内存数据导入模板.xlsx";
    		List<Memory> list = Lists.newArrayList(); 
    		new ExportExcel("内存数据", Memory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memory/memory/?repage";
    }

}