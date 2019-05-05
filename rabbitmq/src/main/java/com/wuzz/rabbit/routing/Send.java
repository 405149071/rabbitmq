package com.wuzz.rabbit.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String args[]) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg = "hello direct";


        String routeKey = "error";
        channel.basicPublish(EXCHANGE_NAME,routeKey,null,msg.getBytes());

        System.out.println("---send msg" + msg);

        channel.close();
        connection.close();

    }

}
