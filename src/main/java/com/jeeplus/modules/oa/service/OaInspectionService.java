package com.jeeplus.modules.oa.service;

import java.util.List;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaInspection;
import com.jeeplus.modules.oa.mapper.OaInspectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 巡检记录Service
 * @author huanglei
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class OaInspectionService extends CrudService<OaInspectionMapper, OaInspection> {

	@Autowired
	private OaInspectionMapper oaInspectionMapper;

	public OaInspection get(String id) {
		OaInspection entity = mapper.get(id);
		return entity;
	}

//	public OaInspection get(String id) {
//		return super.get(id);
//	}
	
	public List<OaInspection> findList(OaInspection oaInspection) {
		return super.findList(oaInspection);
	}
	
	public Page<OaInspection> findPage(Page<OaInspection> page, OaInspection oaInspection) {
		return super.findPage(page, oaInspection);
	}
	
	@Transactional(readOnly = false)
	public void save(OaInspection oaInspection) {
		super.save(oaInspection);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaInspection oaInspection) {
		super.delete(oaInspection);
	}



}