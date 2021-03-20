
package com.mayikt.api.impl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
*@title: AppWeiXin
*@description； 项目
*@author wangjin
*@date 2021/3/10 20:40
*/
@MapperScan("com.mayikt.api.impl.wexin.mapper")
@SpringBootApplication
public class AppWeiXin {
    public static void main(String[] args) {
        SpringApplication.run(AppWeiXin.class);
    }
}
