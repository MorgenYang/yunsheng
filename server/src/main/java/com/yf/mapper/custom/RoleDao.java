
package com.yf.mapper.custom;

import java.util.List;

import com.yf.model.auto.TsysRole;

/**
 * 角色Dao
 * @ClassName: RoleDao
 * @author yf
 * @date 2018年8月25日
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
