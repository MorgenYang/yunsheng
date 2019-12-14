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
import com.yf.model.auto.SysCity;
import com.yf.model.custom.TableSplitResult;
import com.yf.model.custom.Tablepar;
import com.yf.model.custom.TitleVo;
import com.yf.service.SysCityService;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("SysCityController")
@Api(value = "城市设置")
public class SysCityController extends BaseController{
	
	private String prefix = "admin/province/sysCity";
	@Autowired
	private SysCityService sysCityService;
	
	@GetMapping("view")
	@RequiresPermissions("gen:sysCity:view")
    public String view(ModelMap model)
    {	
		String str="城市设置";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
	
	//@Log(title = "城市设置集合查询", action = "111")
	@PostMapping("list")
	@RequiresPermissions("gen:sysCity:list")
	@ResponseBody
	public Object list(Tablepar tablepar,String searchTxt){
		PageInfo<SysCity> page=sysCityService.list(tablepar,searchTxt) ; 
		TableSplitResult<SysCity> result=new TableSplitResult<SysCity>(page.getPageNum(), page.getTotal(), page.getList()); 
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
	
	//@Log(title = "城市设置新增", action = "111")
	@PostMapping("add")
	@RequiresPermissions("gen:sysCity:add")
	@ResponseBody
	public AjaxResult add(SysCity sysCity){
		int b=sysCityService.insertSelective(sysCity);
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
	//@Log(title = "城市设置删除", action = "111")
	@PostMapping("remove")
	@RequiresPermissions("gen:sysCity:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=sysCityService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 检查用户
	 * @param tsysUser
	 * @return
	 */
	@PostMapping("checkNameUnique")
	@ResponseBody
	public int checkNameUnique(SysCity sysCity){
		int b=sysCityService.checkNameUnique(sysCity);
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
        mmap.put("SysCity", sysCityService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "城市设置修改", action = "111")
    @RequiresPermissions("gen:sysCity:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCity record)
    {
        return toAjax(sysCityService.updateByPrimaryKeySelective(record));
    }

    
    

	
}
