
package com.mayikt.api.impl.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.impl.entity.UserLoginLogDo;
import com.mayikt.api.impl.mapper.UserLoginLogMapper;
import com.mayikt.api.impl.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wangjin
 * @title: UserLoginLogManage
 * @description； 项目
 * @date 2021/3/22 20:50
 */

@Component
@Slf4j
public class UserLoginLogManage {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Async("taskExecutor")
    public void asyncLoginLog(UserLoginLogDo userLoginLogDo) {
        //唯一登录

        uniqueLogin(userLoginLogDo);
        //登录日志
        insert(userLoginLogDo);
    }

    public  void uniqueLogin(UserLoginLogDo userLoginLogDo){
        QueryWrapper<UserLoginLogDo> userLoginLogDoQueryWrapper = new QueryWrapper<>();
        userLoginLogDoQueryWrapper.eq("user_id",userLoginLogDo.getUserId());
        userLoginLogDoQueryWrapper.eq("channel",userLoginLogDo.getChannel());
        UserLoginLogDo dbUserLoginLogDo = userLoginLogMapper.selectOne(userLoginLogDoQueryWrapper);
       //之前没登录过
        if(dbUserLoginLogDo==null){
            return;
        }
        int result = userLoginLogMapper.deleteById(dbUserLoginLogDo.getId());
        if(result>0){
            // 清除之前的token
            redisUtil.delKey(dbUserLoginLogDo.getLoginToken());
        }
    }

    private void insert(UserLoginLogDo userLoginLogDo){
        int insert = userLoginLogMapper.insert(userLoginLogDo);
        log.info(">>>db中插入日志记录<<<");
    }
}
