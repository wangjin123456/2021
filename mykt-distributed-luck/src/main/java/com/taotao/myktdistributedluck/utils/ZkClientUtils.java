package com.taotao.myktdistributedluck.utils;

import org.I0Itec.zkclient.ZkClient;

/**
 * @ClassName ZkClientUtils
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public class ZkClientUtils {
    private static ZkClient zkClient = null;

    public static ZkClient getZkClient() {
        return zkClient;
    }

    public static ZkClient newZkClient() {
        if (zkClient != null) {
            zkClient.close();
        }
        zkClient = new ZkClient("127.0.0.1:2181", 5000);
        return zkClient;
    }
}
