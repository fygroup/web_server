package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaTaskDispose;
import com.jeeplus.modules.oa.mapper.OaTaskDisposeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 任务处理Service
 * @author huanglei
 * @version 2018-04-11
 */
@Service
@Transactional(readOnly = true)
public class OaTaskDisposeService extends CrudService<OaTaskDisposeMapper, OaTaskDispose> {

	@Autowired
	private OaTaskDisposeMapper oaTaskDisposeMapper;

	public OaTaskDispose get(String id) {
		OaTaskDispose entity = mapper.get(id);
		return entity;
	}

	public List<OaTaskDispose> findList(OaTaskDispose oaTaskDispose) {
		return super.findList(oaTaskDispose);
	}
	
	public Page<OaTaskDispose> findPage(Page<OaTaskDispose> page, OaTaskDispose oaTaskDispose) {
		return super.findPage(page, oaTaskDispose);
	}


	@Transactional(readOnly = false)
	public int amountdispose( String taskId){
		return oaTaskDisposeMapper.amountdispose(taskId);
	}

	@Transactional(readOnly = false)
	public int amountfinish( String taskId){
		return oaTaskDisposeMapper.amountfinish(taskId);
	}


	@Transactional(readOnly = false)
	public void save(OaTaskDispose oaTaskDispose) {
		super.save(oaTaskDispose);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaTaskDispose oaTaskDispose) {
		super.delete(oaTaskDispose);
	}

	//返回String结果
	public String qexecuteSelectSql(String sql){
		return mapper.qexecSelectSql(sql);
	}

	@Transactional(readOnly = false)
	public void saveTask(OaTaskDispose oaTaskDispose) {
		oaTaskDispose.preUpdate();
		oaTaskDisposeMapper.update(oaTaskDispose);
	}
}