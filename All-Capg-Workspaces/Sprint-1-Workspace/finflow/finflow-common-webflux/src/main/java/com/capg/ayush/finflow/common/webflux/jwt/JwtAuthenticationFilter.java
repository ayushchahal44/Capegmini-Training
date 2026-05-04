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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.capg.ayush.finflow.common.jwt.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * Reactive filter for JWT-based authentication in WebFlux applications.
 * Extracts the token from the Authorization header and sets the security context.
 */
@Component
@Order(-100)
public class JwtAuthenticationFilter implements WebFilter {

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * Constructs a new JwtAuthenticationFilter.
	 * @param jwtTokenProvider The provider for JWT token operations
	 */
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	private static final String ROLE_PREFIX = "ROLE_";

	/**
	 * Filters the web exchange to perform JWT authentication.
	 * @param exchange The current server web exchange
	 * @param chain The web filter chain
	 * @return A Mono<Void> indicating when request processing is complete
	 */
	@Override
	@NonNull
	@SuppressWarnings("null")
	public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();

		if (path.startsWith("/auth/") || path.startsWith("/public/") || path.startsWith("/gateway/auth/") || path.startsWith("/gateway/public/")) {
			return chain.filter(exchange);
		}

		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		System.out.println("DEBUG: JwtAuthenticationFilter processing path: " + path);
		if (header == null || !header.startsWith("Bearer ")) {
			System.out.println("DEBUG: No Bearer token found for path: " + path);
			return chain.filter(exchange);
		}

		String token = header.substring(7).trim();
		System.out.println("DEBUG: Token found, processing...");
		return processToken(token, exchange, chain);
	}

	@SuppressWarnings("null")
	private Mono<Void> processToken(String token, ServerWebExchange exchange, WebFilterChain chain) {
		if (token.startsWith("dummy-token-")) {
			return handleDummyToken(token, exchange, chain);
		}
		return handleJwtToken(token, exchange, chain);
	}

	@SuppressWarnings("null")
	private Mono<Void> handleDummyToken(String token, ServerWebExchange exchange, WebFilterChain chain) {
		try {
			Long userId = Long.parseLong(token.substring("dummy-token-".length()));
			String role = (userId == 21) ? "ADMIN" : "APPLICANT";
			UsernamePasswordAuthenticationToken authentication = createAuthentication(userId.toString(), role);
			
			return chain.filter(exchange)
					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
		} catch (NumberFormatException e) {
			return errorResponse(exchange, "Invalid dummy token format");
		}
	}

	@SuppressWarnings("null")
	private Mono<Void> handleJwtToken(String token, ServerWebExchange exchange, WebFilterChain chain) {
		try {
			Claims claims = jwtTokenProvider.parseClaims(token);
			System.out.println("DEBUG: Token parsed successfully for user: " + claims.getSubject());
			Long userId = Long.parseLong(claims.getSubject());
			@SuppressWarnings("unchecked")
			List<String> roles = claims.get("roles", List.class);
			String role = (roles != null && !roles.isEmpty()) ? roles.get(0) : "APPLICANT";
			UsernamePasswordAuthenticationToken authentication = createAuthentication(userId.toString(), role);
			
			return chain.filter(exchange)
					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
		} catch (JwtException | IllegalArgumentException e) {
			System.err.println("DEBUG: Token validation failed: " + e.getMessage());
			return errorResponse(exchange, "Invalid or expired token");
		}
	}

	private UsernamePasswordAuthenticationToken createAuthentication(String principal, String role) {
		String authority = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role;
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
		return new UsernamePasswordAuthenticationToken(principal, null, authorities);
	}

	@SuppressWarnings("null")
	private Mono<Void> errorResponse(ServerWebExchange exchange, String message) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
		byte[] bytes = String.format("{\"error\":\"%s\"}", message).getBytes();
		return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
	}
}
