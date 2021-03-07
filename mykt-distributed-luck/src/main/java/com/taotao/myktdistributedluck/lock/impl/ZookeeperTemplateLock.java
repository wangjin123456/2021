
package com.taotao.myktdistributedluck.lock.impl;

import com.taotao.myktdistributedluck.utils.ZkClientUtils;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author wangjin
 * @title: ZookeeperTemplateLock
 * @description； 项目
 * @date 2021/3/7 11:38
 */
@Component
public class ZookeeperTemplateLock extends ZookeeperAbstractTemplateLock {
    //分布式锁的路径
    private String lock = "/lockPath";
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void waitLock() throws InterruptedException {
        //创建事件监听
        {
            IZkDataListener iZkDataListener = new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    //当节点删除后我们应该从新唤醒
                    countDownLatch.countDown();
                }
            };
            ZkClientUtils.getZkClient().subscribeDataChanges(lock, iZkDataListener);
            try {
                //让当前线程阻塞
                countDownLatch.await();
            } catch (Exception e) {
                e.getCause();
            }
            //当我们节点被删除以后，我们应该重新唤醒，移除事件监听
            ZkClientUtils.getZkClient().unsubscribeDataChanges(lock, iZkDataListener);
        }

    }

    @Override
    public boolean tryLock() {
        /**
         * 1.获取锁方法：
         * 多个jvm同时在zk上创建一个临时节点/lockPath,
         * 最终只能够有一个jvm创建临时节点成功，如果能够创建
         * 临时节点成功jvm 表示获取锁成功能够正常执行业务逻辑，
         * 如果没有创建临时节点成功的jvm，则表示获取锁失败。
         * 获取锁失败之后，可以采用不断重试策略，重试多次
         * 获取锁失败之后，当前的jvm就进入到阻塞状态。
         */
        try {
            ZkClientUtils.newZkClient().createEphemeral(lock);
            return true;
        } catch (Exception e) {
            e.getCause();
            //获取锁失败其他jvm获取锁
            return false;
        }


    }
}
