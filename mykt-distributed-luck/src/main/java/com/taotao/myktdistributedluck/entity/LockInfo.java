
package com.taotao.myktdistributedluck.entity;

import com.taotao.myktdistributedluck.lock.Lock;
import com.taotao.myktdistributedluck.utils.TranslationUtils;
import lombok.Data;
import org.springframework.transaction.TransactionStatus;

/**
*@title: LockInfo
*@description； 项目
*@author wangjin
*@date 2021/3/8 21:13
*/  
@Data
public class LockInfo {
    /**
     * lockId
     */
    private  String lockId;
    /**
     * 锁的线程
     */
    private  Thread lockThread;

    /**
     * 状态 start 开始使用的状态 stop状态 锁释放了
     */
   private  String state;
    /**
     * 保存分布式锁
     */
    private Lock lock;
    /**
     * 记录事务的信息
     */
    private TranslationUtils translationUtils;


    private TransactionStatus transactionStatus;

    /**
     * 事务
     *
     * @param lockId
     * @param lockThread
     * @param state
     * @param lock
     */

    public LockInfo(String lockId, Thread lockThread, String state, Lock lock, TranslationUtils translationUtils,
                    TransactionStatus transactionStatus) {
        this.lockId = lockId;
        this.lockThread = lockThread;
        this.state = state;
        this.lock = lock;
        this.translationUtils = translationUtils;
        this.transactionStatus = transactionStatus;
    }

    public LockInfo(String lockId, Thread lockThread, String state, Lock lock) {
        this.lockId = lockId;
        this.lockThread = lockThread;
        this.state = state;
        this.lock = lock;
    }
}
