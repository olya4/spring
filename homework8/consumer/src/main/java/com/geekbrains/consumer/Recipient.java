package com.geekbrains.consumer;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class Recipient {
    // имя обменника
    private static final String EXCHANGE_NAME = "MultiTopicsDirect";

    public static void main(String[] argv) throws Exception {
        // фабрика, кот.открывает соединение с сервером RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        // установить хост
        factory.setHost("localhost");
        // открыть соединение
        Connection connection = factory.newConnection();
        // открыть канал для работы с сервером
        // любое взаимодействие с сервером будет происходить через канал
        Channel channel = connection.createChannel();
        // объявление обменника
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // получить имя временной очереди
        String queueName = channel.queueDeclare().getQueue();
        // увязать временную очередь с обменником и указать тему
        channel.queueBind(queueName, EXCHANGE_NAME, "php");

        Scanner scan = new Scanner(System.in);
        System.out.println("Введите команду");
        String s = scan.nextLine();
        if (s.equalsIgnoreCase("set_topic php")) {
            // Слушатель сообщений из очереди
            // когда в очередь попадет сообщение - будет реакция

            // реакция происходит в отдельном потоке

            // delivery - сообщение из очереди (массив байтов)
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // собрать строчку из байтов (сообщения из очереди)
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(Thread.currentThread().getName() + " [x] Received '" + message + "'");
            };
            // на очередь(QUEUE_NAME) повесить слушателя (deliverCallback),
            // кот. при получении сообщения из очереди сделает consumerTag
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } else {
            System.out.println("Неверная команда");

        }
    }
}
