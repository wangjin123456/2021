package com.mayikt.member.consumer;

/**
 * 微信模板的消费者
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import com.mayikt.api.impl.base.BaseResponse;
import com.mayikt.api.impl.weixin.dto.req.LoginReminderReqDto;
import com.mayikt.member.consumer.feign.WechatTemplateServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RabbitListener(queues = "fanout_wechattemplate_queue")
public class WechatTemplateConsumer {
@Autowired
private WechatTemplateServiceFeign wechatTemplateServiceFeign;
    @RabbitHandler
    public  void process(String msg){
        log.info("微信模板获取消息,msg:"+msg);
        LoginReminderReqDto loginReminderReqDto=JSONObject.parseObject(msg,LoginReminderReqDto.class);

      BaseResponse<String> stringBaseResponse= wechatTemplateServiceFeign.sendTemplate(loginReminderReqDto);
      log.info("stringBaseResponse:{}",stringBaseResponse);
    }

}