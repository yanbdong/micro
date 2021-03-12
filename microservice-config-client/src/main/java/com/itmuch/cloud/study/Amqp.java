package com.itmuch.cloud.study;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanbdong@cienet.com.cn
 * @since Mar 11, 2021
 */
@Configuration
class Amqp {

    @Value("${spring.rabbitmq.queue_name}")
    private String queueName;

    @Value("${spring.rabbitmq.exchange_name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing_key}")
    private String routingKey;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Queue rabbitQueue() {
        /**
         * 1、name: 队列名称 2、durable: 是否持久化 3、exclusive: 是否独享、排外的。如果设置为true，定义为排他队列。则只有创建者可以使用此队列。也就是private私有的。
         * 4、autoDelete: 是否自动删除。也就是临时队列。当最后一个消费者断开连接后，会自动删除。
         */
        return new Queue(this.queueName, true, false, false);
    }

    @Bean
    public DirectExchange rabbitmqDemoDirectExchange() {
        // Direct交换机
        return new DirectExchange(this.exchangeName, true, false);
    }

    @Bean
    public Binding bindDirect() {
        // 链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder
                // 绑定队列
                .bind(rabbitQueue())
                // 到交换机
                .to(rabbitmqDemoDirectExchange())
                // 并设置匹配键
                .with(routingKey);
    }

    // 初始化rabbitAdmin对象，使得队列一开始就存在
    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(this.connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        // 创建交换机
        rabbitAdmin.declareExchange(rabbitmqDemoDirectExchange());
        // 创建队列
        rabbitAdmin.declareQueue(rabbitQueue());
        return rabbitAdmin;
    }

}
