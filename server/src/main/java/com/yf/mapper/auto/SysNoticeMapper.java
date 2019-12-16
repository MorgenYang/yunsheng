package com.yf.mapper.auto;

import com.yf.model.auto.SysNotice;
import com.yf.model.auto.SysNoticeExample;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 公告 SysNoticeMapper
 */
public interface SysNoticeMapper {
      	      	   	      	   	      	   	      	   	      	   	      	   	      
    long countByExample(SysNoticeExample example);

    int deleteByExample(SysNoticeExample example);
		
    int deleteByPrimaryKey(String id);
		
    int insert(SysNotice record);

    int insertSelective(SysNotice record);

    List<SysNotice> selectByExample(SysNoticeExample example);
		
    SysNotice selectByPrimaryKey(String id);
		
    int updateByExampleSelective(@Param("record") SysNotice record, @Param("example") SysNoticeExample example);

    int updateByExample(@Param("record") SysNotice record, @Param("example") SysNoticeExample example); 
		
    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);
  	  	
}