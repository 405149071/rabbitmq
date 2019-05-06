package com.wuzz.rabbit.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.wuzz.rabbit.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send3 {


        private static final String QUEUE_NAME = "test_queue_confirm3";

        public static void main (String args[]) throws IOException, TimeoutException, InterruptedException {

            Connection connection = ConnectionUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String msg = "hello tx confirm3";

            //生产者调用，讲channel设置为confirm模式
            channel.confirmSelect();

            // 未确认的消息标识
            final SortedSet<Long> confirmset = Collections.synchronizedSortedSet(new TreeSet<Long>());

            channel.addConfirmListener(new ConfirmListener() {
                // 没有问题的handleACK
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    if(multiple){
                        System.out.println("--handleACK--- multiple");
                        confirmset.headSet(deliveryTag + 1).clear();
                    }else{
                        System.out.println("--handleACK--- multiple false");
                        confirmset.remove(deliveryTag);
                    }
                }
                // handle nack
                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    if(multiple){
                        System.out.println("--handleACK--- multiple");
                        confirmset.headSet(deliveryTag + 1).clear();
                    }else{
                        System.out.println("--handleACK--- multiple false");
                        confirmset.remove(deliveryTag);
                    }
                }
            });

            String msgStr = "sssss";

            while(true){
                long segNo = channel.getNextPublishSeqNo();
                channel.basicPublish("",QUEUE_NAME,null,msgStr.getBytes());
                confirmset.add(segNo);
            }


//            channel.close();
//            connection.close();
        }

}