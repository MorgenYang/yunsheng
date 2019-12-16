package com.yf.mapper.auto;

import com.yf.model.auto.SysQuartzJob;
import com.yf.model.auto.SysQuartzJobExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务调度表 SysQuartzJobMapper
 */
public interface SysQuartzJobMapper {
      	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      
    long countByExample(SysQuartzJobExample example);

    int deleteByExample(SysQuartzJobExample example);
		
    int deleteByPrimaryKey(String id);
		
    int insert(SysQuartzJob record);

    int insertSelective(SysQuartzJob record);

    List<SysQuartzJob> selectByExample(SysQuartzJobExample example);
		
    SysQuartzJob selectByPrimaryKey(String id);
		
    int updateByExampleSelective(@Param("record") SysQuartzJob record, @Param("example") SysQuartzJobExample example);

    int updateByExample(@Param("record") SysQuartzJob record, @Param("example") SysQuartzJobExample example); 
		
    int updateByPrimaryKeySelective(SysQuartzJob record);

    int updateByPrimaryKey(SysQuartzJob record);
  	  	
}