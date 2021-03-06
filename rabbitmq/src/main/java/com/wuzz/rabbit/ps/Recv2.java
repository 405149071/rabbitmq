package com.wuzz.rabbit.ps;

import com.rabbitmq.client.*;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {

    private static final String QUEUE_NAME = "test_queue_fandout_sms";

    private static final String EXCHANGE_NAME = "test_exchange_fandout";


    public static void main(String args[]) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        // 队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定队列到交换机转发器
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done sms");
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
