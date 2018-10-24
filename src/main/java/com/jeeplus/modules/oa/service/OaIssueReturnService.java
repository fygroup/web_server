/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeeplus.modules.oa.service;

import java.util.List;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaIssueReturn;
import com.jeeplus.modules.oa.mapper.OaIssueReturnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 问题申报Service
 * @author huanglei
 * @version 2017-11-14
 */
@Service
@Transactional(readOnly = true)
public class OaIssueReturnService extends CrudService<OaIssueReturnMapper, OaIssueReturn> {

	@Autowired
	private OaIssueReturnMapper oaIssueReturnMapper;

	public OaIssueReturn get(String id) {
		return super.get(id);
	}
	
	public List<OaIssueReturn> findList(OaIssueReturn oaIssueReturn) {
		return super.findList(oaIssueReturn);
	}
	
	public Page<OaIssueReturn> findPage(Page<OaIssueReturn> page, OaIssueReturn oaIssueReturn) {
		return super.findPage(page, oaIssueReturn);
	}
	
	@Transactional(readOnly = false)
	public void save(OaIssueReturn oaIssueReturn) {
		super.save(oaIssueReturn);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaIssueReturn oaIssueReturn) {
		super.delete(oaIssueReturn);
	}

	//返回String结果
	public String qexecuteSelectSql(String sql){
		return mapper.qexecSelectSql(sql);
	}

	@Transactional(readOnly = false)
	public void saveOaIssueReturn(OaIssueReturn oaIssueReturn) {
		oaIssueReturn.preUpdate();
		oaIssueReturnMapper.update(oaIssueReturn);
	}

}