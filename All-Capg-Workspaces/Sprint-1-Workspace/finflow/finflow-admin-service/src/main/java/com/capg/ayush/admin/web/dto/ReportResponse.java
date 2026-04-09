/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.web.dto;

import java.util.Map;

/**
 * Data Transfer Object representing an aggregated administrative report.
 */
public class ReportResponse {

	private Map<String, Long> applicationsByStatus;
	private long totalApplications;
	private long totalRecordedDecisions;
	private long approvedDecisions;
	private long rejectedDecisions;

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

	public long getTotalRecordedDecisions() {
		return totalRecordedDecisions;
	}

	public void setTotalRecordedDecisions(long totalRecordedDecisions) {
		this.totalRecordedDecisions = totalRecordedDecisions;
	}

	public long getApprovedDecisions() {
		return approvedDecisions;
	}

	public void setApprovedDecisions(long approvedDecisions) {
		this.approvedDecisions = approvedDecisions;
	}

	public long getRejectedDecisions() {
		return rejectedDecisions;
	}

	public void setRejectedDecisions(long rejectedDecisions) {
		this.rejectedDecisions = rejectedDecisions;
	}
}
