package com.mayikt.api.impl.member;


import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.member.dto.resp.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserInfoService {

    /**
     *
     * @param token
     * @return
     */
    @GetMapping("getUserInfo")
    BaseResponse<UserInfoDto> getUserInfo(@RequestParam("token") String token);
}