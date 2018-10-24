
package com.jeeplus.modules.test.mapper.grid;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.test.entity.grid.TestCountry;

/**
 * 国家MAPPER接口
 * @author lgf
 * @version 2017-06-12
 */
@MyBatisMapper
public interface TestCountryMapper extends BaseMapper<TestCountry> {
	
}