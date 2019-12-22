package com.ys.web.service;

import com.ys.web.domain.YsStudent;
import java.util.List;

/**
 * 学生信息表Service接口
 * 
 * @author ys
 * @date 2019-12-21
 */
public interface IYsStudentService 
{
    /**
     * 查询学生信息表
     * 
     * @param studentId 学生信息表ID
     * @return 学生信息表
     */
    public YsStudent selectYsStudentById(Long studentId);

    /**
     * 查询学生信息表列表
     * 
     * @param ysStudent 学生信息表
     * @return 学生信息表集合
     */
    public List<YsStudent> selectYsStudentList(YsStudent ysStudent);

    /**
     * 新增学生信息表
     * 
     * @param ysStudent 学生信息表
     * @return 结果
     */
    public int insertYsStudent(YsStudent ysStudent);

    /**
     * 修改学生信息表
     * 
     * @param ysStudent 学生信息表
     * @return 结果
     */
    public int updateYsStudent(YsStudent ysStudent);

    /**
     * 批量删除学生信息表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYsStudentByIds(String ids);

    /**
     * 删除学生信息表信息
     * 
     * @param studentId 学生信息表ID
     * @return 结果
     */
    public int deleteYsStudentById(Long studentId);
}
