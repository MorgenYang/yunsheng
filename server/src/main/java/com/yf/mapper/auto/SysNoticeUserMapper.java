package com.yf.mapper.auto;

import com.yf.model.auto.SysNoticeUser;
import com.yf.model.auto.SysNoticeUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告_用户外键 SysNoticeUserMapper
 */
public interface SysNoticeUserMapper {
      	      	   	      	   	      	   	      
    long countByExample(SysNoticeUserExample example);

    int deleteByExample(SysNoticeUserExample example);
		
    int deleteByPrimaryKey(String id);
		
    int insert(SysNoticeUser record);

    int insertSelective(SysNoticeUser record);

    List<SysNoticeUser> selectByExample(SysNoticeUserExample example);
		
    SysNoticeUser selectByPrimaryKey(String id);
		
    int updateByExampleSelective(@Param("record") SysNoticeUser record, @Param("example") SysNoticeUserExample example);

    int updateByExample(@Param("record") SysNoticeUser record, @Param("example") SysNoticeUserExample example); 
		
    int updateByPrimaryKeySelective(SysNoticeUser record);

    int updateByPrimaryKey(SysNoticeUser record);
  	  	
}