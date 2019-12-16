package com.yf.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.common.base.BaseService;
import com.yf.common.support.Convert;
import com.yf.mapper.auto.SysQuartzJobLogMapper;
import com.yf.model.auto.SysQuartzJobLog;
import com.yf.model.auto.SysQuartzJobLogExample;
import com.yf.model.custom.Tablepar;
import com.yf.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调度日志表 SysQuartzJobLogService 
 **/
@Service
public class SysQuartzJobLogService implements BaseService<SysQuartzJobLog, SysQuartzJobLogExample>{
	@Autowired
	private SysQuartzJobLogMapper sysQuartzJobLogMapper;


	/**
	 * 分页查询
	 * @param tablepar
	 * @param name
	 * @return
	 */
	 public PageInfo<SysQuartzJobLog> list(Tablepar tablepar,String name){
	        SysQuartzJobLogExample testExample=new SysQuartzJobLogExample();
	        testExample.setOrderByClause("id DESC");
	        if(name!=null&&!"".equals(name)){
	        	testExample.createCriteria().andJobNameLike("%"+name+"%");
	        }

	        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
	        List<SysQuartzJobLog> list= sysQuartzJobLogMapper.selectByExample(testExample);
	        PageInfo<SysQuartzJobLog> pageInfo = new PageInfo<SysQuartzJobLog>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
		List<String> lista=Convert.toListStrArray(ids);
		SysQuartzJobLogExample example=new SysQuartzJobLogExample();
		example.createCriteria().andIdIn(lista);
		return sysQuartzJobLogMapper.deleteByExample(example);
	}
	
	
	@Override
	public SysQuartzJobLog selectByPrimaryKey(String id) {
		return sysQuartzJobLogMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(SysQuartzJobLog record) {
		return sysQuartzJobLogMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(SysQuartzJobLog record) {
		//添加雪花主键id
		record.setId(SnowflakeIdWorker.getUUID());
		return sysQuartzJobLogMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(SysQuartzJobLog record, SysQuartzJobLogExample example) {
		return sysQuartzJobLogMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(SysQuartzJobLog record, SysQuartzJobLogExample example) {
		return sysQuartzJobLogMapper.updateByExample(record, example);
	}

	@Override
	public List<SysQuartzJobLog> selectByExample(SysQuartzJobLogExample example) {
		return sysQuartzJobLogMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(SysQuartzJobLogExample example) {
		return sysQuartzJobLogMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(SysQuartzJobLogExample example) {
		return sysQuartzJobLogMapper.deleteByExample(example);
	}
	
	/**
	 * 检查name
	 * @param sysQuartzJobLog
	 * @return
	 */
	public int checkNameUnique(SysQuartzJobLog sysQuartzJobLog){
		SysQuartzJobLogExample example=new SysQuartzJobLogExample();
		example.createCriteria().andJobNameEqualTo(sysQuartzJobLog.getJobName());
		List<SysQuartzJobLog> list=sysQuartzJobLogMapper.selectByExample(example);
		return list.size();
	}
}
