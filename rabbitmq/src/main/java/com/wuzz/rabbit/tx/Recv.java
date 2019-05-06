package com.wuzz.rabbit.tx;

import com.rabbitmq.client.*;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

        private static final String QUEUE_NAME = "test_queue_tx";

        public static void main (String args[]) throws IOException, TimeoutException {

            Connection connection = ConnectionUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);


            channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
                // 消息发送到队列即触发
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(" recv msg:"+ new String(body,"utf-8"));
                }
            });

        }

}
