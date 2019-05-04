package com.wuzz.rabbit.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";
    /**
     * 生产者
     * @param args
     */
    public static void main(String args[]) throws IOException,TimeoutException

    {
        // 先拿到连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 常见队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simple";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("---send msg-" + msg);

        channel.close();
        connection.close();
    }

}
