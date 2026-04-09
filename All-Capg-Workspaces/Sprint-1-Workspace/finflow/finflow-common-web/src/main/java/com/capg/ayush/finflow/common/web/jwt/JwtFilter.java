/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.web.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capg.ayush.finflow.common.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A standard servlet filter for validating JWT tokens and setting authentication.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

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
     * @param req The current HTTP request
     * @param res The current HTTP response
     * @param chain The chain of filters to execute
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse res,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader("Authorization");

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                if (username != null && org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() == null) {
                    org.springframework.security.core.authority.SimpleGrantedAuthority authority = 
                        new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role);
                    org.springframework.security.authentication.UsernamePasswordAuthenticationToken authToken = 
                        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(username, null, java.util.Collections.singletonList(authority));
                    
                    org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                
            }
        }

        chain.doFilter(req, res);
    }
}
