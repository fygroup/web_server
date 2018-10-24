/**
 * There are <a href="http://www.clutek.org/">jeeplus</a> code generation
 */
package com.jeeplus.modules.oa.web;

import com.google.common.collect.Maps;
import com.jeeplus.core.mapper.JsonMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.Leave;
import com.jeeplus.modules.oa.entity.Turnover;
import com.jeeplus.modules.oa.service.LeaveService;
import com.jeeplus.modules.oa.service.TurnoverService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 进出申请Controller
 * @author liuj
 * @version 2016-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/turnover")
public class TurnoverController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected TurnoverService turnoverService;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;
	
	@ModelAttribute
	public Turnover get(@RequestParam(required=false) String id){//,
//			@RequestParam(value="act.procInsId", required=false) String procInsId) {
		Turnover turnover = null;
		if (StringUtils.isNotBlank(id)){
			turnover = turnoverService.get(id);
//		}else if (StringUtils.isNotBlank(procInsId)){
//			testAudit = testAuditService.getByProcInsId(procInsId);
		}
		if (turnover == null){
			turnover = new Turnover();
		}
		return turnover;
	}

	@RequestMapping(value = {"form"})
	public String form(Turnover turnover, Model model) {
		String view = "turnoverForm";
		// 查看审批申请单
		if (StringUtils.isNotBlank(turnover.getId())){//.getAct().getProcInsId())){

			// 环节编号
			String taskDefKey = turnover.getAct().getTaskDefKey();
			
			// 查看工单
			if(turnover.getAct().isFinishTask()){
				view = "turnoverView";
			}
			// 修改环节
			else if ("deptLeaderAudit".equals(taskDefKey)){
				view = "turnoverAudit";
			}
			// 审核环节
			else if ("hrAudit".equals(taskDefKey)){
				view = "turnoverAudit";
			}
			// 审核环节2
			else if ("reportBack".equals(taskDefKey)){
				view = "turnoverAudit";
			}
			// 审核环节3
			else if ("modifyApply".equals(taskDefKey)){
				view = "turnoverAudit";
			}
		}

		model.addAttribute("turnover", turnover);
		return "modules/oa/turnover/"+view;
	}

	/**
	 * 启动进出流程
	 * @param turnover
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Turnover turnover, RedirectAttributes redirectAttributes) {
		try {
			Map<String, Object> variables = Maps.newHashMap();
			turnoverService.save(turnover, variables);
			addMessage(redirectAttributes, "进出申请已经提交");
		} catch (Exception e) {
			logger.error("启动进出流程失败：", e);
			addMessage(redirectAttributes, "系统内部错误！");
		}
		return "redirect:" + adminPath + "/act/task/process/";
	}
	
	
	/**
	 * 工单执行（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveAudit")
	public String saveAudit(Turnover turnover,Map<String, Object> vars, Model model) {
		if (StringUtils.isBlank(turnover.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(turnover, model);
		}
		turnoverService.auditSave(turnover);
		return "redirect:" + adminPath + "/act/task";
	}

	/**
	 * 任务列表
	 * @param turnover
	 */
	@RequestMapping(value = {"list/task",""})
	public String taskList(HttpSession session, Model model) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		List<Turnover> results = turnoverService.findTodoTasks(userId);
		model.addAttribute("turnovers", results);
		return "modules/oa/turnover/turnoverTask";
	}

	/**
	 * 读取所有流程
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(Turnover turnover, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Turnover> page = turnoverService.find(new Page<Turnover>(request, response), turnover);
        model.addAttribute("page", page);
		return "modules/oa/turnover/turnoverList";
	}
	
	/**
	 * 读取详细数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail/{id}")
	@ResponseBody
	public String getLeave(@PathVariable("id") String id) {
		Turnover turnover = turnoverService.get(id);
		return JsonMapper.getInstance().toJson(turnover);
	}

	/**
	 * 读取详细数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail-with-vars/{id}/{taskId}")
	@ResponseBody
	public String getLeaveWithVars(@PathVariable("id") String id, @PathVariable("taskId") String taskId) {
		Turnover turnover = turnoverService.get(id);
		Map<String, Object> variables = taskService.getVariables(taskId);
		turnover.setVariables(variables);
		return JsonMapper.getInstance().toJson(turnover);
	}

}
