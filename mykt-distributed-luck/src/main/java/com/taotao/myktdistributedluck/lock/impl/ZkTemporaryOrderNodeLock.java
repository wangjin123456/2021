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
    public boolean tryLock() {
        // 死循环问题
        // 1.创建一个临时顺序编号节点
        String tempNodeName = LockContext.get();
        if (StringUtils.isEmpty(tempNodeName)) {
            tempNodeName = ZkClientUtils.newZkClient().createEphemeralSequential(lockParent + lockPath, "lock");
            LockContext.set(tempNodeName);
        }
        // 2.查询到当前根节点下所有的子节点 实现排序
        List<String> childrens = ZkClientUtils.getZkClient().getChildren(lockParent);
//        if (childrens == null) {
//               // 异常 bug
//        }
        Collections.sort(childrens);
        // 3.查找到最小的节点 实现比较 如果是为最小的节点 则表示获取锁成功
        if (tempNodeName.equals(lockParent + "/" + childrens.get(0))) {
            return true;
        }
        // 4.如果不是为最小节点的情况下，则表示获取锁失败 失败的情况下
        // childrens 子节点=lockPath01 tempNodeName=/lock/lockPath01
        int index = Collections.binarySearch(childrens, tempNodeName.substring(lockParent.length() + 1));
        // 5.查找到我当前对应的上一个节点是谁
        prevLockPath = lockParent + "/" + childrens.get(index - 1);
        // 5.采用事件监听监听到我上一个节点
        return false;
    }

    @Override
    public void waitLock() {

        // 阻塞等待 被唤醒
        // 创建一个事件监听
        IZkDataListener iZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //当我们节点被删除之后，我们应该从新被唤醒
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        };
        ZkClientUtils.getZkClient().subscribeDataChanges(prevLockPath, iZkDataListener);
        try {
            if (ZkClientUtils.getZkClient().exists(prevLockPath)) {
                //让当前线程阻塞
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
            }

        } catch (Exception e) {

        }
        // 当我们节点被删除之后，我们应该从新被唤醒 移除事件监听
        ZkClientUtils.getZkClient().unsubscribeDataChanges(prevLockPath, iZkDataListener);
    }


}
