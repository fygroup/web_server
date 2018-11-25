package com.jeeplus.modules.patrol.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.modules.patrol.entity.Patrol;
import com.jeeplus.modules.patrolDocument.entity.PatrolDocument;
import com.jeeplus.modules.patrol.service.PatrolService;
import com.jeeplus.modules.patrolDocument.service.PatrolDocumentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;



/**
 * 智能巡检任务 Controller
 * @author  huanglei
 * @version 2018-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/monitor/patrol")
public class PatrolController extends BaseController {

	@Autowired
	private PatrolService patrolService;

	@Autowired
	private PatrolDocumentService patrolDocumentService;
	
	@ModelAttribute
	public Patrol get(@RequestParam(required=false) String id) {
		Patrol entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = patrolService.get(id);
		}
		if (entity == null){
			entity = new Patrol();
		}
		return entity;
	}
	
	/**
	 * 定时任务列表页面
	 */
	@RequiresPermissions("monitor:patrol:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/patrol/task/patrolList";
	}
	
		/**
	 * 定时任务列表数据
	 */
	@ResponseBody
	@RequiresPermissions("monitor:patrol:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Patrol patrol, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Patrol> page = patrolService.findPage(new Page<Patrol>(request, response), patrol);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑定时任务表单页面
	 */
	@RequiresPermissions(value={"monitor:patrol:view","monitor:patrol:add","monitor:patrol:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Patrol patrol, Model model) {
		model.addAttribute("patrol", patrol);
		return "modules/patrol/task/patrolForm";
	}

	/**
	 * 保存巡检任务
	 */
	@RequiresPermissions(value={"monitor:patrol:add","monitor:patrol:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Patrol patrol, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, patrol)){
			return form(patrol, model);
		}
		//验证cron表达式
		if(!CronExpression.isValidExpression(patrol.getCronExpression())){
			addMessage(redirectAttributes, "cron表达式不正确");
			return form(patrol, model);
		}
        patrol.setIsInfo("1");//通知管理员admin
		patrol.setClassName("com.jeeplus.modules.patrol.task.PatrolTask");//巡检任务类
		patrolService.save(patrol);//保存
		addMessage(redirectAttributes, "保存巡检任务成功");
		return "redirect:"+Global.getAdminPath()+"/monitor/patrol/?repage";
	}
	
	/**
	 * 删除定时任务
	 */
	@ResponseBody
	@RequiresPermissions("monitor:patrol:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Patrol patrol, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		patrolService.delete(patrol);
		j.setMsg("删除定时任务成功");
		return j;
	}
	
	/**
	 * 批量删除定时任务
	 */
	@ResponseBody
	@RequiresPermissions("monitor:patrol:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			patrolService.delete(patrolService.get(id));
		}
		j.setMsg("删除定时任务成功");
		return j;
	}
	
	
	
	/**
	 * 暂停任务
	 */
	@RequiresPermissions("monitor:patrol:stop")
	@RequestMapping(value="stop")
	@ResponseBody
	public AjaxJson stop(Patrol patrol) {
		AjaxJson j = new AjaxJson();
		patrolService.stopJob(patrol);
		j.setSuccess(true);
		j.setMsg("暂停成功!");
		return j;
	}


	/**
	 * 立即运行一次
	 */
	@RequiresPermissions("monitor:patrol:startNow")
	@RequestMapping("startNow")
	@ResponseBody
	public AjaxJson stratNow(Patrol patrol) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setMsg("运行成功");
		patrolService.startNowJob(patrol);
		return j;
	}

	/**
	 * 恢复
	 */
	@RequiresPermissions("monitor:patrol:resume")
	@RequestMapping("resume")
	@ResponseBody
	public AjaxJson resume(Patrol patrol, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setMsg("恢复成功");
		patrolService.restartJob(patrol);
		patrolService.startNowJob(patrol);//恢复之后，立即触发一次激活定时任务，不然定时任务有可能不会执行，这是bug的回避案，具体原因我没找到。
		return j;
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