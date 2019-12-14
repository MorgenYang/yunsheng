package com.yf.mapper.custom;

import com.yf.model.auto.TsysDatas;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TsysDatasDao {
	
	public List<TsysDatas> selectByPrimaryKeys(@Param("ids") List<String> ids);
	
}