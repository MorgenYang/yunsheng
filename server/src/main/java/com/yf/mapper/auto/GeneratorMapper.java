package com.yf.mapper.auto;

import com.yf.model.custom.TsysTables;
import com.yf.model.custom.autocode.BeanColumn;

import java.util.List;
import java.util.Map;

/**
 * 数据库接口
 */
public interface GeneratorMapper {
	/**
	 * 查询当前所有表
	 * @param tableName 条件搜索
	 * @return
	 */
	 List<TsysTables> queryList(String tableName);
	 
	 /**
	  * 查询具体某表信息
	  * @param tableName
	  * @return
	  */
	 TsysTables queryTable(String tableName);
	 
	 /**
	  * 查询表详情
	  * @param tableName
	  * @return
	  */
	 List<Map<String, String>> queryColumns(String tableName);
	 /**
	  * 查询表详情
	  * @param tableName
	  * @return
	  */
	 List<BeanColumn> queryColumns2(String tableName);
	 List<Map<String, String>> queryColumns3(String tableName);
}
