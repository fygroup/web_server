/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.toposymbols.web;

import java.util.*;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.topoline.service.TopoLineService;
import com.jeeplus.modules.topoview.entity.TopoView;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
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
import com.jeeplus.modules.toposymbols.entity.TopoSymbols;
import com.jeeplus.modules.toposymbols.service.TopoSymbolsService;

/**
 * 拓扑图资源UI信息Controller
 * @author sun
 * @version 2017-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/toposymbols/topoSymbols")
public class TopoSymbolsController extends BaseController {

	@Autowired
	private TopoSymbolsService topoSymbolsService;

	@Autowired
	private TopoLineService topoLineService;

	@ModelAttribute
	public TopoSymbols get(@RequestParam(required=false) String id) {
		TopoSymbols entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = topoSymbolsService.get(id);
		}
		if (entity == null){
			entity = new TopoSymbols();
		}
		return entity;
	}
	
	/**
	 * 拓扑图资源UI信息列表页面
	 */
	@RequiresPermissions("toposymbols:topoSymbols:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/toposymbols/topoSymbolsList";
	}
	
		/**
	 * 拓扑图资源UI信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("toposymbols:topoSymbols:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TopoSymbols topoSymbols, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TopoSymbols> page = topoSymbolsService.findPage(new Page<TopoSymbols>(request, response), topoSymbols); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑拓扑图资源UI信息表单页面
	 */
	@RequiresPermissions(value={"toposymbols:topoSymbols:view","toposymbols:topoSymbols:add","toposymbols:topoSymbols:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TopoSymbols topoSymbols, Model model) {
		model.addAttribute("topoSymbols", topoSymbols);
		if(StringUtils.isBlank(topoSymbols.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/toposymbols/topoSymbolsForm";
	}

	/**
	 * 保存拓扑图资源UI信息
	 */
	@RequiresPermissions(value={"toposymbols:topoSymbols:add","toposymbols:topoSymbols:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TopoSymbols topoSymbols, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, topoSymbols)){
			return form(topoSymbols, model);
		}
		//新增或编辑表单保存
		topoSymbolsService.save(topoSymbols);//保存
		addMessage(redirectAttributes, "保存拓扑图资源信息成功");
		return "redirect:"+Global.getAdminPath()+"/toposymbols/topoSymbols/?repage";
	}



	@ResponseBody
	@RequestMapping(value = "saveList")
	public AjaxJson saveList(String addSymbolsString,String addLineString){
		AjaxJson ajaxJson=new AjaxJson();
        topoSymbolsService.saveSymbolsAndLines(addSymbolsString,addLineString);
		return ajaxJson;
	}



	/**
	 * 删除拓扑图资源UI信息
	 */
	@ResponseBody
	@RequiresPermissions("toposymbols:topoSymbols:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TopoSymbols topoSymbols, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		topoSymbolsService.delete(topoSymbols);
		j.setMsg("删除拓扑图资源UI信息成功");
		return j;
	}
	
	/**
	 * 批量删除拓扑图资源UI信息
	 */
	@ResponseBody
	@RequiresPermissions("toposymbols:topoSymbols:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			topoSymbolsService.delete(topoSymbolsService.get(id));
		}
		j.setMsg("删除拓扑图资源UI信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("toposymbols:topoSymbols:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TopoSymbols topoSymbols, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "拓扑图资源UI信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TopoSymbols> page = topoSymbolsService.findPage(new Page<TopoSymbols>(request, response, -1), topoSymbols);
    		new ExportExcel("拓扑图资源UI信息", TopoSymbols.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出拓扑图资源UI信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("toposymbols:topoSymbols:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TopoSymbols> list = ei.getDataList(TopoSymbols.class);
			for (TopoSymbols topoSymbols : list){
				try{
					topoSymbolsService.save(topoSymbols);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条拓扑图资源UI信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条拓扑图资源UI信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入拓扑图资源UI信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/toposymbols/topoSymbols/?repage";
    }
	
	/**
	 * 下载导入拓扑图资源UI信息数据模板
	 */
	@RequiresPermissions("toposymbols:topoSymbols:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "拓扑图资源UI信息数据导入模板.xlsx";
    		List<TopoSymbols> list = Lists.newArrayList(); 
    		new ExportExcel("拓扑图资源UI信息数据", TopoSymbols.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/toposymbols/topoSymbols/?repage";
    }

}