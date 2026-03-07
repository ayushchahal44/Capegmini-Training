package com.springboot.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
 * @Configuration
 * This annotation tells Spring Boot that this class contains
 * security configuration settings.
 
 */
//@Configuration tells Spring that this class -
//contains Spring configuration 
//(object creation and dependency setup).--
@Configuration
public class SecurityConfig {

    /*
     * PasswordEncoder Bean
     * ---------------------
     * Spring Security does NOT allow plain text passwords.
     * So we encode the password using BCrypt algorithm.
     * BCrypt is a strong hashing algorithm used for security.
     */
//@Bean Create this object and store it in the Spring Container 
	                                       //(IOC Container).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * UserDetailsService Bean
     * -----------------------
     * This is used to store user authentication information.
     * In this example we are using "InMemory Authentication".
     * That means users are stored in memory (not database).
     * 
     *
     */
    @Bean
    public UserDetailsService userDetailsService() {
        /*
         * Creating USER Role
         * ------------------
         * username : ayush
         * password : ayush123
         * role     : USER
         */
        UserDetails user = User.builder()
                .username("ayush") // login username
                .password(passwordEncoder().encode("ayush123")) // encrypted password
                .roles("USER") // role assigned to this user
                .build();

        /*
         * Creating ADMIN Role
         * -------------------
         * username : admin
         * password : admin123
         * role     : ADMIN
         */
        // 
        
 //User.builder() Creates a user and automatically encodes the provided password using       
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        /*
         * InMemoryUserDetailsManager
         * --------------------------
         * This class stores users in memory.
         * When login happens, Spring Security checks
         * the username and password from here.
         */
        return new InMemoryUserDetailsManager(user, admin);
    }

    /*
     * SecurityFilterChain
     * -------------------
     * This method defines the security rules for the application.
     * It tells Spring Security:
     * 1. Which URLs need authentication
     * 2. Which roles can access which URLs
     * 3. Login mechanism (Form login / Basic authentication)
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	 /*
         * CSRF Protection
         * ----------------
         * CSRF = Cross Site Request Forgery
         * 
         * It protects web applications from malicious requests.
         * For REST APIs we usually disable CSRF.
         */
        http
            .csrf(csrf -> csrf.disable())
            /*
             * Authorization Rules
             * -------------------
             * Define which roles can access which URLs.
             */ 
            .authorizeHttpRequests(auth -> auth
            		
            		/*
                     * Only ADMIN role can access /admin/**
                     * Example:
                     * http://localhost:9092/admin/home
                     */
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    /*
                     * USER or ADMIN can access /user/**
                     * Example:
                     * http://localhost:9092/user/home
                     */
                    .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                   
                    /*
                     * Any other request must be authenticated
                     */
                    
                    .anyRequest().authenticated()
            )
            /*
             * Form Login
             * ----------
             * Spring Security provides a default login page.
             * When user accesses a protected URL,
             * Spring will redirect to login page.
             */
            .formLogin(form -> form
                    .defaultSuccessUrl("/user/home", true)
            )

            .httpBasic();

        return http.build();
    }
}