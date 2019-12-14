package com.yf.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.common.base.BaseService;
import com.yf.common.support.Convert;
import com.yf.mapper.auto.TSysEmailMapper;
import com.yf.model.auto.TSysEmail;
import com.yf.model.auto.TSysEmailExample;
import com.yf.model.custom.Tablepar;
import com.yf.shiro.util.ShiroUtils;
import com.yf.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 电子邮件Service
 */
@Service
public class TSysEmailService implements BaseService<TSysEmail, TSysEmailExample>{
	@Autowired
	private TSysEmailMapper tSysEmailMapper;

	/**
	 * 分页查询
	 * @param tablepar
	 * @param name
	 * @return
	 */
	 public PageInfo<TSysEmail> list(Tablepar tablepar,String name){
	        TSysEmailExample testExample=new TSysEmailExample();
	        testExample.setOrderByClause("id desc");
	        if(name!=null&&!"".equals(name)){
	        	testExample.createCriteria().andContentLike("%"+name+"%");
	        }

	        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
	        List<TSysEmail> list= tSysEmailMapper.selectByExample(testExample);
	        PageInfo<TSysEmail> pageInfo = new PageInfo<TSysEmail>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
		List<String> lista=Convert.toListStrArray(ids);
		TSysEmailExample example=new TSysEmailExample();
		example.createCriteria().andIdIn(lista);
		return tSysEmailMapper.deleteByExample(example);
	}
	
	
	@Override
	public TSysEmail selectByPrimaryKey(String id) {
		
		return tSysEmailMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(TSysEmail record) {
		return tSysEmailMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(TSysEmail record) {
		//添加雪花主键id
		record.setId(SnowflakeIdWorker.getUUID());
		record.setSendUserId(ShiroUtils.getUserId());
		record.setSendUserName(ShiroUtils.getLoginName());
		record.setCreateTime(new Date());
		return tSysEmailMapper.insertSelective(record);
	}
	
	

	

	
	
	

	
	@Override
	public int updateByExampleSelective(TSysEmail record, TSysEmailExample example) {
		
		return tSysEmailMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(TSysEmail record, TSysEmailExample example) {
		
		return tSysEmailMapper.updateByExample(record, example);
	}

	@Override
	public List<TSysEmail> selectByExample(TSysEmailExample example) {
		
		return tSysEmailMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(TSysEmailExample example) {
		
		return tSysEmailMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(TSysEmailExample example) {
		
		return tSysEmailMapper.deleteByExample(example);
	}

	/**
	 * 检查name
	 * @param tSysEmail
	 * @return
	 */
	public int checkNameUnique(TSysEmail tSysEmail){
		TSysEmailExample example=new TSysEmailExample();
		example.createCriteria().andContentEqualTo(tSysEmail.getContent());
		List<TSysEmail> list=tSysEmailMapper.selectByExample(example);
		return list.size();
	}


}
