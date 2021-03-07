
package com.taotao.myktdistributedluck.lock.impl;

import com.taotao.myktdistributedluck.lock.Lock;
import com.taotao.myktdistributedluck.utils.ZkClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wangjin
 * @title: ZookeeperAbstractTemplateLock
 * @description； 模板引擎方法
 * @date 2021/3/7 11:30
 */
@Slf4j
public abstract class ZookeeperAbstractTemplateLock implements Lock {
    @Value("${server.port}")
    private String serverport;

    /**
     * 模板方法设计模式 定义共同的业务逻辑骨架，不同的算法交于实现类
     */
  /*  @Override
    public void getLock() throws InterruptedException {
        //1，调用tryLock方法获取锁
        if (tryLock()) {
            log.info("获取锁成功{}", serverport);
        } else {
            //2 获取锁失败，jvm阻塞
            waitLock();
            //3 唤醒之后从新进入到获取锁的状态
            getLock();
        }
    }
*/
    public abstract void waitLock() throws InterruptedException;

    public abstract boolean tryLock();

    @Override
    public void unLock() {
        //关闭连接，释放锁
        ZkClientUtils.getZkClient().close();
    }
@Override
public  void getLock() throws InterruptedException {
        //调用trylock获取锁
    for (int i = 0; i < 5; i++) {
        boolean result=tryLock();
        if(result){
            return;
        }
    }
    waitLock();
    getLock();
}
}
