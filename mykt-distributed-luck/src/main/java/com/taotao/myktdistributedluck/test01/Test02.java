
package com.taotao.myktdistributedluck.test01;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
*@title: Test02
*@description； 监听事件节点
*@author wangjin
*@date 2021/3/6 21:35
*/  

public class Test02 {
    private static ZkClient zkClient= new ZkClient("127.0.0.1:2181",5000);

    public static void main(String[] args) {
       zkClient.subscribeDataChanges("/mayiketemp", new IZkDataListener() {


            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("s:"+s+"被删除了 ");
            }
        });
        while (true) {

        }
    }


}
