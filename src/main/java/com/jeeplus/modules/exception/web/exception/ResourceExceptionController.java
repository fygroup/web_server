/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.exception.web.exception;


import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.commons.collections.map.HashedMap;
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
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;

/**
 * 异常告警信息Controller
 * @author clouology
 * @version 2017-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/exception/exception/resourceException")
public class ResourceExceptionController extends BaseController {

	@Autowired
	private ResourceExceptionService resourceExceptionService;

	@Autowired
	private AreaService areaService;

	
	@ModelAttribute
	public ResourceException get(@RequestParam(required=false) String id) {
		ResourceException entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceExceptionService.get(id);
		}
		if (entity == null){
			entity = new ResourceException();
		}
		return entity;
	}
	
	/**
	 * 异常告警列表页面
	 */
	@RequiresPermissions("exception:exception:resourceException:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/exception/exception/resourceExceptionList";
	}
	
		/**
	 * 异常告警列表数据
	 */
	@ResponseBody
	@RequiresPermissions("exception:exception:resourceException:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResourceException resourceException, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceException> page = resourceExceptionService.findPage(new Page<ResourceException>(request, response), resourceException); 
		return getBootstrapData(page);
	}

	/**
	 * 我的面板异常告警列表数据
	 */
	@ResponseBody
	@RequiresPermissions("exception:exception:resourceException:list")
	@RequestMapping(value = "exceptionList")
	public List<Map<String,String>> exceptionList() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,String>> result=new ArrayList<>();
		List<ResourceException> list=resourceExceptionService.indexFindList();
		if(CheckObject.checkList(list)){
			for(ResourceException e:list){
				Map<String,String> map=new HashedMap();
				map.put("id",e.getId());
				map.put("ename",e.getIndicatorName());
				map.put("efrom",e.getExceptionSource());
				map.put("etotalNum",e.getTotalQuantity());
				map.put("eclass", DictUtils.getDictLabels(e.getExceptionClass(),"exception_class", "-"));
				map.put("evalue",e.getCurrentValue()+e.getIndicatorItem().getUnit());
				map.put("estatus",DictUtils.getDictLabels(e.getCurrentStatus(),"exception_current_status", "-"));
				map.put("efirstTimeValue",e.getFirstTriggerValue()+e.getIndicatorItem().getUnit());
				map.put("eindicator",e.getIndicatorItem().getName());
				map.put("resourceType",e.getResourceType().getName());
				map.put("elastTime",sdf.format(e.getLastTriggerTime()));
				map.put("event",DictUtils.getDictLabels(e.getEventType(),"indicator_event_type", "-"));
				map.put("econfirmStatus",DictUtils.getDictLabels(e.getConfirmStatus(),"exception_confirm_status", "-"));
				map.put("remarks",e.getRemarks());
				result.add(map);
			}

		}
		return result;
	}

	/**
	 * 查看，增加，编辑异常告警表单页面
	 */
	@RequiresPermissions(value={"exception:exception:resourceException:view","exception:exception:resourceException:add","exception:exception:resourceException:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceException resourceException, Model model) {
		model.addAttribute("resourceException", resourceException);
		if(StringUtils.isBlank(resourceException.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		if(resourceException!=null&&resourceException.getOffice()!=null&&StringUtils.isNotBlank(resourceException.getOffice().getId())){
			resourceException.setArea(areaService.getByOffice(resourceException.getOffice().getId()));
		}
		model.addAttribute("resourceException", resourceException);
		return "modules/exception/exception/resourceExceptionForm";
	}


	/**
	 * 手动恢复
	 */
	@RequiresPermissions(value={"exception:exception:resourceException:view","exception:exception:resourceException:add","exception:exception:resourceException:edit"},logical=Logical.OR)
	@RequestMapping(value = "manualRecovery")
	public String manualRecovery(ResourceException resourceException) {
		if(resourceException!=null&&"0".equals(resourceException.getCurrentStatus())){ //未恢复
			resourceExceptionService.manualRecovery(resourceException.getId(),new Date(),"1","1", UserUtils.getUser().getId());
		}
		return "redirect:"+Global.getAdminPath()+"/exception/exception/resourceException/?repage";
	}
	/**
	 * 我的面板手动恢复
	 */
	@RequiresPermissions(value={"exception:exception:resourceException:view","exception:exception:resourceException:add","exception:exception:resourceException:edit"},logical=Logical.OR)
	@RequestMapping(value = "indexManualRecovery")
	@ResponseBody
	public String indexManualRecovery(ResourceException resourceException) {
		if(resourceException!=null&&"0".equals(resourceException.getCurrentStatus())){ //未恢复
			resourceExceptionService.indexManualRecovery(resourceException.getId(),new Date(),"1","1", UserUtils.getUser().getId());
		}
		return "1";
	}



	/**
	 * 确认异常
	 */
	@RequiresPermissions(value={"exception:exception:resourceException:view","exception:exception:resourceException:add","exception:exception:resourceException:edit"},logical=Logical.OR)
	@RequestMapping(value = "confirmException")
	public String confirmException(ResourceException resourceException) {
		if(resourceException!=null&&"0".equals(resourceException.getConfirmStatus())){//未确认
			resourceExceptionService.confirmException(resourceException.getId(),new Date(),"1", UserUtils.getUser().getId());
		}
		return "redirect:"+Global.getAdminPath()+"/exception/exception/resourceException/?repage";
	}



	/**
	 * 保存异常告警
	 */
	@RequiresPermissions(value={"exception:exception:resourceException:add","exception:exception:resourceException:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceException resourceException, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceException)){
			return form(resourceException, model);
		}
		//新增或编辑表单保存
		resourceExceptionService.save(resourceException);//保存
		addMessage(redirectAttributes, "保存异常告警成功");
		return "redirect:"+Global.getAdminPath()+"/exception/exception/resourceException/?repage";
	}
	
	/**
	 * 删除异常告警
	 */
	@ResponseBody
	@RequiresPermissions("exception:exception:resourceException:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceException resourceException, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceExceptionService.delete(resourceException);
		j.setMsg("删除异常告警成功");
		return j;
	}
	
	/**
	 * 批量删除异常告警
	 */
	@ResponseBody
	@RequiresPermissions("exception:exception:resourceException:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceExceptionService.delete(resourceExceptionService.get(id));
		}
		j.setMsg("删除异常告警成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("exception:exception:resourceException:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ResourceException resourceException, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "异常告警"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceException> page = resourceExceptionService.findPage(new Page<ResourceException>(request, response, -1), resourceException);
    		new ExportExcel("异常告警", ResourceException.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出异常告警记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("exception:exception:resourceException:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceException> list = ei.getDataList(ResourceException.class);
			for (ResourceException resourceException : list){
				try{
					resourceExceptionService.save(resourceException);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条异常告警记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条异常告警记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入异常告警失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/exception/exception/resourceException/?repage";
    }
	
	/**
	 * 下载导入异常告警数据模板
	 */
	@RequiresPermissions("exception:exception:resourceException:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "异常告警数据导入模板.xlsx";
    		List<ResourceException> list = Lists.newArrayList(); 
    		new ExportExcel("异常告警数据", ResourceException.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/exception/exception/resourceException/?repage";
    }


}