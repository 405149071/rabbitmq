package com.wuzz.rabbit.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String args[]) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg = "商品....";


        String routeKey = "goods.dele";
        channel.basicPublish(EXCHANGE_NAME,routeKey,null,msg.getBytes());

        System.out.println("---send msg" + routeKey + " " + msg);

        channel.close();
        connection.close();

    }

}
