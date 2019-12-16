package com.yf.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.yf.common.base.BaseController;
import com.yf.common.domain.AjaxResult;
import com.yf.model.auto.SysProvince;
import com.yf.model.custom.TableSplitResult;
import com.yf.model.custom.Tablepar;
import com.yf.model.custom.TitleVo;
import com.yf.service.SysProvinceService;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("SysProvinceController")
@Api(value = "省份表")
public class SysProvinceController extends BaseController{
	
	private String prefix = "admin/province/sysProvince";
	@Autowired
	private SysProvinceService sysProvinceService;
	
	@GetMapping("view")
	@RequiresPermissions("gen:sysProvince:view")
    public String view(ModelMap model)
    {	
		String str="省份表";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
	
	//@Log(title = "省份表集合查询", action = "111")
	@PostMapping("list")
	@RequiresPermissions("gen:sysProvince:list")
	@ResponseBody
	public Object list(Tablepar tablepar,String searchTxt){
		PageInfo<SysProvince> page=sysProvinceService.list(tablepar,searchTxt) ; 
		TableSplitResult<SysProvince> result=new TableSplitResult<SysProvince>(page.getPageNum(), page.getTotal(), page.getList()); 
		return  result;
	}
	
	/**
     * 新增
     */

    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        return prefix + "/add";
    }
	
	//@Log(title = "省份表新增", action = "111")
	@PostMapping("add")
	@RequiresPermissions("gen:sysProvince:add")
	@ResponseBody
	public AjaxResult add(SysProvince sysProvince){
		int b=sysProvinceService.insertSelective(sysProvince);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	//@Log(title = "省份表删除", action = "111")
	@PostMapping("remove")
	@RequiresPermissions("gen:sysProvince:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=sysProvinceService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 检查用户
	 * @param sysProvince
	 * @return
	 */
	@PostMapping("checkNameUnique")
	@ResponseBody
	public int checkNameUnique(SysProvince sysProvince){
		int b=sysProvinceService.checkNameUnique(sysProvince);
		if(b>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param mmap
	 * @return
	 */
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        mmap.put("SysProvince", sysProvinceService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "省份表修改", action = "111")
    @RequiresPermissions("gen:sysProvince:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProvince record)
    {
        return toAjax(sysProvinceService.updateByPrimaryKeySelective(record));
    }

    
    

	
}
