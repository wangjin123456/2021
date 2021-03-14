package com.taotao.myktdistributedluck.lock.impl;


import com.taotao.myktdistributedluck.LockContext;
import com.taotao.myktdistributedluck.utils.ZkClientUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName ZkTemporaryOrderNodeLock 解决羊群效应问题
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@Component
public class ZkTemporaryOrderNodeLock extends ZookeeperAbstractTemplateLock {
    /**
     * 分布式锁 路径
     */
    private String lockParent = "/lock";
    // 临时顺序编号节点
    private String lockPath = "/lockPath";

    // 上一个临时顺序编号节点
    private String prevLockPath;
    private CountDownLatch countDownLatch;


    @Override
    public void waitLock() throws InterruptedException {
        //阻塞等待被唤醒
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //当节点删除后我们应该从新唤醒
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }

            }
        };
        ZkClientUtils.getZkClient().subscribeDataChanges(prevLockPath, iZkDataListener);
        try {
            //让当前线程阻塞
            if (ZkClientUtils.getZkClient().exists(prevLockPath)) {
                //让当前线程阻塞
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
            }

        } catch (Exception e) {
            e.getCause();
        }
        //当我们节点被删除以后，我们应该重新唤醒，移除事件监听
        ZkClientUtils.getZkClient().unsubscribeDataChanges(prevLockPath, iZkDataListener);
    }


    @Override
    public boolean tryLock() {
        //1创建一个临时编号节点
        String tempNodeName = LockContext.get();
        if (StringUtils.isEmpty(tempNodeName)) {
            tempNodeName = ZkClientUtils.newZkClient().createEphemeralSequential(lockParent + lockPath, "lock");
            LockContext.set(tempNodeName);
        }
        //2 查询到当前根节点
        List<String> childrens = ZkClientUtils.getZkClient().getChildren(lockParent);
        if (childrens != null) {

        }
        Collections.sort(childrens);
        //3查找到当前最小的节点，实现比较，如果为最小节点，则表示获取锁成功
        if (tempNodeName.equals(lockParent + "/" + childrens.get(0))) {
            return true;
        }
        //4如果不足为最小节点的情况下，则表示获取锁失败，失败的情况下
        int index = Collections.binarySearch(childrens, tempNodeName.substring(lockParent.length() + 1));

        //5 查找到我当前对应的上一个节点是谁
        prevLockPath = lockParent + "/" + childrens.get(index - 1);

        //6 采用事件监听监听到上一个节点
        return false;
    }
}
