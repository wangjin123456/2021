
package com.taotao.mykmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author taotao
 * @title: Producer
 * @description； 项目
 * @date 2021/1/25 10:13
 */

public class Producer {
    private static final String QUEUE_NAME = "taotaoqueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1创建连接
        Connection connection = RabbitMQConnection.getConnection();

        //2 创建通道
        Channel channel = connection.createChannel();
        String msg = "6trtrt660";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        //3,关闭通道和连接
        channel.close();
        connection.close();

    }
}
