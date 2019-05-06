package com.wuzz.rabbit.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class Send {


        private static final String QUEUE_NAME = "test_queue_confirm1";

        public static void main (String args[]) throws IOException, TimeoutException, InterruptedException {

            Connection connection = ConnectionUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String msg = "hello tx confirm1";

            //生产者调用，讲channel设置为confirm模式
            channel.confirmSelect();
            // 单条发送
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            // 多条发送
            for (int i = 0; i <5 ; i++) {
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            }
            //  确认
            if(!channel.waitForConfirms()){
                System.out.println("message send faile");
            }else
            {
                System.out.println("send confirm1 ok");
            }

            channel.close();
            connection.close();
        }

}