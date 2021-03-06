
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.Role;

/**
 * 角色MAPPER接口
 */
@MyBatisMapper
public interface RoleMapper extends BaseMapper<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与数据权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleDataRule(Role role);

	public int insertRoleDataRule(Role role);

}
