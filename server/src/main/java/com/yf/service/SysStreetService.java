package com.yf.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.common.base.BaseService;
import com.yf.common.support.Convert;
import com.yf.mapper.auto.SysStreetMapper;
import com.yf.model.auto.SysStreet;
import com.yf.model.auto.SysStreetExample;
import com.yf.model.custom.Tablepar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 街道设置 SysStreetService 
 **/
@Service
public class SysStreetService implements BaseService<SysStreet, SysStreetExample>{
	@Autowired
	private SysStreetMapper sysStreetMapper;


	/**
	 * 分页查询
	 * @param tablepar
	 * @param name
	 * @return
	 */
	 public PageInfo<SysStreet> list(Tablepar tablepar,String name){
		 SysStreetExample testExample=new SysStreetExample();
	        testExample.setOrderByClause("id ASC");
	        if(name!=null&&!"".equals(name)){
	        	testExample.createCriteria().andStreetNameLike("%"+name+"%");
	        }

	        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
	        List<SysStreet> list= sysStreetMapper.selectByExample(testExample);
	        PageInfo<SysStreet> pageInfo = new PageInfo<SysStreet>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
					
			Integer[] integers = Convert.toIntArray(",", ids);
			List<Integer> stringB = Arrays.asList(integers);
			SysStreetExample example=new SysStreetExample();
			example.createCriteria().andIdIn(stringB);
			return sysStreetMapper.deleteByExample(example);
			
				
	}
	
	
	@Override
	public SysStreet selectByPrimaryKey(String id) {
				
			Integer id1 = Integer.valueOf(id);
			return sysStreetMapper.selectByPrimaryKey(id1);
				
	}

	
	@Override
	public int updateByPrimaryKeySelective(SysStreet record) {
		return sysStreetMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(SysStreet record) {
					record.setId(null);
				
		return sysStreetMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(SysStreet record, SysStreetExample example) {
		
		return sysStreetMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(SysStreet record, SysStreetExample example) {
		
		return sysStreetMapper.updateByExample(record, example);
	}

	@Override
	public List<SysStreet> selectByExample(SysStreetExample example) {
		
		return sysStreetMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(SysStreetExample example) {
		
		return sysStreetMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(SysStreetExample example) {
		
		return sysStreetMapper.deleteByExample(example);
	}
	
	/**
	 * 检查name
	 * @param sysStreet
	 * @return
	 */
	public int checkNameUnique(SysStreet sysStreet){
		SysStreetExample example=new SysStreetExample();
		example.createCriteria().andStreetNameEqualTo(sysStreet.getStreetName());
		List<SysStreet> list=sysStreetMapper.selectByExample(example);
		return list.size();
	}


}