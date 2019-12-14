package com.yf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yf.mapper.custom.SysUtilDao;
import com.yf.model.custom.SQLAdapter;

@Service
public class SysUtilService {
	@Autowired
	private SysUtilDao dao;
	
	/**
	 * 执行sql
	 */
	public int executeSQL(String sql){
		return dao.executeSQL(new SQLAdapter(sql));
	}
}
