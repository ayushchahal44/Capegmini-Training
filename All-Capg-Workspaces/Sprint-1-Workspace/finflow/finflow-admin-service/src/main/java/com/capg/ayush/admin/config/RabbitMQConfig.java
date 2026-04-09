/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for RabbitMQ messaging in the Admin Service.
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_APP_STATUS = "finflow.application.status";

    /**
     * Configures the message converter to use Jackson for JSON serialization.
     * @return The configured MessageConverter
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
