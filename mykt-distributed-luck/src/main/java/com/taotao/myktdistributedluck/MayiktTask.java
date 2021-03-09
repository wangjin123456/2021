
package com.taotao.myktdistributedluck;


import com.taotao.myktdistributedluck.entity.LockInfo;
import com.taotao.myktdistributedluck.lock.impl.ZookeeperTemplateLock;
import com.taotao.myktdistributedluck.mapper.UserMapper;
import com.taotao.myktdistributedluck.utils.TranslationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjin
 * @title: MayiktTask
 * @description； 项目
 * @date 2021/3/7 13:13
 */
@Slf4j
@Component
public class MayiktTask {
    @Value("${server.port}")
    private String serverport;
    @Autowired
    private ZookeeperTemplateLock zookeeperTemplateLock;
    private static Map<String, LockInfo> lockInfoMap = new ConcurrentHashMap<>();
    @Autowired
    private UserMapper userMapper;
    private static final String STATE_START = "start";
    private static final String STATE_STOP = "stop";
    //创建定时任务线城池
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private TranslationUtils translationUtils;

    public MayiktTask() {
        //续命三次60s 60*3
        scheduledExecutorService.scheduleAtFixedRate(new DetectionAlgorithm(), 0, 5, TimeUnit.SECONDS);

    }

    /**
     * 每隔2s执行定时任务
     */
    @Scheduled(cron = "0/2 * * * * *")
    public synchronized void taskService() throws InterruptedException {
        //获取锁
        zookeeperTemplateLock.getLock();
        TransactionStatus beign=translationUtils.begin();
        String lockId = UUID.randomUUID().toString();
        lockInfoMap.put(lockId, new LockInfo(lockId, Thread.currentThread(), STATE_START, zookeeperTemplateLock,translationUtils,beign));
        //事务：
        translationUtils.begin();
        userMapper.insert("mykt", 20);
        translationUtils.commit(beign);
        log.info("[{}]正在调用阿里云发送短信" + serverport);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        zookeeperTemplateLock.unLock();
    }

    /**
     * 检测续命的线程
     */
    class DetectionAlgorithm implements Runnable {
        @Override
        public void run() {
            lockInfoMap.forEach((k, lockInfo) -> {
                if (STATE_START.equals(lockInfo.getState())) {
                    //手动关闭连接；
                    lockInfo.getLock().unLock();
                    //2 回滚
                      lockInfo.getTranslationUtils().rollback(lockInfo.getTransactionStatus());
                    //3线程停止
                    lockInfo.getLockThread().interrupt();
                    //4避免重复检测：
                    lockInfoMap.remove(k);
                }
            });
        }
    }
}
