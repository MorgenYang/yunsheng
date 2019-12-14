package com.yf.mapper.auto;

import com.yf.model.auto.SysQuartzJobLog;
import com.yf.model.auto.SysQuartzJobLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务调度日志表 SysQuartzJobLogMapper
 * @author yf_自动生成
 * @email 115889198@qq.com
 * @date 2019-09-13 00:03:42
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