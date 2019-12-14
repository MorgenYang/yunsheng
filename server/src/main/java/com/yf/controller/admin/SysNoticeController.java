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
import com.yf.model.auto.SysNotice;
import com.yf.model.custom.TableSplitResult;
import com.yf.model.custom.Tablepar;
import com.yf.model.custom.TitleVo;
import com.yf.service.SysNoticeService;
import com.yf.shiro.util.ShiroUtils;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("SysNoticeController")
@Api(value = "公告")
public class SysNoticeController extends BaseController{
	
	private String prefix = "admin/sysNotice";
	@Autowired
	private SysNoticeService sysNoticeService;
	
	
	
	@GetMapping("view")
	@RequiresPermissions("gen:sysNotice:view")
    public String view(ModelMap model)
    {	
		String str="公告";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
	
	//@Log(title = "公告集合查询", action = "111")
	@PostMapping("list")
	@RequiresPermissions("gen:sysNotice:list")
	@ResponseBody
	public Object list(Tablepar tablepar,String searchTxt){
		PageInfo<SysNotice> page=sysNoticeService.list(tablepar,searchTxt) ; 
		TableSplitResult<SysNotice> result=new TableSplitResult<SysNotice>(page.getPageNum(), page.getTotal(), page.getList()); 
		return  result;
	}
	
	
	
	@GetMapping("viewUser")
    public String viewUser(ModelMap model)
    {	
		String str="公告";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list_view";
    }
	/**
	 * 根据公告id查询跳转到公告详情页面
	 * @param id
	 * @param mmap
	 * @return
	 */
	@PostMapping("viewUserlist")
	@ResponseBody
    public Object viewUserlist(Tablepar tablepar,String searchTxt)
    {
        
		PageInfo<SysNotice> page=sysNoticeService.list(ShiroUtils.getUser(), tablepar, searchTxt);
		TableSplitResult<SysNotice> result=new TableSplitResult<SysNotice>(page.getPageNum(), page.getTotal(), page.getList()); 
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
	
	//@Log(title = "公告新增", action = "111")
	@PostMapping("add")
	@RequiresPermissions("gen:sysNotice:add")
	@ResponseBody
	public AjaxResult add(SysNotice sysNotice){
		int b=sysNoticeService.insertSelective(sysNotice);
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
	//@Log(title = "公告删除", action = "111")
	@PostMapping("remove")
	@RequiresPermissions("gen:sysNotice:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=sysNoticeService.deleteByPrimaryKey(ids);
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
	public int checkNameUnique(SysNotice sysNotice){
		int b=sysNoticeService.checkNameUnique(sysNotice);
		if(b>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 根据公告id查询跳转到公告详情页面
	 * @param id
	 * @param mmap
	 * @return
	 */
	@GetMapping("/viewinfo/{id}")
    public String viewinfo(@PathVariable("id") String id,ModelMap mmap)
    {
        
		String str="公告";
		setTitle(mmap, new TitleVo("详情", str+"列表", true,"欢迎进入"+str+"详情页面", true, false));
		SysNotice notice= sysNoticeService.selectByPrimaryKey(id);
		mmap.addAttribute("notice", notice);
		//把推送给该用户的公告设置为已读
		sysNoticeService.editUserState(id);
        return prefix + "/view";
    }
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param mmap
	 * @return
	 */
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        mmap.put("SysNotice", sysNoticeService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "公告修改", action = "111")
    @RequiresPermissions("gen:sysNotice:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysNotice record)
    {
        return toAjax(sysNoticeService.updateByPrimaryKeySelective(record));
    }

    
    

	
}
