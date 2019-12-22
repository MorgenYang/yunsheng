package com.ys.web.domain;

import com.ys.common.annotation.Excel;
import com.ys.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 学生信息表对象 ys_student
 * 
 * @author ys
 * @date 2019-12-21
 */
public class YsStudent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long studentId;

    /** 学生名称 */
    @Excel(name = "学生名称")
    private String studentName;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer studentAge;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String studentSex;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String studentStatus;

    /** 生日 */
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date studentBirthday;

    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setStudentAge(Integer studentAge) 
    {
        this.studentAge = studentAge;
    }

    public Integer getStudentAge() 
    {
        return studentAge;
    }
    public void setStudentSex(String studentSex) 
    {
        this.studentSex = studentSex;
    }

    public String getStudentSex() 
    {
        return studentSex;
    }
    public void setStudentStatus(String studentStatus) 
    {
        this.studentStatus = studentStatus;
    }

    public String getStudentStatus() 
    {
        return studentStatus;
    }
    public void setStudentBirthday(Date studentBirthday) 
    {
        this.studentBirthday = studentBirthday;
    }

    public Date getStudentBirthday() 
    {
        return studentBirthday;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("studentId", getStudentId())
            .append("studentName", getStudentName())
            .append("studentAge", getStudentAge())
            .append("studentSex", getStudentSex())
            .append("studentStatus", getStudentStatus())
            .append("studentBirthday", getStudentBirthday())
            .append("remark", getRemark())
            .toString();
    }
}
