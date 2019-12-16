package com.yf.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yf.common.base.BaseController;
import com.yf.model.custom.TitleVo;
import io.swagger.annotations.Api;

@Controller
@Api(value = "系统工具类")
@RequestMapping("ToolController")
public class ToolController  extends BaseController{
	//跳转页面参数
	private String prefix = "admin/tool";
	@GetMapping("view")
    public String view(ModelMap model){
		String str="工具";
		setTitle(model, new TitleVo("列表", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
}
