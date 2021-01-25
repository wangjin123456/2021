
package com.taotao.mykmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
*@title: RabbitMQConnection
*@description； 项目
*@author taotao
*@date 2021/1/25 10:08
*/  

public class RabbitMQConnection {

    /**
     * 获取连接
     * @return
     */
    public  static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        //1,设置virthost
        connectionFactory.setVirtualHost("/taotao");
        //2 设置用户名和密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
         //3 mq 连接信息地址
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        return connectionFactory.newConnection();
    }
}
