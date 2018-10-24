/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.process.web;

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
import com.jeeplus.modules.process.entity.Process;
import com.jeeplus.modules.process.service.ProcessService;

/**
 * 进程Controller
 * @author le
 * @version 2017-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/process/process")
public class ProcessController extends BaseController {

	@Autowired
	private ProcessService processService;
	
	@ModelAttribute
	public Process get(@RequestParam(required=false) String id) {
		Process entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = processService.get(id);
		}
		if (entity == null){
			entity = new Process();
		}
		return entity;
	}
	
	/**
	 * 进程列表页面
	 */
	@RequiresPermissions("process:process:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/processList";
	}
	
		/**
	 * 进程列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:process:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Process process, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Process> page = processService.findPage(new Page<Process>(request, response), process); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑进程表单页面
	 */
	@RequiresPermissions(value={"process:process:view","process:process:add","process:process:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Process process, Model model) {
		model.addAttribute("process", process);
		if(StringUtils.isBlank(process.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/processForm";
	}

	/**
	 * 保存进程
	 */
	@RequiresPermissions(value={"process:process:add","process:process:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Process process, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, process)){
			return form(process, model);
		}
		//新增或编辑表单保存
		processService.save(process);//保存
		addMessage(redirectAttributes, "保存进程成功");
		return "redirect:"+Global.getAdminPath()+"/process/process/?repage";
	}
	
	/**
	 * 删除进程
	 */
	@ResponseBody
	@RequiresPermissions("process:process:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Process process, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		processService.delete(process);
		j.setMsg("删除进程成功");
		return j;
	}
	
	/**
	 * 批量删除进程
	 */
	@ResponseBody
	@RequiresPermissions("process:process:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			processService.delete(processService.get(id));
		}
		j.setMsg("删除进程成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:process:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Process process, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "进程"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Process> page = processService.findPage(new Page<Process>(request, response, -1), process);
    		new ExportExcel("进程", Process.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出进程记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:process:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Process> list = ei.getDataList(Process.class);
			for (Process process : list){
				try{
					processService.save(process);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条进程记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条进程记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入进程失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/process/?repage";
    }
	
	/**
	 * 下载导入进程数据模板
	 */
	@RequiresPermissions("process:process:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "进程数据导入模板.xlsx";
    		List<Process> list = Lists.newArrayList(); 
    		new ExportExcel("进程数据", Process.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/process/?repage";
    }

}