package com.mayikt.api.impl.weixin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangjin
 * @title: WeChatService
 * @description； 项目
 * @date 2021/3/10 20:34
 */

public interface WeChatService {

    @GetMapping("/getWeChat")
    String getWeChat(@RequestParam("a") Integer a);
}
