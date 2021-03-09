package com.taotao.myktdistributedluck.service;


import com.taotao.myktdistributedluck.lock.impl.ZkTemporaryOrderNodeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MyService
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@RestController
@Slf4j
public class MyService {
    @Autowired
    private ZkTemporaryOrderNodeLock zkTemporaryOrderNodeLock;

    @RequestMapping("/testLock")
    public String testLock() {
        try {
            zkTemporaryOrderNodeLock.getLock();
            log.info("获取锁成功 执行业务逻辑");
            zkTemporaryOrderNodeLock.unLock();
        } catch (Exception e) {
            zkTemporaryOrderNodeLock.unLock();
        }
        return "ok";
    }
}
