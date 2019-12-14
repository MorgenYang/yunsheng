package com.yf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 项目启动方法
 * @author yf
 *
 */
@SpringBootApplication
@MapperScan(value = "com.yf.mapper")
public class SpringbootStart {

    public static void main(String[] args) {
		SpringApplication.run(SpringbootStart.class, args);
	}
}
