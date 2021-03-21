package com.taotao.myktdistributedluck.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName CuratorZkLock   zk框架实现分布式锁
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@Component
public class CuratorZkLock {


    // ZooKeeper 服务地址, 单机格式为:(127.0.0.1:2181),
    // 集群格式为:(127.0.0.1:2181)
    private String connectString;
    // Curator 客户端重试策略
    private RetryPolicy retry;
    // Curator 客户端对象
    private CuratorFramework client;

    public CuratorZkLock() throws Exception {
        init();
    }

    public void init() throws Exception {
        // 设置 ZooKeeper 服务地址为本机的 2181 端口
        connectString = "127.0.0.1:2181";
        // 重试策略
        // 初始休眠时间为 1000ms, 最大重试次数为 3
        retry = new ExponentialBackoffRetry(1000, 3);
        // 创建一个客户端, 60000(ms)为 session 超时时间, 15000(ms)为连接超时时间
        client = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        client.start();
    }

    public void close() {
        CloseableUtils.closeQuietly(client);
    }

    public CuratorFramework getClient() {
        return client;
    }
}
