
package com.mayikt.api.impl.wexin;

import com.mayikt.api.weixin.WeChatService;
import org.springframework.web.bind.annotation.RestController;

/**
*@title: WeChatServiceImpl
*@description； 项目
*@author wangjin
*@date 2021/3/10 20:37
*/  
@RestController
public class WeChatServiceImpl implements WeChatService {
    @Override
    public String getWeChat(Integer a) {
        return "hellow wang"+a;
    }
}
