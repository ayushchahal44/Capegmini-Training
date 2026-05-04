/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class AuthServiceClient {

    private final RestTemplate restTemplate;
    private final String authBaseUrl;

    public AuthServiceClient(RestTemplate restTemplate,
                             @Value("${finflow.services.auth.url:http://finflow-auth-service}") String authBaseUrl) {
        this.restTemplate = restTemplate;
        this.authBaseUrl = authBaseUrl.replaceAll("/$", "");
    }

    @SuppressWarnings("null")
    public String getUserEmail(Long userId) {
        if (userId == null || userId == 0) return null;
        String url = authBaseUrl + "/api/auth/internal/users/" + userId + "/email";
        try {
            ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, String>>() {}
            );
            if (response.getBody() != null) {
                return response.getBody().get("email");
            }
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
