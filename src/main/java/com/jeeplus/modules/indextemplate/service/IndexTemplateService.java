/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.indextemplate.service;

import java.util.List;

import com.jeeplus.common.utils.CheckObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.indextemplate.entity.IndexTemplate;
import com.jeeplus.modules.indextemplate.mapper.IndexTemplateMapper;

/**
 * 指标模板Service
 * @author le
 * @version 2018-01-20
 */
@Service
@Transactional(readOnly = true)
public class IndexTemplateService extends CrudService<IndexTemplateMapper, IndexTemplate> {

	@Autowired
	private IndexTemplateMapper indexTemplateMapper;

	public IndexTemplate get(String id) {
		return super.get(id);
	}
	
	public List<IndexTemplate> findList(IndexTemplate indexTemplate) {
		return super.findList(indexTemplate);
	}
	
	public Page<IndexTemplate> findPage(Page<IndexTemplate> page, IndexTemplate indexTemplate) {
		return super.findPage(page, indexTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(IndexTemplate indexTemplate) {
		super.save(indexTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(IndexTemplate indexTemplate) {
		super.delete(indexTemplate);
	}

	public List<IndexTemplate> list(String resourceTypeId){
		List<IndexTemplate> list=indexTemplateMapper.list(resourceTypeId);
		if(CheckObject.checkList(list)){
			for(IndexTemplate indexTemplate:list){
				indexTemplate.setIndicatorList(indexTemplateMapper.indicatorListByIndexTemplateId(indexTemplate.getId()));
			}
		}
		return list;
	}
}