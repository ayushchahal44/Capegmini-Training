/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.jwt;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Component for generating and validating JWT tokens.
 */
@Component
public class JwtTokenProvider {

	private final FinflowJwtProperties properties;

	/**
	 * Constructs a new JwtTokenProvider.
	 * @param properties The JWT configuration properties
	 */
	public JwtTokenProvider(FinflowJwtProperties properties) {
		this.properties = properties;
	}

	private SecretKey signingKey() {
		return Keys.hmacShaKeyFor(properties.getSecret().getBytes());
	}

	/**
	 * Generates a JWT token for a user.
	 * @param userId The ID of the user
	 * @param email The email of the user
	 * @param role The role assigned to the user
	 * @return A signed JWT token string
	 */
	public String generateToken(Long userId, String email, String role) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + properties.getExpirationMs());
		return Jwts.builder()
				.subject(String.valueOf(userId))
				.claim("email", email)
				.claim("roles", List.of(role))
				.issuedAt(now)
				.expiration(exp)
				.signWith(signingKey())
				.compact();
	}

	/**
	 * Parses and retrieves claims from a JWT token.
	 * @param token The JWT token string
	 * @return The claims contained in the token
	 */
	public Claims parseClaims(String token) {
		return Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * Validates a JWT token's signature and expiration.
	 * @param token The JWT token string
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		}
		catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Extracts the user ID from a JWT token.
	 * @param token The JWT token string
	 * @return The user ID as a Long
	 */
	public Long getUserId(String token) {
		return Long.parseLong(parseClaims(token).getSubject());
	}

	/**
	 * Retrieves the primary role from the JWT token.
	 * @param token The JWT token string
	 * @return The primary role name
	 */
	@SuppressWarnings("unchecked")
	public String getPrimaryRole(String token) {
		return ((List<String>) parseClaims(token).get("roles", List.class)).get(0);
	}
}
