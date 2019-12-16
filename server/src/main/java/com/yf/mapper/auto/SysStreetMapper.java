package com.yf.mapper.auto;

import com.yf.model.auto.SysStreet;
import com.yf.model.auto.SysStreetExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 街道设置 SysStreetMapper
 */
public interface SysStreetMapper {
      	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      
    long countByExample(SysStreetExample example);

    int deleteByExample(SysStreetExample example);
		
    int deleteByPrimaryKey(Integer id);
		
    int insert(SysStreet record);

    int insertSelective(SysStreet record);

    List<SysStreet> selectByExample(SysStreetExample example);
		
    SysStreet selectByPrimaryKey(Integer id);
		
    int updateByExampleSelective(@Param("record") SysStreet record, @Param("example") SysStreetExample example);

    int updateByExample(@Param("record") SysStreet record, @Param("example") SysStreetExample example); 
		
    int updateByPrimaryKeySelective(SysStreet record);

    int updateByPrimaryKey(SysStreet record);
  	  	
}