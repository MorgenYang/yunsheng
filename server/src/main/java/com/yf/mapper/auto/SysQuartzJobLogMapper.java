package com.yf.mapper.auto;

import com.yf.model.auto.SysQuartzJobLog;
import com.yf.model.auto.SysQuartzJobLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务调度日志表 SysQuartzJobLogMapper
 */
public interface SysQuartzJobLogMapper {
      	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      	   	      
    long countByExample(SysQuartzJobLogExample example);

    int deleteByExample(SysQuartzJobLogExample example);
		
    int deleteByPrimaryKey(String id);
		
    int insert(SysQuartzJobLog record);

    int insertSelective(SysQuartzJobLog record);

    List<SysQuartzJobLog> selectByExample(SysQuartzJobLogExample example);
		
    SysQuartzJobLog selectByPrimaryKey(String id);
		
    int updateByExampleSelective(@Param("record") SysQuartzJobLog record, @Param("example") SysQuartzJobLogExample example);

    int updateByExample(@Param("record") SysQuartzJobLog record, @Param("example") SysQuartzJobLogExample example); 
		
    int updateByPrimaryKeySelective(SysQuartzJobLog record);

    int updateByPrimaryKey(SysQuartzJobLog record);
  	  	
}