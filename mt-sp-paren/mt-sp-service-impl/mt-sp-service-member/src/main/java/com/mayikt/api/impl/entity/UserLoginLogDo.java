package com.mayikt.api.impl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 蚂蚁课堂创始人-余胜军QQ644064779
 * @title: userLoginLog
 * @description: 每特教育独创微服务电商项目
 * @addres www.mayikt.com
 * @date 2020/3/1218:31
 */
@Data
@TableName("user_login_log")
public class UserLoginLogDo {
    @TableId
    private Long id;
    private Long userId;

    private String loginIp;
    private Date loginTime;
    private String loginToken;
    private String channel;
    private String equipment;

    public UserLoginLogDo(Long userId, String loginIp, Date loginTime, String loginToken, String channel, String equipment) {
        this.userId = userId;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.loginToken = loginToken;
        this.channel = channel;
        this.equipment = equipment;
    }

    // 默认的情况下 查询 is_Delete=0
    @TableLogic
    private int isDelete;
}