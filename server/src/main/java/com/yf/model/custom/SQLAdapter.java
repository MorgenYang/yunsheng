package com.yf.model.custom;

/**
 * 通用sql
*
 */
public class SQLAdapter {
	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @param sql
	 * @author yf
	 * @Date 2019年8月31日 下午7:25:22
	 */
	public SQLAdapter(String sql) {
		super();
		this.sql = sql;
	}
	
}
