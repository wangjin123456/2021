
package com.mayikt.api.impl.impl.member;


import com.mayikt.api.impl.base.BaseApiService;
import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.entity.UserInfoDo;
import com.mayikt.api.impl.mapper.UserInfoMapper;
import com.mayikt.api.impl.member.UserInfoService;
import com.mayikt.api.impl.member.dto.resp.UserInfoDto;
import com.mayikt.api.impl.utils.DesensitizationUtil;
import com.mayikt.api.impl.utils.TokenUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjin
 * @title: UserInfoServiceImpl
 * @description； 项目
 * @date 2021/3/21 22:53
 */

@RestController
public class UserInfoServiceImpl extends BaseApiService implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TokenUtils tokenUtils;


    @Override
    public BaseResponse<UserInfoDto> getUserInfo(String token) {
        if (token == null) {
            return setResultError("token能为空");
        }
        String tokenvalue = tokenUtils.getTokenValue(token);
        if (Strings.isEmpty(tokenvalue)) {
            return setResultError("token 不可以失效");
        }
        long userId = Long.parseLong(tokenvalue);
        UserInfoDo userInfoDt = userInfoMapper.selectById(userId);

        if (userInfoDt == null) {
            return setResultError("token错误");
        }
        UserInfoDo userInfoDto = doToDto(userInfoDt, UserInfoDo.class);
        userInfoDto.setMobile(DesensitizationUtil.mobileEncrypt(userInfoDto.getMobile()));

        return setResultSuccess(userInfoDto);
    }
}
