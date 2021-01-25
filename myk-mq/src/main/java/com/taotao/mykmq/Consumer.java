
package com.taotao.mykmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
*@title: Consumer
*@description； 项目
*@author taotao
*@date 2021/1/25 10:24
*/  

public class Consumer {
    private  static  final String QUEUE_NAME="taotaoqueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1创建连接
        Connection connection = RabbitMQConnection.getConnection();
        //2 创建通道
        Channel channel = connection.createChannel();
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"UTF-8");
                System.out.println("消费者获取消息："+msg);
            }
        };
        //3监听队列
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }


}
