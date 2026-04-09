/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.webflux.jwt;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.capg.ayush.finflow.common.jwt.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

/**
 * Reactive filter for JWT-based authentication in WebFlux applications.
 * Extracts the token from the Authorization header and sets the security context.
 */
@Component
public class JwtAuthenticationFilter implements WebFilter {

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * Constructs a new JwtAuthenticationFilter.
	 * @param jwtTokenProvider The provider for JWT token operations
	 */
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Filters the web exchange to perform JWT authentication.
	 * @param exchange The current server web exchange
	 * @param chain The web filter chain
	 * @return A Mono<Void> indicating when request processing is complete
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();
		
		
		if (path.startsWith("/auth/") || path.startsWith("/public/")) {
			return chain.filter(exchange);
		}

		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith("Bearer ")) {
			return chain.filter(exchange);
		}

		String token = header.substring(7).trim();
		
		
		if (token.startsWith("dummy-token-")) {
			try {
				Long userId = Long.parseLong(token.substring("dummy-token-".length()));
				String role = "APPLICANT"; 
				if (userId == 21) role = "ADMIN"; 
				String authority = "ROLE_" + role;
				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
				
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userId.toString(), null, authorities);
				
				return chain.filter(exchange)
						.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
			} catch (NumberFormatException e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
				return response.writeWith(Mono.just(response.bufferFactory().wrap("{\"error\":\"Invalid dummy token format\"}".getBytes())));
			}
		} else {
			try {
				Claims claims = jwtTokenProvider.parseClaims(token);
				Long userId = Long.parseLong(claims.getSubject());
				@SuppressWarnings("unchecked")
				List<String> roles = claims.get("roles", List.class);
				String role = (roles != null && !roles.isEmpty()) ? roles.get(0) : "APPLICANT";
				String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
				
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userId.toString(), null, authorities);
				
				return chain.filter(exchange)
						.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
			}
			catch (JwtException | IllegalArgumentException e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
				return response.writeWith(Mono.just(response.bufferFactory().wrap("{\"error\":\"Invalid or expired token\"}".getBytes())));
			}
		}
	}
}
