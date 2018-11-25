package com.jeeplus.modules.patrolDocument.web;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.patrolDocument.entity.PatrolDocument;
import com.jeeplus.modules.patrolDocument.service.PatrolDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 智能巡检任务 Controller
 * @author  huanglei
 * @version 2018-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/monitor/patrolDocument")
public class PatrolDocumentController extends BaseController {


	@Autowired
	private PatrolDocumentService patrolDocumentService;
	
	@ModelAttribute
	public PatrolDocument get(@RequestParam(required=false) String id) {
		PatrolDocument entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = patrolDocumentService.get(id);
		}
		if (entity == null){
			entity = new PatrolDocument();
		}
		return entity;
	}


	/**
	 * 智能巡检文件下载列表页面
	 */

	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/patrol/task/patrolDocumentList";
	}

	/**
	 * 智能巡检文件下载
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(PatrolDocument patrolDocument, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PatrolDocument> page = patrolDocumentService.findPage(new Page<PatrolDocument>(request, response), patrolDocument);
		return getBootstrapData(page);
	}

	
	/**
	 * 验证类任务类是否存在
	 */
	@RequestMapping("existsClass")
	@ResponseBody
	public boolean existsClass(String className) {
		Class job = null;
		try {
			job = Class.forName(className);
			return true;
		} catch (ClassNotFoundException e1) {
			return false;
		}
	}










}