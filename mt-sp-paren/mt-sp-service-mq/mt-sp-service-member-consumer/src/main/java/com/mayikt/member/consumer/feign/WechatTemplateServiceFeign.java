package com.mayikt.member.consumer.feign;

import com.mayikt.api.impl.weixin.WechatTemplateService;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author wangj
 * @title: WechatTemplateServiceFeign
 * @description；
 * @date 2021/3/25 22:07
 */
@FeignClient("mayikt-weixin")
public interface WechatTemplateServiceFeign  extends WechatTemplateService {
}
