package com.mayikt.api.impl.weixin;

import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.weixin.dto.req.LoginReminderReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangjin
 * @title: WechatTemplateService
 * @description； 项目
 * @date 2021/3/20 17:45
 */

public interface WechatTemplateService {
    @RequestMapping("sendTemplate")
    BaseResponse<String> sendTemplate(@RequestBody LoginReminderReqDto LoginReminderReqDto);
}