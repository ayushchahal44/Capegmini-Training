/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.capg.ayush.admin.domain.Decision;
import com.capg.ayush.admin.repo.DecisionRepository;
import com.capg.ayush.admin.security.SecurityUtils;
import com.capg.ayush.admin.web.dto.AdminDecisionRequest;
import com.capg.ayush.admin.web.dto.AdminStatsDto;
import com.capg.ayush.admin.web.dto.LoanApplicationDto;
import com.capg.ayush.admin.web.dto.ReportResponse;
import com.capg.ayush.admin.web.dto.UpdateUserRequest;
import com.capg.ayush.admin.web.dto.UserResponse;

/**
 * Service class for administrative operations.
 * Coordinates with other microservices via REST and handles local administrative data.
 */
@Service
public class AdminService {

	private final RestTemplate restTemplate;
	private final DecisionRepository decisionRepository;

	private final String authBaseUrl;
	private final String applicationBaseUrl;

	/**
	 * Constructs a new AdminService with necessary dependencies and service URLs.
	 * @param restTemplate The RestTemplate used for inter-service communication
	 * @param decisionRepository The repository for administrative decisions
	 * @param authBaseUrl The base URL for the Auth Service
	 * @param applicationBaseUrl The base URL for the Application Service
	 */
	public AdminService(RestTemplate restTemplate, DecisionRepository decisionRepository,
			@Value("${finflow.services.auth.url:http://finflow-auth-service}") String authBaseUrl,
			@Value("${finflow.services.application.url:http://finflow-application-service}") String applicationBaseUrl) {
		this.restTemplate = restTemplate;
		this.decisionRepository = decisionRepository;
		this.authBaseUrl = (authBaseUrl != null ? authBaseUrl : "http://finflow-auth-service").replaceAll("/$", "");
		this.applicationBaseUrl = (applicationBaseUrl != null ? applicationBaseUrl : "http://finflow-application-service").replaceAll("/$", "");
	}

	/**
	 * Retrieves the queue of loan applications from the application service.
	 * @param authorizationHeader The authorization header for the request
	 * @return A list of loan application DTOs
	 */
	public List<LoanApplicationDto> applicationQueue(String authorizationHeader) {
		HttpHeaders headers = authHeaders(authorizationHeader);
		String url = applicationBaseUrl + "/api/applications/admin/queue";
		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
				new ParameterizedTypeReference<List<LoanApplicationDto>>() {
				}).getBody();
	}

	/**
	 * Records an administrative decision on a loan application and notifies the application service.
	 * @param applicationId The ID of the loan application
	 * @param request The decision details
	 * @param authorizationHeader The authorization header for the request
	 */
	@Transactional
	public void decide(Long applicationId, AdminDecisionRequest request, String authorizationHeader) {
		HttpHeaders headers = authHeaders(authorizationHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = applicationBaseUrl + "/api/applications/admin/" + applicationId + "/decision";
		restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Void.class);

		Decision d = new Decision();
		d.setApplicationId(applicationId);
		d.setApproved(Boolean.TRUE.equals(request.getApproved()));
		d.setTerms(request.getTerms());
		d.setRejectionReason(request.getRejectionReason());
		d.setDecidedByUserId(SecurityUtils.currentUserId());
		d.setDecidedAt(Instant.now());
		decisionRepository.save(d);
	}

	/**
	 * Generates a report of application statistics across services.
	 * @param authorizationHeader The authorization header for the request
	 * @return A ReportResponse containing aggregated stats
	 */
	public ReportResponse reports(String authorizationHeader) {
		HttpHeaders headers = authHeaders(authorizationHeader);
		String url = applicationBaseUrl + "/api/applications/admin/stats";
		AdminStatsDto stats = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), AdminStatsDto.class)
				.getBody();
		ReportResponse r = new ReportResponse();
		if (stats != null) {
			r.setApplicationsByStatus(stats.getApplicationsByStatus());
			r.setTotalApplications(stats.getTotalApplications());
		}
		r.setTotalRecordedDecisions(decisionRepository.count());
		r.setApprovedDecisions(decisionRepository.countByApproved(true));
		r.setRejectedDecisions(decisionRepository.countByApproved(false));
		return r;
	}

	/**
	 * Lists all users from the auth service.
	 * @param authorizationHeader The authorization header for the request
	 * @return A list of user responses
	 */
	public List<UserResponse> listUsers(String authorizationHeader) {
		HttpHeaders headers = authHeaders(authorizationHeader);
		String url = authBaseUrl + "/api/auth/users";
		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
				new ParameterizedTypeReference<List<UserResponse>>() {
				}).getBody();
	}

	/**
	 * Updates a user's properties via the auth service.
	 * @param id The ID of the user to update
	 * @param request The update details
	 * @param authorizationHeader The authorization header for the request
	 * @return The updated user response
	 */
	public UserResponse updateUser(Long id, UpdateUserRequest request, String authorizationHeader) {
		HttpHeaders headers = authHeaders(authorizationHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = authBaseUrl + "/api/auth/users/" + id;
		return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), UserResponse.class)
				.getBody();
	}

	private HttpHeaders authHeaders(String authorizationHeader) {
		HttpHeaders headers = new HttpHeaders();
		if (authorizationHeader != null && !authorizationHeader.isBlank()) {
			headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
		}
		return headers;
	}
}
