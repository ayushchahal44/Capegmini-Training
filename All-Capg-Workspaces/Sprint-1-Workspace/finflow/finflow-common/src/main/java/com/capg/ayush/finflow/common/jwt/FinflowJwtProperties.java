/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for JWT (JSON Web Token) handling.
 */
@ConfigurationProperties(prefix = "finflow.jwt")
public class FinflowJwtProperties {

	private String secret = "finflow-dev-secret-key-change-me-please-use-256-bits-minimum-for-hs256-algorithm";

	private long expirationMs = 86400000L;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public long getExpirationMs() {
		return expirationMs;
	}

	public void setExpirationMs(long expirationMs) {
		this.expirationMs = expirationMs;
	}
}
