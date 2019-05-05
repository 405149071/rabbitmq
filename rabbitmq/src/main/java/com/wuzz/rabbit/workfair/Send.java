package com.wuzz.rabbit.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_workfair_queue";
    /**
     * 生产者
     * @param args
     */
    public static void main(String args[]) throws IOException, TimeoutException, InterruptedException

    {
        // 先拿到连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 常见队列声明 （队列持久化）
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //每次回执之前只发送一条
        channel.basicQos(1);

        //发送50条消息
        for (int i = 0; i < 50 ; i++) {
            String msg = "hello " + i;
            // 消息持久化
            channel.basicPublish("",QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());

            System.out.println("---send msg-" + msg);

            Thread.sleep(i*5);

        }


        channel.close();
        connection.close();
    }
}
