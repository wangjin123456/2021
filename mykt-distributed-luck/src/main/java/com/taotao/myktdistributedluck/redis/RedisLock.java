package com.taotao.myktdistributedluck.redis;

/**
 * @ClassName RedisLock
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public interface RedisLock {
    /**
     * 获取锁
     *
     * @return
     */
    boolean tryLock();

    /**
     * 释放锁
     *
     * @return
     */
    boolean releaseLock();
}
