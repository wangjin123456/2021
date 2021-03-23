package com.taotao.myktdistributedluck.entity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName RedisLockInfo
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public class RedisLockInfo {
    /**
     * 锁的状态 state为true 则表示获取锁成功
     */
    private boolean state;
    /**
     * 锁的id
     */
    private String lockId;
    /**
     * 锁的持有线程
     */
    private Thread lockThread;

    /**
     * 锁的过期时间
     */
    private Long expire;

    /**
     * 续命次数
     */
    private AtomicInteger lifeCount;

    // 锁的可重入次数

    public RedisLockInfo(String lockId, Thread lockThread, Long expire) {
        state = true;
        this.lockId = lockId;
        this.lockThread = lockThread;
        this.expire = expire;
        lifeCount = new AtomicInteger(0);
    }

    public RedisLockInfo(Thread lockThread, Long expire) {
        this.lockThread = lockThread;
        this.expire = expire;
        lifeCount = new AtomicInteger(0);
        state = true;
    }

    public RedisLockInfo() {
        state = true;
    }

    public String getLockId() {
        return lockId;
    }

    public boolean isState() {
        return state;
    }

    public Thread getLockThread() {
        return lockThread;
    }

    public Long getExpire() {
        return expire;
    }

    public Integer getLifeCount() {
        return lifeCount.incrementAndGet();
    }
}
