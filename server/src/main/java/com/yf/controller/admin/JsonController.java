package com.yf.controller.admin;

import com.yf.common.base.BaseController;
import com.yf.model.custom.TitleVo;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(value = "Json格式化")
@RequestMapping("Json")
public class JsonController extends BaseController{

	private String prefix = "admin/json";

	@GetMapping("view")
    public String view(ModelMap model)
    {	
		String str="json";
		setTitle(model, new TitleVo("json格式化", str+"管理", true,"欢迎进入"+str+"页面", true, false));
        return prefix + "/list";
    }
}
