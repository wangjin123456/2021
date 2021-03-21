
package com.mayikt.api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
*@title: AppMember
*@description； 项目
*@author wangjin
*@date 2021/3/10 20:56
*/
@MapperScan("com.mayikt.api.impl.mapper")
@SpringBootApplication
@EnableFeignClients
public class AppMember {
    public  static  void main(String [] args){
        SpringApplication.run(AppMember.class);

    }
}
