
package com.taotao.myktdistributedluck.test01;

import org.I0Itec.zkclient.ZkClient;

/**
*@title: Test01
*@description； 项目
*@author wangjin
*@date 2021/3/6 21:10
*/  

public class Test01 {

   private static ZkClient zkClient= new ZkClient("127.0.0.1:2181",5000);
    public static void main(String[] args) {
             /*zkClient.createEphemeral("/mayiketemp");
             try {
                 Thread.sleep(10000);
             }catch (Exception e){
                 e.getCause();
             }*/
        zkClient.createEphemeralSequential("/lock", "lock");
        zkClient.createEphemeralSequential("/lock", "lock");
        zkClient.createEphemeralSequential("/lock", "lock");
           //  zkClient.close();
        while (true) {

        }
    }
}
