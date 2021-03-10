
package com.mayikt.api.impl.feign;

import com.mayikt.api.weixin.WeChatService;
import org.springframework.cloud.openfeign.FeignClient;

/**
*@title: WeChatServiceFeign
*@description； 项目
*@author wangjin
*@date 2021/3/10 20:52
*/  

@FeignClient("mayikt-weixin")
public interface WeChatServiceFeign  extends WeChatService {
}
