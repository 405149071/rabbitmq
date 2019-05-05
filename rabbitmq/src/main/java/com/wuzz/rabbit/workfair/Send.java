package com.wuzz.rabbit.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
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
        // 常见队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //每次回执之前只发送一条
        channel.basicQos(1);

        //发送50条消息
        for (int i = 0; i < 50 ; i++) {
            String msg = "hello " + i;

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            System.out.println("---send msg-" + msg);

            Thread.sleep(i*5);

        }


        channel.close();
        connection.close();
    }
}
