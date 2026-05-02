/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration for the Notification Service.
 * Binds to the finflow exchange to consume all application status events.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "finflow.exchange";
    public static final String QUEUE_NOTIFICATIONS = "finflow.notifications";
    public static final String ROUTING_KEY = "application.status.#";

    @Bean
    public TopicExchange finflowExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATIONS, true);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange finflowExchange) {
        return BindingBuilder.bind(notificationQueue).to(finflowExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}
