package com.yf.common.quartz.task;

import com.yf.mapper.auto.TsysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 *测试类
 */
@Component("v2Task")
public class V2Task {
	@Autowired
	private TsysUserMapper tsysUserMapper;
    /**
     * 无参的任务
     */
    public void runTask1(){
        System.out.println("正在执行定时任务，无参方法");
    }

    /**
     * 有参任务
     * 目前仅执行常见的数据类型  Integer Long  带L  string  带 ''  bool Double 带 d
     * @param a
     * @param b
     */
    public void runTask2(Integer a,Long b,String c,Boolean d,Double e) {
//    	List<TsysUser> list=  tsysUserMapper.selectByExample(new TsysUserExample());
//    	System.err.println("用户查询num："+list.size());
        System.out.println("正在执行定时任务，带多个参数的方法"+a+"   "+b+" "+c+"  "+d+" "+e+"执行时间:"+new Date().toLocaleString());
    }
}
