/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.web.dto;

import java.util.List;

import com.capg.ayush.application.domain.ApplicationStatus;

/**
 * Data Transfer Object representing the detailed status and timeline of a loan application.
 */
public class ApplicationStatusResponse {

	private Long applicationId;
	private ApplicationStatus currentStatus;
	private List<TimelineEntry> timeline;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public ApplicationStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(ApplicationStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<TimelineEntry> getTimeline() {
		return timeline;
	}

	public void setTimeline(List<TimelineEntry> timeline) {
		this.timeline = timeline;
	}

	/**
	 * Represents a single event in the application's lifecycle timeline.
	 */
	public static class TimelineEntry {
		private ApplicationStatus status;
		private String note;
		private String at;

		public TimelineEntry() {
		}

		public TimelineEntry(ApplicationStatus status, String note, String at) {
			this.status = status;
			this.note = note;
			this.at = at;
		}

		public ApplicationStatus getStatus() {
			return status;
		}

		public void setStatus(ApplicationStatus status) {
			this.status = status;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getAt() {
			return at;
		}

		public void setAt(String at) {
			this.at = at;
		}
	}
}
