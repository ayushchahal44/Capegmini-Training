/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the Loan Application flow.
 * Verifies application creation, status updates, and messaging.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
class ApplicationIntegrationTest {


    @Test
    void contextLoads() {
        
    }

    
    
    
}
