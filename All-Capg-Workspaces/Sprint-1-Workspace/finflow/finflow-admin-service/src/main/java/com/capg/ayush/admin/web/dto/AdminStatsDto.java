/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.web.dto;

import java.util.Map;

/**
 * Data Transfer Object for loan application statistics.
 */
public class AdminStatsDto {

	private Map<String, Long> applicationsByStatus;
	private long totalApplications;

	public Map<String, Long> getApplicationsByStatus() {
		return applicationsByStatus;
	}

	public void setApplicationsByStatus(Map<String, Long> applicationsByStatus) {
		this.applicationsByStatus = applicationsByStatus;
	}

	public long getTotalApplications() {
		return totalApplications;
	}

	public void setTotalApplications(long totalApplications) {
		this.totalApplications = totalApplications;
	}
}
