
package com.jeeplus.modules.indicator.service;

import java.util.List;

import com.jeeplus.modules.indicator.entity.Indicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.indicator.mapper.IndicatorMapper;

/**
 * 指标列表Service
 * @author le
 * @version 2017-11-13
 */
@Service
@Transactional(readOnly = true)
public class IndicatorService extends CrudService<IndicatorMapper, Indicator> {

	@Autowired
	private IndicatorMapper indicatorMapper;

	public Indicator get(String id) {
		return super.get(id);
	}
	
	public List<Indicator> findList(Indicator indicator) {
		return super.findList(indicator);
	}
	
	public Page<Indicator> findPage(Page<Indicator> page, Indicator indicator) {
		return super.findPage(page, indicator);
	}
	
	@Transactional(readOnly = false)
	public void save(Indicator indicator) {
		super.save(indicator);
	}
	
	@Transactional(readOnly = false)
	public void delete(Indicator indicator) {
		super.delete(indicator);
	}

	public Indicator getByType(String type) {
		return indicatorMapper.getByType(type);
	}

}