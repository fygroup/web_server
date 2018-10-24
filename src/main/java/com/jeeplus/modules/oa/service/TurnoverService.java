
package com.jeeplus.modules.oa.service;

import com.google.common.collect.Maps;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.act.utils.ActUtils;
import com.jeeplus.modules.oa.entity.Leave;
import com.jeeplus.modules.oa.entity.Turnover;
import com.jeeplus.modules.oa.mapper.LeaveMapper;
import com.jeeplus.modules.oa.mapper.TurnoverMapper;
import org.activiti.engine.*;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 请假Service
 * @author liuj
 * @version 2016-04-05
 */
@Service
@Transactional(readOnly = true)
public class TurnoverService extends BaseService {

	@Autowired
	private TurnoverMapper turnoverMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	
	
	@Autowired
	private ActTaskService actTaskService;


	/**
	 * 获取流程详细及工作流参数
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public Turnover get(String id) {
		Turnover turnover = turnoverMapper.get(id);
		Map<String,Object> variables=null;
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(turnover.getProcessInstanceId()).singleResult();
		if(historicProcessInstance!=null) {
			variables = Collections3.extractToMap(historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list(), "variableName", "value");
		} else {
			variables = runtimeService.getVariables(runtimeService.createProcessInstanceQuery().processInstanceId(turnover.getProcessInstanceId()).active().singleResult().getId());
		}
		turnover.setVariables(variables);
		return turnover;
	}
	
	/**
	 * 启动流程
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(Turnover turnover, Map<String, Object> variables) {
		
		// 保存业务数据
		if (StringUtils.isBlank(turnover.getId())){
			turnover.preInsert();
			turnoverMapper.insert(turnover);
		}else{
			turnover.preUpdate();
			turnoverMapper.update(turnover);
		}
		logger.debug("save entity: {}", turnover);
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(turnover.getCurrentUser().getLoginName());
		
		// 启动流程
		String businessKey = turnover.getId().toString();
		variables.put("type", "turnover");
		variables.put("busId", businessKey);
		variables.put("title", turnover.getReason());//设置标题；
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ActUtils.PD_TRUNOVER[0], businessKey, variables);
		turnover.setProcessInstance(processInstance);
		
		// 更新流程实例ID
		turnover.setProcessInstanceId(processInstance.getId());
		turnoverMapper.updateProcessInstanceId(turnover);
		
		logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[] { 
				ActUtils.PD_TRUNOVER[0], businessKey, processInstance.getId(), variables });
		
	}

	/**
	 * 查询待办任务
	 * @param userId 用户ID
	 * @return
	 */
	public List<Turnover> findTodoTasks(String userId) {
		
		List<Turnover> results = new ArrayList<Turnover>();
		List<Task> tasks = new ArrayList<Task>();
		// 根据当前人的ID查询
		List<Task> todoList = taskService.createTaskQuery().processDefinitionKey(ActUtils.PD_TRUNOVER[0]).taskAssignee(userId).active().orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
		// 根据当前人未签收的任务
		List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(ActUtils.PD_TRUNOVER[0]).taskCandidateUser(userId).active().orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
		// 合并
		tasks.addAll(todoList);
		tasks.addAll(unsignedTasks);
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
			String businessKey = processInstance.getBusinessKey();
			Turnover turnover = turnoverMapper.get(businessKey);
			turnover.setTask(task);
			turnover.setProcessInstance(processInstance);
			turnover.setProcessDefinition(repositoryService.createProcessDefinitionQuery().processDefinitionId((processInstance.getProcessDefinitionId())).singleResult());
			results.add(turnover);
		}
		return results;
	}

	public Page<Turnover> find(Page<Turnover> page, Turnover turnover) {

		dataRuleFilter(turnover);
		turnover.setPage(page);
		page.setList(turnoverMapper.findList(turnover));
		
		for(Turnover item : page.getList()) {
			String processInstanceId = item.getProcessInstanceId();
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
			item.setTask(task);
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(historicProcessInstance!=null) {
				item.setHistoricProcessInstance(historicProcessInstance);
				item.setProcessDefinition(repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult());
			} else {
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
				if (processInstance != null){
					item.setProcessInstance(processInstance);
					item.setProcessDefinition(repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult());
				}
			}
		}
		return page;
	}
	
	/* * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void auditSave(Turnover turnover) {
		
		// 设置意见
		turnover.getAct().setComment(("yes".equals(turnover.getAct().getFlag())?"[同意] ":"[驳回] ")+turnover.getAct().getComment());

		turnover.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = turnover.getAct().getTaskDefKey();
       
		//业务逻辑对应的条件表达式
		String exp = "";
		// 审核环节
		if ("deptLeaderAudit".equals(taskDefKey)){
			
			exp = "deptLeaderPass";
			
		}
		else if ("hrAudit".equals(taskDefKey)){
			exp = "hrPass";
		}
		else if ("modifyApply".equals(taskDefKey)){
			exp = "reApply";
		}
		else if ("apply_end".equals(taskDefKey)){
			
		}
		
		// 未知环节，直接返回
		else{
			return;
		}
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put(exp, "yes".equals(turnover.getAct().getFlag())? true : false);
		// 提交流程任务
		actTaskService.complete(turnover.getAct().getTaskId(), turnover.getAct().getProcInsId(), turnover.getAct().getComment(), vars);
		
	}
	
}
