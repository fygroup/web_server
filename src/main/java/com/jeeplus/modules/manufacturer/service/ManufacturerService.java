/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.manufacturer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.manufacturer.entity.Manufacturer;
import com.jeeplus.modules.manufacturer.mapper.ManufacturerMapper;

/**
 * 厂商信息Service
 * @author le
 * @version 2017-11-01
 */
@Service
@Transactional(readOnly = true)
public class ManufacturerService extends CrudService<ManufacturerMapper, Manufacturer> {

	public Manufacturer get(String id) {
		return super.get(id);
	}
	
	public List<Manufacturer> findList(Manufacturer manufacturer) {
		return super.findList(manufacturer);
	}
	
	public Page<Manufacturer> findPage(Page<Manufacturer> page, Manufacturer manufacturer) {
		return super.findPage(page, manufacturer);
	}
	
	@Transactional(readOnly = false)
	public void save(Manufacturer manufacturer) {
		super.save(manufacturer);
	}
	
	@Transactional(readOnly = false)
	public void delete(Manufacturer manufacturer) {
		super.delete(manufacturer);
	}
	
}