/**
 * Copyright &copy; 2015-2020 <a href="http://www.clutek.org/">clutek</a> All rights reserved.
 */
package com.jeeplus.modules.iim.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.iim.entity.Mail;

/**
 * 发件箱MAPPER接口
 * @author clutek
 * @version 2015-11-15
 */
@MyBatisMapper
public interface MailMapper extends BaseMapper<Mail> {
	public int getCount(MailMapper entity);
}