
package com.mayikt.api.impl.member;

import com.mayikt.api.impl.base.BaseApiService;
import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.entity.UserDo;
import com.mayikt.api.impl.feign.WeChatServiceFeign;
import com.mayikt.api.impl.mapper.UserMapper;
import com.mayikt.api.impl.member.dto.req.UserReqDto;
import com.mayikt.api.impl.member.dto.resp.UserRespDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import com.alibaba.fastjson.JSON;
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
    @Autowired
    private UserMapper userMapper;
    @Override
    public String memberToWeiXin(Integer a) {
        return weChatServiceFeign.getWeChat(a);
    }
    @Value("${mayikt.userName}")
    private String userName;
    @Override
    public BaseResponse<String> addMember(String userName, String pwd, Integer age) {
       if(Strings.isBlank(userName)){
           return  setResultError("username is null error");
       }
       int j=1/0;

        return setResultSuccess("success");
    }

    @Override
    public Object updateUser(@RequestBody Map<String, String> map) {
        String jsonStr = JSON.toJSONString(map);
        UserDo user = JSON.parseObject(jsonStr, UserDo.class);
        Integer userId = user.getUserId();
        if (userId == null) {
            return setResultError("userId is null");
        }
        if (userMapper.updateById(user) <= 0) {
            return setResultError("修改失败");
        }
        //2.查询最新的修改的数据直接返回
        UserDo userEntity = userMapper.selectById(userId);
        return userEntity == null ? setResultError("查询修改数据失败") : userEntity;
    }

    @Override
    public BaseResponse<String> updateUserDto(UserReqDto userReqDto) {
        // 1.验证参数
        // 2.userReqDto 转换成 do
        UserDo userReqDo = dtoToDo(userReqDto, UserDo.class);
        if (userMapper.updateById(userReqDo) <= 0) {
            return setResultError("修改失败");
        }
        //2.查询最新的修改的数据直接返回
        Integer userId = userReqDo.getUserId();
        UserDo userRespDo = userMapper.selectById(userId);
        //3.do转换成dto
        UserRespDto userRespDto = doToDto(userRespDo, UserRespDto.class);
        return userRespDto == null ? setResultError("查询修改数据失败") : setResultSuccess(userRespDto.toString());
    }

    @Override
    public String getTestConfig() {
        return userName;
    }
}
