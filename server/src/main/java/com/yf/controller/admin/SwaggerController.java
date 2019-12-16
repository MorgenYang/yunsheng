package com.yf.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yf.common.base.BaseController;
import com.yf.model.custom.TitleVo;
import io.swagger.annotations.Api;

@Controller
@Api(value = "SwaggerController")
@RequestMapping("SwaggerController")
public class SwaggerController  extends BaseController{

	//跳转页面参数
	private String prefix = "admin/swagger";
	
	@GetMapping("view")
	@RequiresPermissions("system:swagger:view")
    public String view(ModelMap model){
		String str="API文档";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
}
