package com.wuzz.rabbit.topic;

import com.rabbitmq.client.*;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {

    private static final String EXCHANGE_NAME = "test_exchange_topic";
    private static final String QUEUE_NAME = "test_queue_topic_2";

    public static void main(String args[]) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);


        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"goods.#");


        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");


        //每次回执之前只发送一条
        channel.basicQos(1);

        // 事件触发的回调函数
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 一旦有消息进入队列，则触发此方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgStr = new String(body, "utf-8");
                System.out.println("[2] recv msg " + msgStr);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done 'good##'");
                    // 手动发送回执
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //  监听队列
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}
