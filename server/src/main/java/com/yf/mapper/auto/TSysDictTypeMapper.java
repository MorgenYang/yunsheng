package com.yf.mapper.auto;

import com.yf.model.auto.TSysDictType;
import com.yf.model.auto.TSysDictTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型表
 */
public interface TSysDictTypeMapper {
    long countByExample(TSysDictTypeExample example);

    int deleteByExample(TSysDictTypeExample example);
		
    int deleteByPrimaryKey(String id);
		
    int insert(TSysDictType record);

    int insertSelective(TSysDictType record);

    List<TSysDictType> selectByExample(TSysDictTypeExample example);
		
    TSysDictType selectByPrimaryKey(String id);
		
    int updateByExampleSelective(@Param("record") TSysDictType record, @Param("example") TSysDictTypeExample example);

    int updateByExample(@Param("record") TSysDictType record, @Param("example") TSysDictTypeExample example); 
		
    int updateByPrimaryKeySelective(TSysDictType record);

    int updateByPrimaryKey(TSysDictType record);
}