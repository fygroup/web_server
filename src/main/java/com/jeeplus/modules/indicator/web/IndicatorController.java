
package com.jeeplus.modules.indicator.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.operatingsystem.service.OperatingSystemService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
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
import com.jeeplus.modules.indicator.service.IndicatorService;

/**
 * 指标列表Controller
 * @author le
 * @version 2017-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/indicator/indicator")
public class IndicatorController extends BaseController {

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private OperatingSystemService operatingSystemService;


	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;
	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceTypeService resourceTypeService;


	
	@ModelAttribute
	public Indicator get(@RequestParam(required=false) String id) {
		Indicator entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = indicatorService.get(id);
		}
		if (entity == null){
			entity = new Indicator();
		}
		return entity;
	}
	
	/**
	 * 指标列表列表页面
	 */
	@RequiresPermissions("indicator:indicator:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/indicator/indicatorList";
	}
	
		/**
	 * 指标列表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("indicator:indicator:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Indicator indicator, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Indicator> page = indicatorService.findPage(new Page<Indicator>(request, response), indicator);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑指标列表表单页面
	 */
	@RequiresPermissions(value={"indicator:indicator:view","indicator:indicator:add","indicator:indicator:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Indicator indicator, Model model) {
		model.addAttribute("indicatorList", indicator);
		if(StringUtils.isBlank(indicator.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}else{
			model.addAttribute("operatingSystemList", operatingSystemService.findListByServerId(indicator.getResourceTypeId()));
		}
		return "modules/indicator/indicatorForm";
	}



	/**
	 * 保存指标列表
	 */
	@RequiresPermissions(value={"indicator:indicator:add","indicator:indicator:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Indicator indicator, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, indicator)){
			return form(indicator, model);
		}
		indicator.setUnit(indicator.getUnit()==null?"":indicator.getUnit());
		//新增或编辑表单保存
		indicatorService.save(indicator);//保存
		addMessage(redirectAttributes, "保存指标列表成功");
		return "redirect:"+Global.getAdminPath()+"/indicator/indicator/?repage";
	}
	
	/**
	 * 删除指标列表
	 */
	@ResponseBody
	@RequiresPermissions("indicator:indicator:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Indicator indicator, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		indicatorService.delete(indicator);
		j.setMsg("删除指标列表成功");
		return j;
	}
	
	/**
	 * 批量删除指标列表
	 */
	@ResponseBody
	@RequiresPermissions("indicator:indicator:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			indicatorService.delete(indicatorService.get(id));
		}
		j.setMsg("删除指标列表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("indicator:indicator:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Indicator indicator, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "指标列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Indicator> page = indicatorService.findPage(new Page<Indicator>(request, response, -1), indicator);
    		new ExportExcel("指标列表", Indicator.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出指标列表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("indicator:indicator:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Indicator> list = ei.getDataList(Indicator.class);
			for (Indicator indicator : list){
				try{
					indicatorService.save(indicator);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条指标列表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条指标列表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入指标列表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/indicator/indicator/?repage";
    }
	
	/**
	 * 下载导入指标列表数据模板
	 */
	@RequiresPermissions("indicator:indicator:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "指标列表数据导入模板.xlsx";
    		List<Indicator> list = Lists.newArrayList();
    		new ExportExcel("指标列表数据", Indicator.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/indicator/indicator/?repage";
    }




	@RequestMapping(value = "showIndicatorList")
	public String showIndicatorList( Model model,String resourceId) throws Exception{
		/*List<ResourceIndicatorlist> resourceIndicatorlist= resourceIndicatorlistService.findListByResourceId(resourceId);
		model.addAttribute("resourceIndicatorlist", resourceIndicatorlist);*/
		Resource resource=resourceService.get(resourceId);
		ResourceType resourceType=resource.getResourceType();
		resourceType.setParent(resourceTypeService.getParentByChild(resourceType.getId()));
		resource.setResourceType(resourceType);
		model.addAttribute("resource", resource);
		return "modules/resourceindicatorlist/editResourceIndicatorlist";
	}


	@ResponseBody
	@RequestMapping(value = "initList")
	public List<ResourceIndicatorlist>  initList(String resourceId) {
		return resourceIndicatorlistService.findListByResourceId(resourceId);
	}




	@ResponseBody
	@RequestMapping(value = "addIndicator")
	public List<Indicator>  addIndicator(String resourceId, String type) {
		List<Indicator> list=new ArrayList<>();
		Resource resource=null;
		if(StringUtils.isNotBlank(resourceId)&&StringUtils.isNotBlank(type)){
			List<String> selectedIds=resourceIndicatorlistService.findIdsByResource(resourceId);
			resource=resourceService.get(resourceId);
			Indicator indicator =new Indicator();
			indicator.setResourceTypeId(resource.getResourceType().getId());
			indicator.setEventType(type);
			list= indicatorService.findList(indicator);
			if(CheckObject.checkList(selectedIds)&&CheckObject.checkList(list)){
				for(Indicator item :list){
					if(selectedIds.contains(item.getId())){
						item.setSelected(true);
					}
				}
			}
		}
		return list;
	}

}