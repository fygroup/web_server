/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.software.web;

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
import com.jeeplus.modules.software.entity.Software;
import com.jeeplus.modules.software.service.SoftwareService;

/**
 * 软件列表Controller
 * @author le
 * @version 2017-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/software/software")
public class SoftwareController extends BaseController {

	@Autowired
	private SoftwareService softwareService;
	
	@ModelAttribute
	public Software get(@RequestParam(required=false) String id) {
		Software entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = softwareService.get(id);
		}
		if (entity == null){
			entity = new Software();
		}
		return entity;
	}
	
	/**
	 * 软件列表列表页面
	 */
	@RequiresPermissions("software:software:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/software/softwareList";
	}
	
		/**
	 * 软件列表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("software:software:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Software software, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Software> page = softwareService.findPage(new Page<Software>(request, response), software); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑软件列表表单页面
	 */
	@RequiresPermissions(value={"software:software:view","software:software:add","software:software:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Software software, Model model) {
		model.addAttribute("software", software);
		if(StringUtils.isBlank(software.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/software/softwareForm";
	}

	/**
	 * 保存软件列表
	 */
	@RequiresPermissions(value={"software:software:add","software:software:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Software software, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, software)){
			return form(software, model);
		}
		//新增或编辑表单保存
		softwareService.save(software);//保存
		addMessage(redirectAttributes, "保存软件列表成功");
		return "redirect:"+Global.getAdminPath()+"/software/software/?repage";
	}
	
	/**
	 * 删除软件列表
	 */
	@ResponseBody
	@RequiresPermissions("software:software:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Software software, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		softwareService.delete(software);
		j.setMsg("删除软件列表成功");
		return j;
	}
	
	/**
	 * 批量删除软件列表
	 */
	@ResponseBody
	@RequiresPermissions("software:software:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			softwareService.delete(softwareService.get(id));
		}
		j.setMsg("删除软件列表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("software:software:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Software software, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "软件列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Software> page = softwareService.findPage(new Page<Software>(request, response, -1), software);
    		new ExportExcel("软件列表", Software.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出软件列表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("software:software:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Software> list = ei.getDataList(Software.class);
			for (Software software : list){
				try{
					softwareService.save(software);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条软件列表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条软件列表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入软件列表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/software/software/?repage";
    }
	
	/**
	 * 下载导入软件列表数据模板
	 */
	@RequiresPermissions("software:software:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "软件列表数据导入模板.xlsx";
    		List<Software> list = Lists.newArrayList(); 
    		new ExportExcel("软件列表数据", Software.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/software/software/?repage";
    }

}