/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.web;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capg.ayush.auth.repo.UserRepository;
import com.capg.ayush.auth.web.dto.SignupRequest;

/**
 * Internal REST Controller for connectivity and database testing.
 * Not intended for production use.
 */
@RestController
public class TestController {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Simple health check endpoint.
     * @return A status message
     */
    @GetMapping("/test")
    public String test() {
        return "Auth service is working!";
    }

    /**
     * Tests the database connection and retrieves the user count.
     * @return A diagnostic message with connection status and user count
     */
    @GetMapping("/test-db")
    public String testDatabase() {
        try {
            
            dataSource.getConnection().close();
            
            
            long userCount = userRepository.count();
            
            return "Database connection test - OK, User count: " + userCount;
        } catch (Exception e) {
            return "Database connection test - ERROR: " + e.getMessage();
        }
    }

    @PostMapping("/test-signup")
    public String testSignup(@RequestBody SignupRequest request) {
        try {
            return "Received signup request for: " + request.getEmail() + 
                   ", First Name: " + request.getFirstName() + 
                   ", Last Name: " + request.getLastName();
        } catch (Exception e) {
            return "Error processing signup request: " + e.getMessage();
        }
    }
}
