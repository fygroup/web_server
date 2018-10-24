/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeeplus.modules.oa.service;

import java.util.List;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaOutRecords;
import com.jeeplus.modules.oa.mapper.OaOutRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


	/**
	 * 进出记录Service
	 * @author huanglei
	 * @version 2017-11-10
	 */
@Service
@Transactional(readOnly = true)
public class OaOutRecordsService extends CrudService<OaOutRecordsMapper, OaOutRecords> {
	@Autowired
	private OaOutRecordsMapper oaOutRecordsMapper;

	public OaOutRecords get(String id) {
		OaOutRecords entity = mapper.get(id);
		return entity;
	}

//	public OaInspection get(String id) {
//		return super.get(id);
//	}



	public List<OaOutRecords> findList(OaOutRecords oaOutRecords) {
		return super.findList(oaOutRecords);
	}

	public Page<OaOutRecords> findPage(Page<OaOutRecords> page, OaOutRecords oaOutRecords) {
		return super.findPage(page, oaOutRecords);
	}

	@Transactional(readOnly = false)
	public void save(OaOutRecords oaOutRecords) {
		super.save(oaOutRecords);
	}

	@Transactional(readOnly = false)
	public void delete(OaOutRecords oaOutRecords) {
		super.delete(oaOutRecords);
	}


}