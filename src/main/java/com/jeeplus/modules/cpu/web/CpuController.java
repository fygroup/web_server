/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cpu.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.service.CpuService;

/**
 * CPU信息Controller
 * @author le
 * @version 2017-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cpu/cpu")
public class CpuController extends BaseController {

	@Autowired
	private CpuService cpuService;
	@Autowired
	private static SystemService systemService;

	
	@ModelAttribute
	public Cpu get(@RequestParam(required=false) String id) {
		Cpu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cpuService.get(id);
		}
		if (entity == null){
			entity = new Cpu();
		}
		return entity;
	}
	
	/**
	 * CPU信息列表页面
	 */
	@RequiresPermissions("cpu:cpu:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cpu/cpuList";
	}
	
		/**
	 * CPU信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cpu:cpu:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Cpu cpu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cpu> page = cpuService.findPage(new Page<Cpu>(request, response), cpu); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑CPU信息表单页面
	 */
	@RequiresPermissions(value={"cpu:cpu:view","cpu:cpu:add","cpu:cpu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Cpu cpu, Model model) {
		model.addAttribute("cpu", cpu);
		if(StringUtils.isBlank(cpu.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cpu/cpuForm";
	}

	/**
	 * 保存CPU信息
	 */
	@RequiresPermissions(value={"cpu:cpu:add","cpu:cpu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Cpu cpu, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cpu)){
			return form(cpu, model);
		}
		//新增或编辑表单保存
		cpuService.save(cpu);//保存
		addMessage(redirectAttributes, "保存CPU信息成功");
		return "redirect:"+Global.getAdminPath()+"/cpu/cpu/?repage";
	}
	
	/**
	 * 删除CPU信息
	 */
	@ResponseBody
	@RequiresPermissions("cpu:cpu:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Cpu cpu, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cpuService.delete(cpu);
		j.setMsg("删除CPU信息成功");
		return j;
	}
	
	/**
	 * 批量删除CPU信息
	 */
	@ResponseBody
	@RequiresPermissions("cpu:cpu:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cpuService.delete(cpuService.get(id));
		}
		j.setMsg("删除CPU信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cpu:cpu:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Cpu cpu, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "CPU信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Cpu> page = cpuService.findPage(new Page<Cpu>(request, response, -1), cpu);
    		new ExportExcel("CPU信息", Cpu.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出CPU信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cpu:cpu:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Cpu> list = ei.getDataList(Cpu.class);
			for (Cpu cpu : list){
				try{
					cpuService.save(cpu);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条CPU信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条CPU信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入CPU信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cpu/cpu/?repage";
    }
	
	/**
	 * 下载导入CPU信息数据模板
	 */
	@RequiresPermissions("cpu:cpu:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "CPU信息数据导入模板.xlsx";
    		List<Cpu> list = Lists.newArrayList(); 
    		new ExportExcel("CPU信息数据", Cpu.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cpu/cpu/?repage";
    }














	/**
	 * 计算阈值
	 * @param resource
	 * @param resourceIndicatorlist
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static ResourceException calculationThreshold(Resource resource, ResourceIndicatorlist resourceIndicatorlist, String value,ResourceException resourceException) throws Exception{
		boolean update=false;
		ScriptEngineManager manager=new ScriptEngineManager();
		ScriptEngine se=manager.getEngineByName("js");
		Date date=new Date();
		String ruler="";
		if(resourceException==null){
			resourceException =new ResourceException();
		}else{
			resourceException.setCurrentStatus("2");                               //当前状态（0，未恢复 1，手动恢复 2，已恢复）
			resourceException.setConfirmStatus("1");                               //确认状态（0，未确认 1，已确认）
			//resourceException.setConfirmUser(systemService.getUser("1"));       //确认人
			update=true;
		}
		if(!update){
			resourceException.setCreateDate(date);
			resourceException.setIndicatorName(resourceIndicatorlist.getIndicator().getName()+"超过阈值");
			resourceException.setEventType(resourceIndicatorlist.getIndicator().getEventType());
			resourceException.setExceptionSource(resource.getName()+"("+resource.getIp()+")");
			resourceException.setResourceIndicator(resourceIndicatorlist);
			resourceException.setFirstTriggerTime(date);
			resourceException.setLastTriggerTime(date);
			resourceException.setFirstTriggerValue(value);
			resourceException.setResourceType(resource.getResourceType());
			resourceException.setIndicatorItem(resourceIndicatorlist.getIndicator());
			resourceException.setTotalQuantity("1");
			resourceException.setCurrentValue(value);
			resourceException.setCurrentStatus("0");                     //当前状态（0，未恢复 1，手动恢复 2，已恢复）
			resourceException.setOffice(resource.getCompany());
			resourceException.setConfirmStatus("0");                     //确认状态（0，未确认 1，已确认）
			//resourceException.setConfirmUser(UserUtils.getUser());       //确认人
		}

		if(StringUtils.isNotBlank(resourceIndicatorlist.getHighUrgentThreshold())){
			if(resourceIndicatorlist.getHighUrgentThreshold().indexOf("x")!=-1){
				ruler=replaceEscape(resourceIndicatorlist.getHighUrgentThreshold()).toLowerCase().replaceAll("x",value);
				if(((Boolean) se.eval(ruler))) {
					resourceException.setExceptionClass("3");
					if (!update) {
						resourceException.setFirstTriggerClass("3");
					}else {
						resourceException.setTotalQuantity(String.valueOf(Integer.parseInt(resourceException.getTotalQuantity())+1));
						resourceException.setCurrentValue(value);

						//再次产生异常是否需要设置状态未恢复、未确认？
						resourceException.setCurrentStatus("0");      //当前状态（0，未恢复 1，手动恢复 2，已恢复）
						resourceException.setConfirmStatus("0");      //确认状态（0，未确认 1，已确认）
						resourceException.setConfirmUser(null);       //确认人


					}

					return resourceException;
				}

			}
		}

		if(StringUtils.isNotBlank(resourceIndicatorlist.getMiddleUrgentThreshold())){
			if(resourceIndicatorlist.getMiddleUrgentThreshold().indexOf("x")!=-1) {
				ruler = replaceEscape(resourceIndicatorlist.getMiddleUrgentThreshold()).toLowerCase().replaceAll("x", value);
				if (((Boolean) se.eval(ruler))) {
					resourceException.setExceptionClass("2");
					if (!update) {
						resourceException.setFirstTriggerClass("2");
					} else {
						resourceException.setTotalQuantity(String.valueOf(Integer.parseInt(resourceException.getTotalQuantity()) + 1));
						resourceException.setCurrentValue(value);

						//再次产生异常是否需要设置状态未恢复、未确认？
						resourceException.setCurrentStatus("0");      //当前状态（0，未恢复 1，手动恢复 2，已恢复）
						resourceException.setConfirmStatus("0");      //确认状态（0，未确认 1，已确认）
						resourceException.setConfirmUser(null);       //确认人


					}

					return resourceException;
				}
			}
		}

		if(StringUtils.isNotBlank(resourceIndicatorlist.getNormalUrgentThreshold())){
			if(resourceIndicatorlist.getNormalUrgentThreshold().indexOf("x")!=-1) {
				ruler = replaceEscape(resourceIndicatorlist.getNormalUrgentThreshold()).toLowerCase().replaceAll("x", value);
				if (((Boolean) se.eval(ruler))) {
					resourceException.setExceptionClass("1");
					if (!update) {
						resourceException.setFirstTriggerClass("1");
					} else {
						resourceException.setTotalQuantity(String.valueOf(Integer.parseInt(resourceException.getTotalQuantity()) + 1));
						resourceException.setCurrentValue(value);

						//再次产生异常是否需要设置状态未恢复、未确认？
						resourceException.setCurrentStatus("0");      //当前状态（0，未恢复 1，手动恢复 2，已恢复）
						resourceException.setConfirmStatus("0");      //确认状态（0，未确认 1，已确认）
						resourceException.setConfirmUser(null);       //确认人

					}
					return resourceException;
				}
			}
		}

		if(StringUtils.isNotBlank(resourceIndicatorlist.getTipThreshold())) {
			if(resourceIndicatorlist.getTipThreshold().indexOf("x")!=-1) {
				ruler = replaceEscape(resourceIndicatorlist.getTipThreshold()).toLowerCase().replaceAll("x", value);
				if (((Boolean) se.eval(ruler))) {
					resourceException.setExceptionClass("0");
					if (!update) {
						resourceException.setFirstTriggerClass("0");
					} else {
						resourceException.setTotalQuantity(String.valueOf(Integer.parseInt(resourceException.getTotalQuantity()) + 1));
						resourceException.setCurrentValue(value);


						//再次产生异常是否需要设置状态未恢复、未确认？
						resourceException.setCurrentStatus("0");      //当前状态（0，未恢复 1，手动恢复 2，已恢复）
						resourceException.setConfirmStatus("0");      //确认状态（0，未确认 1，已确认）
						resourceException.setConfirmUser(null);       //确认人


					}
				} /*else {
					if (update) {
						resourceException.setCurrentStatus("2");
					}
				}*/
			}
		}
		if(StringUtils.isEmpty(resourceException.getExceptionClass())) {
			return null;
		}
		if(update) {
			resourceException.setUpdateDate(date);
		}
		return resourceException;
	}

	public static String replaceEscape(String value){
		value=value.replaceAll("&gt;",">");
		value=value.replaceAll("&lt;","<");
		value=value.replaceAll("&amp;","&");
		return value;
	}
}