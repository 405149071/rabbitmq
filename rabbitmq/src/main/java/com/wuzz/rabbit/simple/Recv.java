package com.wuzz.rabbit.simple;

import com.rabbitmq.client.*;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private static final String QUEUE_NAME = "test_simple_queue";
    /**
     * 消费者
     * @param args
     */
    public static void main(String args[]) throws IOException,TimeoutException,ShutdownSignalException,ConsumerCancelledException,InterruptedException

    {
        // 先拿到连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();

        // 3.4之前的老的版本的消费方法
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);

        while(true){

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            String msgStr = new String (delivery.getBody());

            System.out.println("RECV msg : " + msgStr);

        }



    }
}
