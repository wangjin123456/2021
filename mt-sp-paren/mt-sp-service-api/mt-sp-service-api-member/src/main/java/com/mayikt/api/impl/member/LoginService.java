package com.mayikt.api.impl.member;

import com.alibaba.fastjson.JSONObject;

import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.member.dto.req.UserLoginDto;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public interface LoginService {
    /**
     * 提供登录接口
     * @param userLoginDto
     * @return
     */
    @RequestMapping("/login")
    BaseResponse<JSONObject> login(@RequestBody UserLoginDto userLoginDto);
}