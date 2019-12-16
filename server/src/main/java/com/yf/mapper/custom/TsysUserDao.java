package com.yf.mapper.custom;

import com.yf.model.auto.TsysUser;

/**
 * @ClassName: TsysUserDao
 *
 */
public interface TsysUserDao {
	/**
	 * 根据用户名字查询用户
	 * @param username
	 * @return
	 */
	public TsysUser queryUserName(String username);
}
