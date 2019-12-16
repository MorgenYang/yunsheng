package com.yf.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yf.common.base.BaseController;
import com.yf.model.custom.Service;
import com.yf.model.custom.TitleVo;
import io.swagger.annotations.Api;

/**
 * 服务器信息Controller
 */
@Controller
@Api(value = "服务器信息")
@RequestMapping("ServiceController")
public class ServiceController extends BaseController{

	//跳转页面参数
	private String prefix = "admin/service";
	
	@GetMapping("view")
	@RequiresPermissions("system:service:view")
    public String view(ModelMap model){
		String str="服务器";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
		model.addAttribute("service", new Service());
        return prefix + "/list";
    }
}
