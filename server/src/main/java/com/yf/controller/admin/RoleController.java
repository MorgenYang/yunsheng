package com.yf.controller.admin;

import com.github.pagehelper.PageInfo;
import com.yf.common.base.BaseController;
import com.yf.common.domain.AjaxResult;
import com.yf.model.auto.TsysRole;
import com.yf.model.custom.BootstrapTree;
import com.yf.model.custom.TableSplitResult;
import com.yf.model.custom.Tablepar;
import com.yf.model.custom.TitleVo;
import com.yf.shiro.util.ShiroUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 角色Controller
 * @author yf
 */
@Controller
@Api(value = "用户角色")
@RequestMapping("RoleController")
public class RoleController extends BaseController{
	
	
	//跳转页面参数
	private String prefix = "admin/role";
	
	@GetMapping("view")
	@RequiresPermissions("system:role:view")
    public String view(ModelMap model)
    {	
		String str="角色";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
	
	/**
	 * 角色列表
	 * @param tablepar
	 * @param searchTxt 搜索字符
	 * @return
	 */
	@PostMapping("list")
	@RequiresPermissions("system:user:list")
	@ResponseBody
	public Object list(Tablepar tablepar,String searchTxt){
		PageInfo<TsysRole> page=sysRoleService.list(tablepar,searchTxt) ; 
		TableSplitResult<TsysRole> result=new TableSplitResult<TsysRole>(page.getPageNum(), page.getTotal(), page.getList()); 
		return  result;
	}
	
	/**
     * 新增角色
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }
	
	
    /**
     * 角色添加
     * @param role
     * @return
     */
	@PostMapping("add")
	@RequiresPermissions("system:user:add")
	@ResponseBody
	public AjaxResult add(TsysRole role,String prem){
		int b=sysRoleService.insertRoleAndPrem(role,prem);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 删除角色
	 * @param ids
	 * @return
	 */
	@PostMapping("remove")
	@RequiresPermissions("system:user:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=sysRoleService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 检查角色
	 * @param TsysRole
	 * @return
	 */
	@PostMapping("checkNameUnique")
	@ResponseBody
	public int checkNameUnique(TsysRole tsysRole){
		int b=sysRoleService.checkNameUnique(tsysRole);
		if(b>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	
	/**
	 * 修改角色
	 * @param id
	 * @param mmap
	 * @return
	 */
	@GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") String id, ModelMap mmap)
    {
        mmap.put("TsysRole", sysRoleService.selectByPrimaryKey(id));
        return prefix + "/edit";
    }
	
	/**
     * 修改保存角色
     */
    @RequiresPermissions("system:user:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TsysRole tsysRole,String prem,HttpServletRequest request)
    {
    	int i=sysRoleService.updateRoleAndPrem(tsysRole,prem);
    	if(i>0) {
    		//大于0刷新权限
    		ShiroUtils.clearCachedAuthorizationInfo();
    		//获取菜单栏
        	BootstrapTree bootstrapTree= sysPermissionService.getbooBootstrapTreePerm(ShiroUtils.getUserId());
        	request.getSession().setAttribute("bootstrapTree", bootstrapTree);
    	}
		
        return toAjax(i);
    }
}
