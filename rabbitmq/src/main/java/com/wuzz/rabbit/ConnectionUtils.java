package com.wuzz.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取mq的连接
 */
public class ConnectionUtils {

    public static Connection getConnection() throws IOException,TimeoutException {
         // 定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        // 设置端口好
        factory.setPort(5672);

        //设置数据库
        factory.setVirtualHost("my_vhost");

        // 设置用户名密码
        factory.setUsername("wuzz");
        factory.setPassword("123456");

        return  factory.newConnection();
    }
}
