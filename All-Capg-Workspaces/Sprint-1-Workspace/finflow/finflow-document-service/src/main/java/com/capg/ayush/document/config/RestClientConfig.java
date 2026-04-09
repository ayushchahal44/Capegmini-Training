/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for REST clients (RestTemplate).
 * Provides load-balanced and plain RestTemplate beans.
 */
@Configuration
public class RestClientConfig {

	/**
	 * Provides a load-balanced RestTemplate for service-to-service communication.
	 * @return The load-balanced RestTemplate instance
	 */
	@Bean
	@LoadBalanced
	@Profile("!test")
	public RestTemplate loadBalancedRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@Profile("test")
	public RestTemplate plainRestTemplate() {
		return new RestTemplate();
	}
}
