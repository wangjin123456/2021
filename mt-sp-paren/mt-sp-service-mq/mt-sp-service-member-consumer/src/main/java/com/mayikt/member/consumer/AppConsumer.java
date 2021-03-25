
package com.mayikt.member.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
*@title: AppConsumer
*@description； 
*@author wangj
*@date 2021/3/25 21:46
*/
@EnableFeignClients
@SpringBootApplication
public class AppConsumer {
    public static void main(String[] args) {
        SpringApplication.run(AppConsumer.class);
    }
}
