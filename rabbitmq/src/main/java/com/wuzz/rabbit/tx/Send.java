package com.wuzz.rabbit.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String args[]) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        
        String msg = "hello tx message";
        
        try {
            channel.txSelect();

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            int i = 1/0;
            channel.txCommit();
            System.out.println("send msg ok!");

        }catch (Exception e){
            channel.txRollback();
            System.out.println(" send msg rollback");
        }

        channel.close();
        connection.close();

    }
}
