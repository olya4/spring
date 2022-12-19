package com.geekbrains.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    // имя обменника
    private static final String EXCHANGE_NAME = "MultiTopicsDirect";

    public static void main(String[] argv) throws Exception {
        // фабрика, кот.открывает соединение с сервером RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        // установить хост
        factory.setHost("localhost");
        // открыть соединение
        try (Connection connection = factory.newConnection();
             // открыть канал для работы с сервером
             // любое взаимодействие с сервером будет происходить через канал
             Channel channel = connection.createChannel()) {
            // объявление обменника
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // сообщение
            String message = "php some message";
            String[] words = message.split(" ");
            String someMessage = words[1] + " " + words[2];

            if (words[0].equalsIgnoreCase("php")) {
                // в  обменник послать сообщения с темой php
                channel.basicPublish(EXCHANGE_NAME, "php", null,someMessage.getBytes("UTF-8"));
            }
            if (words[0].equalsIgnoreCase("java")) {
                // в обменник послать сообщения с темой java
                channel.basicPublish(EXCHANGE_NAME, "java", null, someMessage.getBytes("UTF-8"));
            }
            if (words[0].equalsIgnoreCase("python")) {
                // в обменник послать сообщения с темой python
                channel.basicPublish(EXCHANGE_NAME, "python", null, someMessage.getBytes("UTF-8"));
            }
            System.out.println("OK");
        }
    }
}
