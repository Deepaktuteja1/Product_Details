package com.example.demo;
import com.example.demo.config.RabbitMqConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RabbitMqConfigTests {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMqConfig rabbitMqConfig;

    @Test
    void queue_ShouldCreateQueueBean() {

        Queue queue = rabbitMqConfig.queue();


        assertNotNull(queue);
        assertEquals("Products", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void directExchange_ShouldCreateDirectExchangeBean() {

        DirectExchange directExchange = rabbitMqConfig.directExchange();


        assertNotNull(directExchange);
        assertEquals("myDirectExchange", directExchange.getName());
    }

    
}