package com.jeeplus.modules.oa.service;

import com.jeeplus.modules.oa.entity.Leave;
import com.jeeplus.modules.oa.entity.Turnover;
import com.jeeplus.modules.oa.mapper.LeaveMapper;
import com.jeeplus.modules.oa.mapper.TurnoverMapper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 调整进出申请内容处理器
 * @author huanglei
 */
@Service
@Transactional
public class TurnoverModifyProcessor implements TaskListener {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private TurnoverMapper turnoverMapper;
	@Autowired
	private RuntimeService runtimeService;

	public void notify(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		Turnover turnover = new Turnover(processInstance.getBusinessKey());
		turnover.setUnits((String) delegateTask.getVariable("units"));
		turnover.setName((String) delegateTask.getVariable("name"));
		turnover.setEntryTime((Date) delegateTask.getVariable("entryTime"));
		turnover.setDepartureTime((Date) delegateTask.getVariable("departureTime"));
		turnover.setReason((String) delegateTask.getVariable("reason"));
		turnover.preUpdate();
		turnoverMapper.update(turnover);
	}

}
