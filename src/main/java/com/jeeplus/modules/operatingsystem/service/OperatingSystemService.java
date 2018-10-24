/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.operatingsystem.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.operatingsystem.entity.OperatingSystem;
import com.jeeplus.modules.operatingsystem.mapper.OperatingSystemMapper;

/**
 * 操作系统Service
 * @author le
 * @version 2017-11-01
 */
@Service
@Transactional(readOnly = true)
public class OperatingSystemService extends CrudService<OperatingSystemMapper, OperatingSystem> {
	@Autowired
	private OperatingSystemMapper operatingSystemMapper;

	public OperatingSystem get(String id) {
		return super.get(id);
	}
	
	public List<OperatingSystem> findList(OperatingSystem operatingSystem) {
		return super.findList(operatingSystem);
	}
	
	public Page<OperatingSystem> findPage(Page<OperatingSystem> page, OperatingSystem operatingSystem) {
		return super.findPage(page, operatingSystem);
	}
	
	@Transactional(readOnly = false)
	public void save(OperatingSystem operatingSystem) {
		super.save(operatingSystem);
	}
	
	@Transactional(readOnly = false)
	public void delete(OperatingSystem operatingSystem) {
		super.delete(operatingSystem);
	}

	public List<OperatingSystem> findListByServerId( String serverId){
		return operatingSystemMapper.findListByServerId(serverId);
	}
}