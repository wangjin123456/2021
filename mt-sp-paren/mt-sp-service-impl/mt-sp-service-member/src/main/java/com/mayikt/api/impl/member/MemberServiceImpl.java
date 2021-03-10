
package com.mayikt.api.impl.member;

import com.mayikt.api.base.BaseApiService;
import com.mayikt.api.base.BaseResponse;
import com.mayikt.api.impl.feign.WeChatServiceFeign;
import com.mayikt.api.member.MemberService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjin
 * @title: MemberServiceImpl
 * @description； 项目
 * @date 2021/3/10 20:50
 */
@RestController
public class MemberServiceImpl extends BaseApiService<String> implements MemberService {
  @Autowired
  private WeChatServiceFeign weChatServiceFeign;

    @Override
    public String memberToWeiXin(Integer a) {
        return weChatServiceFeign.getWeChat(a);
    }

    @Override
    public BaseResponse<String> addMember(String userName, String pwd, Integer age) {
       if(Strings.isBlank(userName)){
           return  setResultError("username is null error");
       }
       int j=1/0;

        return setResultSuccess("success");
    }
}
