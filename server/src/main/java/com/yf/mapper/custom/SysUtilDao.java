package com.yf.mapper.custom;

import com.yf.model.custom.SQLAdapter;

/**
 * 通用dao
* @ClassName: SysUtilDao
* @author yf
* @date 2019-08-31 18:11
*
 */
public interface SysUtilDao {
	
	/**
	 * 执行sql
	 * @param sql
	 * @return
	 * @author yf
	 * @Date 2019年8月31日 下午6:15:08
	 */
	public int executeSQL(SQLAdapter sql); 
}
