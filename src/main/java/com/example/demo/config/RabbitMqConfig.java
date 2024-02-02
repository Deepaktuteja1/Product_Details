package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfig {


    @Bean
    public Queue queue() {
        return new Queue("Products", true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("myDirectExchange");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {

        return BindingBuilder.bind(queue).to(directExchange).with("myRoutingKey");
    }
}
