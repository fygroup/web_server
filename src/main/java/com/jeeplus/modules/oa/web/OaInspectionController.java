package com.jeeplus.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.OaInspection;
import com.jeeplus.modules.oa.service.OaInspectionService;
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

import java.util.Map;


/**
 * 巡检记录Controller
 * @author huanglei
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaInspection")
public class OaInspectionController extends BaseController {

	@Autowired
	private OaInspectionService oaInspectionService;
	
	@ModelAttribute
	public OaInspection get(@RequestParam(required=false) String id) {
		OaInspection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaInspectionService.get(id);
		}
		if (entity == null){
			entity = new OaInspection();
		}
		return entity;
	}

	/**
	 * 巡检列表页面
	 */
	@RequiresPermissions("oa:oaInspection:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/oa/inspection/oaInspectionList";
	}


	/**
	 * 巡检列表数据
	 */
    @ResponseBody
	@RequiresPermissions("oa:oaInspection:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaInspection oaInspection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaInspection> page = oaInspectionService.findPage(new Page<OaInspection>(request, response), oaInspection);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaInspection:view","oa:oaInspection:add","oa:oaInspection:edit"},logical= Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaInspection oaInspection,Model model) {
		model.addAttribute("oaInspection", oaInspection);
		if (StringUtils.isNotBlank(oaInspection.getId())){
			model.addAttribute("isAdd", true);
		}
		return "modules/oa/inspection/oaInspectionForm";
	}

	@RequiresPermissions("oa:oaInspection:edit")
	@RequestMapping(value = "save")
	public String save(OaInspection oaInspection, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oaInspection)){
			return form(oaInspection, model);
		}
		oaInspectionService.save(oaInspection);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+ Global.getAdminPath()+"/oa/oaInspection/?repage";
	}



    //删除单条
	@ResponseBody
	@RequiresPermissions("oa:oaInspection:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OaInspection oaInspection, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oaInspectionService.delete(oaInspection);
		j.setMsg("删除巡检记录成功");
		return j;
	}

    //删除多条
	@ResponseBody
	@RequiresPermissions("oa:oaInspection:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaInspectionService.delete(oaInspectionService.get(id));
		}
		j.setMsg("删除巡检记录成功");
		return j;
	}





}