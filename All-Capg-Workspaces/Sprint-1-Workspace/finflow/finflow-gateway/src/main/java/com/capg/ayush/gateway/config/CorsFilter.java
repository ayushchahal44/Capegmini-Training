package com.capg.ayush.gateway.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Custom CORS filter to handle preflight requests properly
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements WebFilter {

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // Handle preflight OPTIONS requests
        if (request.getMethod().name().equals("OPTIONS")) {
            String origin = request.getHeaders().getFirst(HttpHeaders.ORIGIN);
            String accessControlRequestMethod = request.getHeaders().getFirst(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD);
            String accessControlRequestHeaders = request.getHeaders().getFirst(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);

            if (origin != null && accessControlRequestMethod != null) {
                // Check if origin is allowed
                if (isAllowedOrigin(origin)) {
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, accessControlRequestHeaders);
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
                    response.setStatusCode(HttpStatus.OK);
                    return response.setComplete();
                }
            }
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // Handle actual requests with Origin header
        String origin = request.getHeaders().getFirst(HttpHeaders.ORIGIN);
        if (origin != null && isAllowedOrigin(origin)) {
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.getHeaders().set(HttpHeaders.VARY, HttpHeaders.ORIGIN);
        }

        return chain.filter(exchange);
    }

    private boolean isAllowedOrigin(String origin) {
        return origin.equals("http://localhost:4200") || origin.equals("http://localhost:62322");
    }
}
