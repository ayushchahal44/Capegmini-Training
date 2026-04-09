/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.config;

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
 * Configuration for RabbitMQ messaging in the Document Service.
 * Configures exchanges, queues, and bindings for consuming status events.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME    = "finflow.exchange";
    public static final String QUEUE_APP_STATUS = "finflow.application.status";
    public static final String ROUTING_KEY      = "application.status.#";

    /**
     * Defines the topic exchange for FinFlow events.
     * @return The TopicExchange instance
     */
    @Bean
    public TopicExchange finflowExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue applicationStatusQueue() {
        return new Queue(QUEUE_APP_STATUS, true);
    }

    @Bean
    public Binding applicationStatusBinding(Queue applicationStatusQueue, TopicExchange finflowExchange) {
        return BindingBuilder.bind(applicationStatusQueue).to(finflowExchange).with(ROUTING_KEY);
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
