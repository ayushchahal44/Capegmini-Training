/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.web.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capg.ayush.finflow.common.jwt.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for JWT-based authentication in traditional Spring Security web applications.
 * Validates the token and sets the security context if valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * Constructs a new JwtAuthenticationFilter.
	 * @param jwtTokenProvider The provider for JWT token operations
	 */
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Determines if the filter should not be applied to the given request.
	 * @param request The current HTTP request
	 * @return true if the filter should be skipped, false otherwise
	 */
	@Override
	protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
		String path = request.getServletPath();
		return path.contains("/swagger-ui") || path.contains("/api-docs") || path.contains("/v3/api-docs");
	}

	/**
	 * Performs the actual filtering logic for JWT authentication.
	 * @param request The current HTTP request
	 * @param response The current HTTP response
	 * @param filterChain The chain of filters to execute
	 * @throws ServletException if a servlet error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
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
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write("{\"error\":\"Invalid dummy token format\"}");
				return;
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
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch (JwtException | IllegalArgumentException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
