package com.ys.web.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ys.web.mapper.YsStudentMapper;
import com.ys.web.domain.YsStudent;
import com.ys.web.service.IYsStudentService;
import com.ys.common.core.text.Convert;

/**
 * 学生信息表Service业务层处理
 * 
 * @author ys
 * @date 2019-12-21
 */
@Service
public class YsStudentServiceImpl implements IYsStudentService 
{
    @Autowired
    private YsStudentMapper ysStudentMapper;

    /**
     * 查询学生信息表
     * 
     * @param studentId 学生信息表ID
     * @return 学生信息表
     */
    @Override
    public YsStudent selectYsStudentById(Long studentId)
    {
        return ysStudentMapper.selectYsStudentById(studentId);
    }

    /**
     * 查询学生信息表列表
     * 
     * @param ysStudent 学生信息表
     * @return 学生信息表
     */
    @Override
    public List<YsStudent> selectYsStudentList(YsStudent ysStudent)
    {
        return ysStudentMapper.selectYsStudentList(ysStudent);
    }

    /**
     * 新增学生信息表
     * 
     * @param ysStudent 学生信息表
     * @return 结果
     */
    @Override
    public int insertYsStudent(YsStudent ysStudent)
    {
        return ysStudentMapper.insertYsStudent(ysStudent);
    }

    /**
     * 修改学生信息表
     * 
     * @param ysStudent 学生信息表
     * @return 结果
     */
    @Override
    public int updateYsStudent(YsStudent ysStudent)
    {
        return ysStudentMapper.updateYsStudent(ysStudent);
    }

    /**
     * 删除学生信息表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYsStudentByIds(String ids)
    {
        return ysStudentMapper.deleteYsStudentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除学生信息表信息
     * 
     * @param studentId 学生信息表ID
     * @return 结果
     */
    @Override
    public int deleteYsStudentById(Long studentId)
    {
        return ysStudentMapper.deleteYsStudentById(studentId);
    }
}
