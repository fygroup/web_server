/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.indextemplate.web;

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
import com.jeeplus.modules.indextemplate.entity.IndexTemplate;
import com.jeeplus.modules.indextemplate.service.IndexTemplateService;

/**
 * 指标模板Controller
 * @author le
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/indextemplate/indexTemplate")
public class IndexTemplateController extends BaseController {

	@Autowired
	private IndexTemplateService indexTemplateService;
	
	@ModelAttribute
	public IndexTemplate get(@RequestParam(required=false) String id) {
		IndexTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = indexTemplateService.get(id);
		}
		if (entity == null){
			entity = new IndexTemplate();
		}
		return entity;
	}
	
	/**
	 * 指标模板列表页面
	 */
	@RequiresPermissions("indextemplate:indexTemplate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/indextemplate/indexTemplateList";
	}
	
		/**
	 * 指标模板列表数据
	 */
	@ResponseBody
	@RequiresPermissions("indextemplate:indexTemplate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(IndexTemplate indexTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IndexTemplate> page = indexTemplateService.findPage(new Page<IndexTemplate>(request, response), indexTemplate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑指标模板表单页面
	 */
	@RequiresPermissions(value={"indextemplate:indexTemplate:view","indextemplate:indexTemplate:add","indextemplate:indexTemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(IndexTemplate indexTemplate, Model model) {
		model.addAttribute("indexTemplate", indexTemplate);
		if(StringUtils.isBlank(indexTemplate.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/indextemplate/indexTemplateForm";
	}

	/**
	 * 保存指标模板
	 */
	@RequiresPermissions(value={"indextemplate:indexTemplate:add","indextemplate:indexTemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(IndexTemplate indexTemplate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, indexTemplate)){
			return form(indexTemplate, model);
		}
		//新增或编辑表单保存
		indexTemplateService.save(indexTemplate);//保存
		addMessage(redirectAttributes, "保存指标模板成功");
		return "redirect:"+Global.getAdminPath()+"/indextemplate/indexTemplate/?repage";
	}
	
	/**
	 * 删除指标模板
	 */
	@ResponseBody
	@RequiresPermissions("indextemplate:indexTemplate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(IndexTemplate indexTemplate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		indexTemplateService.delete(indexTemplate);
		j.setMsg("删除指标模板成功");
		return j;
	}
	
	/**
	 * 批量删除指标模板
	 */
	@ResponseBody
	@RequiresPermissions("indextemplate:indexTemplate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			indexTemplateService.delete(indexTemplateService.get(id));
		}
		j.setMsg("删除指标模板成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("indextemplate:indexTemplate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(IndexTemplate indexTemplate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "指标模板"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<IndexTemplate> page = indexTemplateService.findPage(new Page<IndexTemplate>(request, response, -1), indexTemplate);
    		new ExportExcel("指标模板", IndexTemplate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出指标模板记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("indextemplate:indexTemplate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<IndexTemplate> list = ei.getDataList(IndexTemplate.class);
			for (IndexTemplate indexTemplate : list){
				try{
					indexTemplateService.save(indexTemplate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条指标模板记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条指标模板记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入指标模板失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/indextemplate/indexTemplate/?repage";
    }
	
	/**
	 * 下载导入指标模板数据模板
	 */
	@RequiresPermissions("indextemplate:indexTemplate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "指标模板数据导入模板.xlsx";
    		List<IndexTemplate> list = Lists.newArrayList(); 
    		new ExportExcel("指标模板数据", IndexTemplate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/indextemplate/indexTemplate/?repage";
    }

}