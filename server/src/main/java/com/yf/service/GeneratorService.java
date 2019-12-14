package com.yf.service;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.mapper.auto.GeneratorMapper;
import com.yf.model.custom.GenVo;
import com.yf.model.custom.Tablepar;
import com.yf.model.custom.TsysTables;
import com.yf.model.custom.autocode.BeanColumn;
import com.yf.util.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;


/**
 * 自动生成代码Service 
 */
@Service
public class GeneratorService {
	@Autowired
	private GeneratorMapper generatorMapper;
	/**
	 * 分页查询
	 * @param tablepar
	 * @param searchTxt
	 * @return
	 */
	 public PageInfo<TsysTables> list(Tablepar tablepar,String searchTxt){
		 	PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
		 	List<TsysTables> list=  generatorMapper.queryList(searchTxt);
		    PageInfo<TsysTables> pageInfo = new PageInfo<TsysTables>(list);  
		    
		    return pageInfo;
	 }
	 

	 /**
	  * 查询具体某表信息
	  * @param tableName
	  * @return
	  */
	 public List<TsysTables> queryList(String tableName){
		return generatorMapper.queryList(tableName);
	 }
	 
	 
	 /**
	  * 代码
	  * @param tableNames
	  * @return
	  */
	public byte[] generatorCode(String[] tableNames,GenVo genVo){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			TsysTables table = generatorMapper.queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns =generatorMapper.queryColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip,genVo);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	 }
	
	/**
	  * 查询表详情
	  * @param tableName
	  * @return
	  */
	public List<BeanColumn> queryColumns2(String tableName){
		System.out.println("ssssssss"+JSONUtil.toJsonPrettyStr(generatorMapper.queryColumns3(tableName)));
		return generatorMapper.queryColumns2(tableName);
	 }
}
