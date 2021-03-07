package com.taotao.myktdistributedluck.lock;

/**
 * @author wangjin
 * @title: Lock
 * @description； 项目
 * @date 2021/3/7 11:28
 */

public interface Lock {
    /**
     * 获取锁
     */
    void getLock() throws InterruptedException;
    /**
     * 释放锁
     */
    void unLock();
}
