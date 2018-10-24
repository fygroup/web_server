/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.networkinterface.web;

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
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;

/**
 * 网络接口Controller
 * @author le
 * @version 2017-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/networkinterface/networkInterface")
public class NetworkInterfaceController extends BaseController {

	@Autowired
	private NetworkInterfaceService networkInterfaceService;
	
	@ModelAttribute
	public NetworkInterface get(@RequestParam(required=false) String id) {
		NetworkInterface entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = networkInterfaceService.get(id);
		}
		if (entity == null){
			entity = new NetworkInterface();
		}
		return entity;
	}
	
	/**
	 * 网络接口列表页面
	 */
	@RequiresPermissions("networkinterface:networkInterface:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/networkinterface/networkInterfaceList";
	}
	
		/**
	 * 网络接口列表数据
	 */
	@ResponseBody
	@RequiresPermissions("networkinterface:networkInterface:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(NetworkInterface networkInterface, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NetworkInterface> page = networkInterfaceService.findPage(new Page<NetworkInterface>(request, response), networkInterface); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑网络接口表单页面
	 */
	@RequiresPermissions(value={"networkinterface:networkInterface:view","networkinterface:networkInterface:add","networkinterface:networkInterface:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(NetworkInterface networkInterface, Model model) {
		model.addAttribute("networkInterface", networkInterface);
		if(StringUtils.isBlank(networkInterface.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/networkinterface/networkInterfaceForm";
	}

	/**
	 * 保存网络接口
	 */
	@RequiresPermissions(value={"networkinterface:networkInterface:add","networkinterface:networkInterface:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(NetworkInterface networkInterface, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, networkInterface)){
			return form(networkInterface, model);
		}
		//新增或编辑表单保存
		networkInterfaceService.save(networkInterface);//保存
		addMessage(redirectAttributes, "保存网络接口成功");
		return "redirect:"+Global.getAdminPath()+"/networkinterface/networkInterface/?repage";
	}
	
	/**
	 * 删除网络接口
	 */
	@ResponseBody
	@RequiresPermissions("networkinterface:networkInterface:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(NetworkInterface networkInterface, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		networkInterfaceService.delete(networkInterface);
		j.setMsg("删除网络接口成功");
		return j;
	}
	
	/**
	 * 批量删除网络接口
	 */
	@ResponseBody
	@RequiresPermissions("networkinterface:networkInterface:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			networkInterfaceService.delete(networkInterfaceService.get(id));
		}
		j.setMsg("删除网络接口成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("networkinterface:networkInterface:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(NetworkInterface networkInterface, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "网络接口"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<NetworkInterface> page = networkInterfaceService.findPage(new Page<NetworkInterface>(request, response, -1), networkInterface);
    		new ExportExcel("网络接口", NetworkInterface.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出网络接口记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("networkinterface:networkInterface:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<NetworkInterface> list = ei.getDataList(NetworkInterface.class);
			for (NetworkInterface networkInterface : list){
				try{
					networkInterfaceService.save(networkInterface);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条网络接口记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条网络接口记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入网络接口失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/networkinterface/networkInterface/?repage";
    }
	
	/**
	 * 下载导入网络接口数据模板
	 */
	@RequiresPermissions("networkinterface:networkInterface:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "网络接口数据导入模板.xlsx";
    		List<NetworkInterface> list = Lists.newArrayList(); 
    		new ExportExcel("网络接口数据", NetworkInterface.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/networkinterface/networkInterface/?repage";
    }

}