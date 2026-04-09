/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Utility component for JWT operations using value injection for properties.
 */
@Component
public class JwtUtil {

    private final Key key;
    private final long EXPIRATION;

    /**
     * Constructs a new JwtUtil with specified secret and expiration.
     * @param secret The JWT secret key
     * @param expiration The expiration time in milliseconds
     */
    public JwtUtil(
        @Value("${finflow.jwt.secret}") String secret,
        @Value("${finflow.jwt.expiration-ms}") long expiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.EXPIRATION = expiration;
    }

    /**
     * Generates a JWT token for a user.
     * @param username The username of the user
     * @param role The role assigned to the user
     * @return A signed JWT token string
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * Validates a JWT token and returns its claims.
     * @param token The JWT token string
     * @return The claims contained in the token
     * @throws RuntimeException if the token is expired or invalid
     */
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        } catch (io.jsonwebtoken.JwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }

    /**
     * Extracts the username from a JWT token.
     * @param token The JWT token string
     * @return The username as a String
     */
    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    /**
     * Extracts the role from a JWT token.
     * @param token The JWT token string
     * @return The role as a String
     */
    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }
}