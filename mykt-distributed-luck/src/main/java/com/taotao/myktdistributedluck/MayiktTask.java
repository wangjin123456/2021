
package com.taotao.myktdistributedluck;


import com.taotao.myktdistributedluck.lock.impl.ZookeeperTemplateLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
*@title: MayiktTask
*@description； 项目
*@author wangjin
*@date 2021/3/7 13:13
*/  
@Slf4j
@Component
public class MayiktTask {
    @Value("${server.port}")
    private String serverport;
     @Autowired
     private ZookeeperTemplateLock zookeeperTemplateLock;
    /**
     * 每隔2s执行定时任务
     */
    @Scheduled(cron = "0/2 * * * * *")
    public  synchronized  void taskService() throws InterruptedException {
        zookeeperTemplateLock.getLock();
        log.info("[{}]正在调用阿里云发送短信"+serverport);
        zookeeperTemplateLock.unLock();
    }
}
