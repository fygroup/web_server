
package com.jeeplus.modules.test.mapper.manytoone;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.test.entity.manytoone.Category;

/**
 * 商品类型MAPPER接口
 * @author lf
 * @version 2017-06-11
 */
@MyBatisMapper
public interface CategoryMapper extends TreeMapper<Category> {
	
}