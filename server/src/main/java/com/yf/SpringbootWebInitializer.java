package com.yf;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
* @ClassName: SpringbootWebInitializer
* @Description: web容器中进行部署
*
*/
public class SpringbootWebInitializer  extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(SpringbootStart.class);
    }
}
