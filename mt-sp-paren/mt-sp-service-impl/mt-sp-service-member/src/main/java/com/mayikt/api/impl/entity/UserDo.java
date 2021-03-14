package com.mayikt.api.impl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("mayikt_user")
public class UserDo {
    @TableId
    private Integer userId;
    private String userName;
    private String userPwd;
    private Integer userAge;
    private String userAddres;
    private Integer userIntegral;
}