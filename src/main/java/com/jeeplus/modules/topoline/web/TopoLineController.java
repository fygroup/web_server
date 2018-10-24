/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.topoline.web;

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
import com.jeeplus.modules.topoline.entity.TopoLine;
import com.jeeplus.modules.topoline.service.TopoLineService;

/**
 * 拓扑图线条位置信息Controller
 * @author sun
 * @version 2017-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/topoline/topoLine")
public class TopoLineController extends BaseController {

	@Autowired
	private TopoLineService topoLineService;
	
	@ModelAttribute
	public TopoLine get(@RequestParam(required=false) String id) {
		TopoLine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = topoLineService.get(id);
		}
		if (entity == null){
			entity = new TopoLine();
		}
		return entity;
	}
	
	/**
	 * 拓扑图线条位置信息列表页面
	 */
	@RequiresPermissions("topoline:topoLine:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/topoline/topoLineList";
	}
	
		/**
	 * 拓扑图线条位置信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("topoline:topoLine:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TopoLine topoLine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TopoLine> page = topoLineService.findPage(new Page<TopoLine>(request, response), topoLine); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑拓扑图线条位置信息表单页面
	 */
	@RequiresPermissions(value={"topoline:topoLine:view","topoline:topoLine:add","topoline:topoLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TopoLine topoLine, Model model) {
		model.addAttribute("topoLine", topoLine);
		if(StringUtils.isBlank(topoLine.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/topoline/topoLineForm";
	}

	/**
	 * 保存拓扑图线条位置信息
	 */
	@RequiresPermissions(value={"topoline:topoLine:add","topoline:topoLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TopoLine topoLine, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, topoLine)){
			return form(topoLine, model);
		}
		//新增或编辑表单保存
		topoLineService.save(topoLine);//保存
		addMessage(redirectAttributes, "保存拓扑图线条位置信息成功");
		return "redirect:"+Global.getAdminPath()+"/topoline/topoLine/?repage";
	}
	
	/**
	 * 删除拓扑图线条位置信息
	 */
	@ResponseBody
	@RequiresPermissions("topoline:topoLine:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TopoLine topoLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		topoLineService.delete(topoLine);
		j.setMsg("删除拓扑图线条位置信息成功");
		return j;
	}
	
	/**
	 * 批量删除拓扑图线条位置信息
	 */
	@ResponseBody
	@RequiresPermissions("topoline:topoLine:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			topoLineService.delete(topoLineService.get(id));
		}
		j.setMsg("删除拓扑图线条位置信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("topoline:topoLine:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TopoLine topoLine, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "拓扑图线条位置信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TopoLine> page = topoLineService.findPage(new Page<TopoLine>(request, response, -1), topoLine);
    		new ExportExcel("拓扑图线条位置信息", TopoLine.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出拓扑图线条位置信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("topoline:topoLine:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TopoLine> list = ei.getDataList(TopoLine.class);
			for (TopoLine topoLine : list){
				try{
					topoLineService.save(topoLine);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条拓扑图线条位置信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条拓扑图线条位置信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入拓扑图线条位置信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/topoline/topoLine/?repage";
    }
	
	/**
	 * 下载导入拓扑图线条位置信息数据模板
	 */
	@RequiresPermissions("topoline:topoLine:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "拓扑图线条位置信息数据导入模板.xlsx";
    		List<TopoLine> list = Lists.newArrayList(); 
    		new ExportExcel("拓扑图线条位置信息数据", TopoLine.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/topoline/topoLine/?repage";
    }

}