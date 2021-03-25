
package com.mayikt.api.impl.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.impl.base.BaseApiService;
import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.entity.UserInfoDo;
import com.mayikt.api.impl.entity.UserLoginLogDo;
import com.mayikt.api.impl.manage.UserLoginLogManage;
import com.mayikt.api.impl.mapper.UserInfoMapper;
import com.mayikt.api.impl.member.dto.req.UserLoginDto;
import com.mayikt.api.impl.utils.DesensitizationUtil;
import com.mayikt.api.impl.utils.MD5Util;
import com.mayikt.api.impl.utils.TokenUtils;
import com.mayikt.api.producer.LoginProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author wangjin
 * @title: LoginServiceImpl
 * @description； 项目
 * @date 2021/3/21 21:08
 */
@Slf4j
@RestController
public class LoginServiceImpl extends BaseApiService implements LoginService {
    @Autowired
    private UserLoginLogManage userLoginLogManage;
    @Autowired
    private UserInfoMapper userInfoMapper;
   @Autowired
   private TokenUtils tokenUtils;
   @Autowired
   private LoginProducer loginProducer;
    @Override
    public BaseResponse<JSONObject> login(UserLoginDto userLoginDto) {
        // 验证参数
        String mobile = userLoginDto.getMobile();
        if (StringUtils.isEmpty(userLoginDto.getMobile())) {
            return setResultError("mobile 不能为空!");
        }
        String passWord = userLoginDto.getPassWord();
        if (StringUtils.isEmpty(userLoginDto.getPassWord())) {
            return setResultError("passWord 不能为空!");
        }
        // 从请求头中获取 其他信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String deviceInfor = request.getHeader("deviceInfor");
        String channel = request.getHeader("channel");
        String sourceIp = request.getHeader("sourceIp");
        String newpassword = MD5Util.MD5(passWord);
        QueryWrapper<UserInfoDo> userLoginDtoQueryWrapper = new QueryWrapper<>();
        userLoginDtoQueryWrapper.eq("MOBILE", mobile);
        userLoginDtoQueryWrapper.eq("PASSWORD", newpassword);
        UserInfoDo userInfoDo = userInfoMapper.selectOne(userLoginDtoQueryWrapper);
        if (userInfoDo == null) {
            return setResultError("手机或者密码错误");
        }
        long userId= userInfoDo.getUserId();
        //生成令牌
         String usertoken=tokenUtils.createToken(userId+"");
          JSONObject data=new JSONObject();
          data.put("userToken",usertoken);
        log.info(">>>登录成功:userToken{}<<<",usertoken);
          //异步单独线程处理
        loginProducer.sendMsgLoginFollowUp( userId,  sourceIp,  new Date(),  usertoken,  channel,
                 deviceInfor,  userInfoDo.getWxOpenId(),   DesensitizationUtil.mobileEncrypt(mobile));
     /*   userLoginLogManage.asyncLoginLog(new UserLoginLogDo(userId,sourceIp,new Date(),usertoken,
                channel,deviceInfor));*/
        return setResultSuccess(data);
    }
}
