/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.config;

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
 * Configuration for RabbitMQ messaging in the Application Service.
 * Sets up exchanges, queues, and bindings for application status events.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME  = "finflow.exchange";
    public static final String QUEUE_APP_STATUS = "finflow.application.status";
    public static final String ROUTING_KEY     = "application.status.#";

    /**
     * Configures the topic exchange for FinFlow.
     * @return The configured TopicExchange
     */
    @Bean
    public TopicExchange finflowExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    /**
     * Configures the queue for application status changes.
     * @return The configured Queue
     */
    @Bean
    public Queue applicationStatusQueue() {
        return new Queue(QUEUE_APP_STATUS, true);
    }

    /**
     * Binds the application status queue to the topic exchange with a specific routing key.
     * @param applicationStatusQueue The queue to bind
     * @param finflowExchange The exchange to bind to
     * @return The configured Binding
     */
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
