
package com.jeeplus.modules.echarts.service.other;

import java.util.List;

import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.entity.ResponseTypeNumStatistics;
import com.jeeplus.modules.resourcetype.mapper.ResourceTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.echarts.entity.other.TestPieClass;
import com.jeeplus.modules.echarts.mapper.other.TestPieClassMapper;


@Service
@Transactional(readOnly = true)
public class TestPieClassService extends CrudService<TestPieClassMapper, TestPieClass> {
	@Autowired
	private ResourceTypeMapper resourceTypeMapper;
	public TestPieClass get(String id) {
		return super.get(id);
	}
	
	public List<TestPieClass> findList(TestPieClass testPieClass) {
		return super.findList(testPieClass);
	}
	
	public Page<ResponseTypeNumStatistics> findPage(Page<ResponseTypeNumStatistics> page, ResponseTypeNumStatistics responseTypeNumStatistics) {
		dataRuleFilter(responseTypeNumStatistics);
		responseTypeNumStatistics.setPage(page);
		List<ResponseTypeNumStatistics> list=resourceTypeMapper.numStatistics();
		page.setList(list);
		page.setCount(list.size());
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(TestPieClass testPieClass) {
		super.save(testPieClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestPieClass testPieClass) {
		super.delete(testPieClass);
	}
	
}