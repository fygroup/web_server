package com.jeeplus.modules.oa.service;

import com.jeeplus.modules.oa.entity.Turnover;
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
 * 进出处理器
 * @author huanglei
 */
@Service
@Transactional
public class TurnoverReportProcessor implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TurnoverMapper turnoverMapper;
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * 进出人员离开后执行，保存实际开始和结束时间
	 */
	public void notify(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		Turnover turnover = new Turnover(processInstance.getBusinessKey());
		turnover.setRealityEntryTime((Date) delegateTask.getVariable("realityEntryTime"));
		turnover.setRealityDepartureTime((Date) delegateTask.getVariable("realityDepartureTime"));
		turnover.preUpdate();
		turnoverMapper.updateRealityTime(turnover);
	}

}
