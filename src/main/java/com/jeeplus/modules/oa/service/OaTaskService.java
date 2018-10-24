package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaTask;
import com.jeeplus.modules.oa.mapper.OaTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 任务安排Service
 * @author huanglei
 * @version 2018-04-10
 */
@Service
@Transactional(readOnly = true)
public class OaTaskService extends CrudService<OaTaskMapper, OaTask> {

	@Autowired
	private OaTaskMapper oaTaskMapper;

	public OaTask get(String id) {
		OaTask entity = mapper.get(id);
		return entity;
	}

	public List<OaTask> findList(OaTask oaTask) {
		return super.findList(oaTask);
	}
	
	public Page<OaTask> findPage(Page<OaTask> page, OaTask oaTask) {
		return super.findPage(page, oaTask);
	}
	
	@Transactional(readOnly = false)
	public void save(OaTask oaTask) {
		super.save(oaTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaTask oaTask) {
		super.delete(oaTask);
	}

	@Transactional(readOnly = false)
	public int setIsFlag(String taskId){
		return oaTaskMapper.setIsFlag(taskId);
	}

	@Transactional(readOnly = false)
	public int setIsFlagFinish(String taskId){
		return oaTaskMapper.setIsFlagFinish(taskId);
	}


	//返回String结果
	public String qexecuteSelectSql(String sql){
		return mapper.qexecSelectSql(sql);
	}

	@Transactional(readOnly = false)
	public void saveTask(OaTask oaTask) {
		oaTask.preUpdate();
		oaTaskMapper.update(oaTask);
	}
}