package com.yf.mapper.auto;

import com.yf.model.auto.TSysDictData;
import com.yf.model.auto.TSysDictDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据表
 */
public interface TSysDictDataMapper {
    long countByExample(TSysDictDataExample example);

    int deleteByExample(TSysDictDataExample example);
		
    int insert(TSysDictData record);

    int insertSelective(TSysDictData record);

    List<TSysDictData> selectByExample(TSysDictDataExample example);
		
    int updateByExampleSelective(@Param("record") TSysDictData record, @Param("example") TSysDictDataExample example);

    int updateByExample(@Param("record") TSysDictData record, @Param("example") TSysDictDataExample example);

    TSysDictData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSysDictData record);
}