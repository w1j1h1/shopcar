package com.project.shopcar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(value = "com.project.shopcar.mapper")//扫描指定文件夹下的mapper
@ServletComponentScan   //扫描注入的组件
@SpringBootApplication
public class ShopcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopcarApplication.class, args);
    }

}
