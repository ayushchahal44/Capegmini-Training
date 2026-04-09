/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for REST clients (RestTemplate) used for inter-service communication.
 */
@Configuration
public class RestClientConfig {

	/**
	 * Provides a load-balanced RestTemplate bean for production/dev profiles.
	 * @return A load-balanced RestTemplate instance
	 */
	@Bean
	@LoadBalanced
	@Profile("!test")
	public RestTemplate loadBalancedRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * Provides a plain RestTemplate bean for testing environments.
	 * @return A plain RestTemplate instance
	 */
	@Bean
	@Profile("test")
	public RestTemplate plainRestTemplate() {
		return new RestTemplate();
	}
}
