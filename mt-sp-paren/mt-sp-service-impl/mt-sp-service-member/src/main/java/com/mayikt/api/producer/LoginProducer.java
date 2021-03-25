
package com.mayikt.api.producer;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.api.impl.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
*@title: LoginProducer
*@description； 项目
*@author wangjin
*@date 2021/3/24 22:58
*/  
@Component
@Slf4j
public class LoginProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 登录后一步处理的代码
     * @param userId
     * @param sourceIp
     * @param date
     * @param userToken
     * @param channel
     * @param deviceInfor
     * @param wxOpenId
     * @param phone
     */
    public  void sendMsgLoginFollowUp(Long userId, String sourceIp, Date date, String userToken, String channel,
                                  String deviceInfor, String wxOpenId, String phone) {

        /**
         * 1.交换机名称
         * 2.路由key名称
         * 3.发送内容
         */

        JSONObject data = new JSONObject();
        data.put("userId", userId);
        data.put("loginIp", sourceIp);
        data.put("loginTime", date);
        data.put("loginToken", userToken);
        data.put("channel", channel);
        data.put("equipment", deviceInfor);
        data.put("openId", wxOpenId);
        data.put("phone", phone);
        amqpTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_MAYIKT_NAME, "", data.toJSONString());
        log.info(">>登录之后投递mq消息，异步处理后续操作..<<");
}











}
