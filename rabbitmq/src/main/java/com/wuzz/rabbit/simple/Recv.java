package com.wuzz.rabbit.simple;

import com.rabbitmq.client.*;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private static final String QUEUE_NAME = "test_simple_queue";

    /**
     * 消费者 (新方法)
     * @param args
     */
    public static void main(String args[]) throws IOException,TimeoutException,ShutdownSignalException,ConsumerCancelledException,InterruptedException

    {
        // 先拿到连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取一个通道
        Channel channel = connection.createChannel();
        // 常见队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 事件触发的回调函数

        DefaultConsumer consumer =  new DefaultConsumer(channel){
            // 一旦有消息进入队列，则触发此方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgStr = new String(body,"utf-8");
                System.out.println("new RECV " + msgStr);
            }
        };
        //  监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);


    }

    /**
     * 消费者 (老方法)
     * @param args
     */
    public static void oldmain(String args[]) throws IOException,TimeoutException,ShutdownSignalException,ConsumerCancelledException,InterruptedException

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
