/**
 * Copyright &copy; 2015-2020 <a href="http://www.clutek.org/">clutek</a> All rights reserved.
 */
package com.jeeplus.modules.patrolDocument.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.patrolDocument.entity.PatrolDocument;
import com.jeeplus.modules.patrolDocument.mapper.PatrolDocumentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 智能巡检任务 Service
 * @author huanglei
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class PatrolDocumentService extends CrudService<PatrolDocumentMapper, PatrolDocument> {

	public PatrolDocument get(String id) {
		return mapper.get(id);
	}

	public List<PatrolDocument> findList(PatrolDocument patrolDocument) {
		return mapper.findList(patrolDocument);
	}

	public Page<PatrolDocument> findPage(Page<PatrolDocument> page, PatrolDocument patrolDocument) {
		return super.findPage(page, patrolDocument);
	}

	@Transactional(readOnly = false)
	public void save(PatrolDocument patrolDocument) {
		super.save(patrolDocument);
	}

	@Transactional(readOnly = false)
	public void delete(PatrolDocument patrolDocument) {
		super.delete(patrolDocument);
	}
	
	
	
}