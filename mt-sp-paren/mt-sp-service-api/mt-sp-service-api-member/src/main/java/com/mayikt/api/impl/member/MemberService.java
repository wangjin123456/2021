package com.mayikt.api.impl.member;

import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.member.dto.req.UserReqDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author wangjin
 * @title: MemberService
 * @description； 项目
 * @date 2021/3/10 20:43
 */

public interface MemberService {
    @GetMapping("memberToWeiXin")
    String memberToWeiXin(Integer a);
    @GetMapping
    BaseResponse<String> addMember(String userName, String pwd, Integer age);

    @PostMapping("/updateUser")
    Object updateUser(@RequestBody Map<String, String> map);


    @PostMapping("updateUserDto")
    BaseResponse<String> updateUserDto(@RequestBody UserReqDto userReqDto);
    @RequestMapping("/getTestConfig")
    String getTestConfig();
}
