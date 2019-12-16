
package com.yf.mapper.custom;

import com.yf.model.auto.TsysRole;

import java.util.List;

/**
 * 角色Dao
 *
 */
public interface RoleDao {
	/**
	 * 根据用户id查询角色
	 * @param userid
	 * @return
	 */
	public List<TsysRole> queryUserRole(String userid);
}
