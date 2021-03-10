package com.mayikt.api.member;

import com.mayikt.api.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

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
}
