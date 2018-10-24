/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.linkindicator.web;

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
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;

/**
 * 链路指标Controller
 * @author le
 * @version 2017-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/linkindicator/linkIndicator")
public class LinkIndicatorController extends BaseController {

	@Autowired
	private LinkIndicatorService linkIndicatorService;
	
	@ModelAttribute
	public LinkIndicator get(@RequestParam(required=false) String id) {
		LinkIndicator entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linkIndicatorService.get(id);
		}
		if (entity == null){
			entity = new LinkIndicator();
		}
		return entity;
	}
	
	/**
	 * 链路指标列表页面
	 */
	@RequiresPermissions("linkindicator:linkIndicator:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/linkindicator/linkIndicatorList";
	}
	
		/**
	 * 链路指标列表数据
	 */
	@ResponseBody
	@RequiresPermissions("linkindicator:linkIndicator:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinkIndicator linkIndicator, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinkIndicator> page = linkIndicatorService.findPage(new Page<LinkIndicator>(request, response), linkIndicator); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑链路指标表单页面
	 */
	@RequiresPermissions(value={"linkindicator:linkIndicator:view","linkindicator:linkIndicator:add","linkindicator:linkIndicator:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinkIndicator linkIndicator, Model model) {
		model.addAttribute("linkIndicator", linkIndicator);
		if(StringUtils.isBlank(linkIndicator.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/linkindicator/linkIndicatorForm";
	}

	/**
	 * 保存链路指标
	 */
	@RequiresPermissions(value={"linkindicator:linkIndicator:add","linkindicator:linkIndicator:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LinkIndicator linkIndicator, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, linkIndicator)){
			return form(linkIndicator, model);
		}
		//新增或编辑表单保存
		linkIndicatorService.save(linkIndicator);//保存
		addMessage(redirectAttributes, "保存链路指标成功");
		return "redirect:"+Global.getAdminPath()+"/linkindicator/linkIndicator/?repage";
	}
	
	/**
	 * 删除链路指标
	 */
	@ResponseBody
	@RequiresPermissions("linkindicator:linkIndicator:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinkIndicator linkIndicator, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linkIndicatorService.delete(linkIndicator);
		j.setMsg("删除链路指标成功");
		return j;
	}
	
	/**
	 * 批量删除链路指标
	 */
	@ResponseBody
	@RequiresPermissions("linkindicator:linkIndicator:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linkIndicatorService.delete(linkIndicatorService.get(id));
		}
		j.setMsg("删除链路指标成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("linkindicator:linkIndicator:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinkIndicator linkIndicator, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "链路指标"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinkIndicator> page = linkIndicatorService.findPage(new Page<LinkIndicator>(request, response, -1), linkIndicator);
    		new ExportExcel("链路指标", LinkIndicator.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出链路指标记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("linkindicator:linkIndicator:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinkIndicator> list = ei.getDataList(LinkIndicator.class);
			for (LinkIndicator linkIndicator : list){
				try{
					linkIndicatorService.save(linkIndicator);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条链路指标记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条链路指标记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入链路指标失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/linkindicator/linkIndicator/?repage";
    }
	
	/**
	 * 下载导入链路指标数据模板
	 */
	@RequiresPermissions("linkindicator:linkIndicator:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "链路指标数据导入模板.xlsx";
    		List<LinkIndicator> list = Lists.newArrayList(); 
    		new ExportExcel("链路指标数据", LinkIndicator.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/linkindicator/linkIndicator/?repage";
    }

}