/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.client;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * REST Client for communicating with the FinFlow Application Service.
 * Used to notify the application service about document verification results.
 */
@Component
public class ApplicationServiceClient {

	private final RestTemplate restTemplate;

	private final String applicationBaseUrl;

	/**
	 * Constructs a new ApplicationServiceClient.
	 * @param restTemplate Template for making REST calls
	 * @param applicationBaseUrl Base URL of the application service
	 */
	public ApplicationServiceClient(RestTemplate restTemplate,
			@Value("${finflow.services.application.url:http://finflow-application-service}") String applicationBaseUrl) {
		this.restTemplate = restTemplate;
		this.applicationBaseUrl = applicationBaseUrl.replaceAll("/$", "");
	}

	/**
	 * Notifies the application service that all required documents for an application are verified.
	 * @param applicationId The ID of the loan application
	 * @param allRequiredVerified Whether all required docs are verified
	 * @param authorizationHeader The JWT authorization header to pass along
	 */
	public void notifyDocumentsVerified(Long applicationId, boolean allRequiredVerified, String authorizationHeader) {
		String url = applicationBaseUrl + "/api/applications/admin/" + applicationId + "/notify-doc-verified";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (authorizationHeader != null && !authorizationHeader.isBlank()) {
			headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
		}
		Map<String, Object> body = Map.of("allRequiredVerified", allRequiredVerified);
		restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), Void.class);
	}
}
