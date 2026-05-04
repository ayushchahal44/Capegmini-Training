package com.capg.ayush.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Configuration to disable JWT filter for gateway service.
 * This allows the gateway to handle authentication requests without JWT validation.
 */
@Configuration
public class JwtFilterConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebFilter jwtFilterDisabler() {
        return new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                String path = exchange.getRequest().getURI().getPath();
                
                // Skip JWT processing for auth endpoints and OPTIONS requests
                if (path.startsWith("/gateway/auth/") || 
                    path.startsWith("/gateway/public/") ||
                    path.startsWith("/actuator/") ||
                    exchange.getRequest().getMethod().name().equals("OPTIONS")) {
                    return chain.filter(exchange);
                }
                
                return chain.filter(exchange);
            }
        };
    }
}
