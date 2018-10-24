package com.jeeplus.modules.violations.service;


import com.jeeplus.modules.violations.entity.Violations;
import com.jeeplus.modules.violations.mapper.ViolationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.service.CrudService;


/**
 * 违规事件 Service
 * @author lei
 * @version 2018-09-28
 */
@Service
@Transactional(readOnly = true)
public class ViolationsService extends CrudService<ViolationsMapper, Violations> {

	@Autowired
	private ViolationsMapper violationsMapper;

	public Violations getTopViolations(String id) {
		return violationsMapper.getTopViolations(id);
	}

}