/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcebaseinfo.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
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
import com.jeeplus.modules.resourcebaseinfo.service.ResourceBaseInfoService;

/**
 * 资源访问信息Controller
 * @author le
 * @version 2017-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/resourcebaseinfo/resourceBaseInfo")
public class ResourceBaseInfoController extends BaseController {

	@Autowired
	private ResourceBaseInfoService resourceBaseInfoService;
	
	@ModelAttribute
	public ResourceBaseInfo get(@RequestParam(required=false) String id) {
		ResourceBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new ResourceBaseInfo();
		}
		return entity;
	}
	
	/**
	 * 资源访问信息列表页面
	 */
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/resourcebaseinfo/resourceBaseInfoList";
	}
	
		/**
	 * 资源访问信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourceBaseInfo resourceBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceBaseInfo> page = resourceBaseInfoService.findPage(new Page<ResourceBaseInfo>(request, response), resourceBaseInfo);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资源访问信息表单页面
	 */
	@RequiresPermissions(value={"resourcebaseinfo:resourceBaseInfo:view","resourcebaseinfo:resourceBaseInfo:add","resourcebaseinfo:resourceBaseInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceBaseInfo resourceBaseInfo, Model model) {
		model.addAttribute("resourceBaseInfo", resourceBaseInfo);
		if(StringUtils.isBlank(resourceBaseInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/resourcebaseinfo/resourceBaseInfoForm";
	}

	/**
	 * 保存资源访问信息
	 */
	@RequiresPermissions(value={"resourcebaseinfo:resourceBaseInfo:add","resourcebaseinfo:resourceBaseInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceBaseInfo resourceBaseInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceBaseInfo)){
			return form(resourceBaseInfo, model);
		}
		//新增或编辑表单保存
		resourceBaseInfoService.save(resourceBaseInfo);//保存
		addMessage(redirectAttributes, "保存资源访问信息成功");
		return "redirect:"+Global.getAdminPath()+"/resourcebaseinfo/resourceBaseInfo/?repage";
	}
	
	/**
	 * 删除资源访问信息
	 */
	@ResponseBody
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceBaseInfo resourceBaseInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceBaseInfoService.delete(resourceBaseInfo);
		j.setMsg("删除资源访问信息成功");
		return j;
	}
	
	/**
	 * 批量删除资源访问信息
	 */
	@ResponseBody
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceBaseInfoService.delete(resourceBaseInfoService.get(id));
		}
		j.setMsg("删除资源访问信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourceBaseInfo resourceBaseInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资源访问信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceBaseInfo> page = resourceBaseInfoService.findPage(new Page<ResourceBaseInfo>(request, response, -1), resourceBaseInfo);
    		new ExportExcel("资源访问信息", ResourceBaseInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资源访问信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceBaseInfo> list = ei.getDataList(ResourceBaseInfo.class);
			for (ResourceBaseInfo resourceBaseInfo : list){
				try{
					resourceBaseInfoService.save(resourceBaseInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源访问信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源访问信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源访问信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcebaseinfo/resourceBaseInfo/?repage";
    }
	
	/**
	 * 下载导入资源访问信息数据模板
	 */
	@RequiresPermissions("resourcebaseinfo:resourceBaseInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源访问信息数据导入模板.xlsx";
    		List<ResourceBaseInfo> list = Lists.newArrayList();
    		new ExportExcel("资源访问信息数据", ResourceBaseInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcebaseinfo/resourceBaseInfo/?repage";
    }

}