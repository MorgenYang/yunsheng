package com.ys.web.controller;

import com.ys.common.annotation.Log;
import com.ys.common.core.controller.BaseController;
import com.ys.common.core.domain.AjaxResult;
import com.ys.common.core.page.TableDataInfo;
import com.ys.common.enums.BusinessType;
import com.ys.common.utils.poi.ExcelUtil;
import com.ys.web.domain.YsStudent;
import com.ys.web.service.IYsStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生信息表Controller
 * 
 * @author ys
 * @date 2019-12-21
 */
@Controller
@RequestMapping("/web/student")
public class YsStudentController extends BaseController
{
    private String prefix = "web/student";

    @Autowired
    private IYsStudentService ysStudentService;

    @RequiresPermissions("web:student:view")
    @GetMapping()
    public String student()
    {
        return prefix + "/student";
    }

    /**
     * 查询学生信息表列表
     */
    @RequiresPermissions("web:student:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YsStudent ysStudent)
    {
        startPage();
        List<YsStudent> list = ysStudentService.selectYsStudentList(ysStudent);
        return getDataTable(list);
    }

    /**
     * 导出学生信息表列表
     */
    @RequiresPermissions("web:student:export")
    @Log(title = "学生信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YsStudent ysStudent)
    {
        List<YsStudent> list = ysStudentService.selectYsStudentList(ysStudent);
        ExcelUtil<YsStudent> util = new ExcelUtil<YsStudent>(YsStudent.class);
        return util.exportExcel(list, "student");
    }

    /**
     * 新增学生信息表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存学生信息表
     */
    @RequiresPermissions("web:student:add")
    @Log(title = "学生信息表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YsStudent ysStudent)
    {
        return toAjax(ysStudentService.insertYsStudent(ysStudent));
    }

    /**
     * 修改学生信息表
     */
    @GetMapping("/edit/{studentId}")
    public String edit(@PathVariable("studentId") Long studentId, ModelMap mmap)
    {
        YsStudent ysStudent = ysStudentService.selectYsStudentById(studentId);
        mmap.put("ysStudent", ysStudent);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生信息表
     */
    @RequiresPermissions("web:student:edit")
    @Log(title = "学生信息表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YsStudent ysStudent)
    {
        return toAjax(ysStudentService.updateYsStudent(ysStudent));
    }

    /**
     * 删除学生信息表
     */
    @RequiresPermissions("web:student:remove")
    @Log(title = "学生信息表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(ysStudentService.deleteYsStudentByIds(ids));
    }
}
