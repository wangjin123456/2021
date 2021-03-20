package com.mayikt.api.impl.wexin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wechat_keywords")
public class WechatKeywordDo {
    private Long id;
    private String keywordName;
    private String keywordValue;
    private Date createTime;
    private Date updateTime;
    private Long version;
    @TableLogic
    private int isDelete;
}