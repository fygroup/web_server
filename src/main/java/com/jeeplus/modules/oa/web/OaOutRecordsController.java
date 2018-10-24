/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeeplus.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.OaOutRecords;
import com.jeeplus.modules.oa.service.OaOutRecordsService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.jeeplus.common.utils.time.DateUtil.isSameDay;


/**
 * 进出记录Controller
 * @author huanglei
 * @version 2017-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaOutRecords")
public class OaOutRecordsController extends BaseController {

	@Autowired
	private OaOutRecordsService oaOutRecordsService;

	@ModelAttribute
	public OaOutRecords get(@RequestParam(required=false) String id) {
		OaOutRecords entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaOutRecordsService.get(id);
		}
		if (entity == null){
			entity = new OaOutRecords();
		}
		return entity;
	}


//	@RequiresPermissions("oa:oaInspection:list")
//	@RequestMapping(value = {"list", ""})
//	public String list(OaInspection oaInspection, HttpServletRequest request, HttpServletResponse response, Model model) {
//		model.addAttribute("isSelf", false);
//		return "modules/oa/inspection/oaInspectionList";
//	}
//
//	@RequiresPermissions("oa:oaInspection:list")
//	@RequestMapping(value = "data")
//	public Map<String, Object> data(OaInspection oaInspection, boolean isSelf, HttpServletRequest request, HttpServletResponse response, Model model) {
//		if(isSelf){
//			oaInspection.setSelf(true);
//		}
//		Page<OaInspection> page = oaInspectionService.findPage(new Page<OaInspection>(request, response), oaInspection);
//		return getBootstrapData(page);
//	}

	/**
	 * 进出列表页面
	 */
	@RequiresPermissions("oa:oaOutRecords:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/oa/outRecords/oaOutRecordsList";
	}


	/**
	 * 进出列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oa:oaOutRecords:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaOutRecords oaOutRecords, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaOutRecords> page = oaOutRecordsService.findPage(new Page<OaOutRecords>(request, response), oaOutRecords);
		return getBootstrapData(page);
	}
//
//
//	public String list(OaInspection oaInspection, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<OaInspection> page = oaInspectionService.findPage(new Page<OaInspection>(request, response), oaInspection);
//		model.addAttribute("page", page);
//		return "modules/oa/inspection/oaInspectionList";
//	}

//	@RequiresPermissions("oa:oaInspection:view")
//	@RequestMapping(value = "form")
//	public String form(OaInspection oaInspection, Model model) {
//		model.addAttribute("oaInspection", oaInspection);
//		return "modules/oa/inspection/oaInspectionForm";
//	}


	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaOutRecords:view","oa:oaOutRecords:add","oa:oaOutRecords:edit"},logical= Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaOutRecords oaOutRecords,Model model) {
		if (StringUtils.isNotBlank(oaOutRecords.getId())){
			User user = UserUtils.getUser();
		}
		model.addAttribute("oaOutRecords", oaOutRecords);
		return "modules/oa/outRecords/oaOutRecordsForm";
	}

	@RequiresPermissions("oa:oaOutRecords:edit")
	@RequestMapping(value = "save")
	public String save(OaOutRecords oaOutRecords, Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, oaOutRecords)){
			return form(oaOutRecords, model);
		}
		if (isSameDay(oaOutRecords.getEntryTime(),oaOutRecords.getDepartureTime())) {
			Date d1 =oaOutRecords.getEntryTime();
			Date d2 =oaOutRecords.getDepartureTime();
			SimpleDateFormat f = new SimpleDateFormat("hhmmss"); //格式化为 hhmmss
			int d1Number = Integer.parseInt(f.format(d1).toString()); //将第一个时间格式化后转为int
			int d2Number = Integer.parseInt(f.format(d2).toString()); //将第二个时间格式化后转为int
			if (d1Number <= d2Number){
				addMessage(model, "离开时间必须大于进入时间");
				return "modules/oa/outRecords/oaOutRecordsForm";
			}
			oaOutRecordsService.save(oaOutRecords);
			addMessage(redirectAttributes, "保存成功");
			return "redirect:"+ Global.getAdminPath()+"/oa/oaOutRecords/?repage";
		}
		addMessage(model,"进入时间跟离开时间不在同一天");
		return "modules/oa/outRecords/oaOutRecordsForm";
	}

	//	@RequiresPermissions("oa:oaInspection:edit")
	//	@RequestMapping(value = "delete")
	//	public String delete(OaInspection oaInspection, RedirectAttributes redirectAttributes) {
	//		oaInspectionService.delete(oaInspection);
	//		addMessage(redirectAttributes, "删除成功");
	//		return "redirect:"+Global.getAdminPath()+"/oa/oaInspection/?repage";
	//	}


	//删除单条
	@ResponseBody
	@RequiresPermissions("oa:oaOutRecords:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OaOutRecords oaOutRecords, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oaOutRecordsService.delete(oaOutRecords);
		j.setMsg("删除进出记录成功");
		return j;
	}

	//删除多条
	@ResponseBody
	@RequiresPermissions("oa:oaOutRecords:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaOutRecordsService.delete(oaOutRecordsService.get(id));
		}
		j.setMsg("删除进出记录成功");
		return j;
	}


}