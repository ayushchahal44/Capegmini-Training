/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.application.config.RabbitMQConfig;
import com.capg.ayush.application.domain.ApplicationStatus;
import com.capg.ayush.application.domain.ApplicationStatusEvent;
import com.capg.ayush.application.domain.LoanApplication;
import com.capg.ayush.application.messaging.ApplicationStatusChangedEvent;
import com.capg.ayush.application.repo.ApplicationStatusEventRepository;
import com.capg.ayush.application.repo.LoanApplicationRepository;
import com.capg.ayush.application.security.SecurityUtils;
import com.capg.ayush.application.web.dto.AdminDecisionRequest;
import com.capg.ayush.application.web.dto.AdminStatsDto;
import com.capg.ayush.application.web.dto.ApplicationStatusResponse;
import com.capg.ayush.application.web.dto.LoanApplicationDto;
import com.capg.ayush.application.web.dto.NotifyDocVerifiedRequest;
import com.capg.ayush.application.web.dto.UpdateLoanApplicationRequest;

/**
 * Service class for managing loan applications.
 * Handles the creation, submission, and status management of loan applications.
 */
@Service
public class ApplicationService {

	private final LoanApplicationRepository loanApplicationRepository;
	private final ApplicationStatusEventRepository eventRepository;
	private final RabbitTemplate rabbitTemplate;

	/**
	 * Constructs a new ApplicationService.
	 * @param loanApplicationRepository Repository for loan applications
	 * @param eventRepository Repository for status change events
	 * @param rabbitTemplate Template for RabbitMQ messaging
	 */
	public ApplicationService(LoanApplicationRepository loanApplicationRepository,
			ApplicationStatusEventRepository eventRepository,
			RabbitTemplate rabbitTemplate) {
		this.loanApplicationRepository = loanApplicationRepository;
		this.eventRepository = eventRepository;
		this.rabbitTemplate = rabbitTemplate;
	}


	/**
	 * Creates a new loan application in DRAFT state for the current user.
	 * @return The created loan application as a DTO
	 */
	@Transactional
	public LoanApplicationDto createDraft() {
		Long userId = SecurityUtils.currentUserId();
		LoanApplication app = new LoanApplication();
		app.setUserId(userId);
		app.setStatus(ApplicationStatus.DRAFT);
		app = loanApplicationRepository.save(app);
		appendEvent(app, ApplicationStatus.DRAFT, "Draft created");
		return toDto(app);
	}

	/**
	 * Updates the details of a DRAFT loan application.
	 * @param id The ID of the application to update
	 * @param req The updated application details
	 * @return The updated loan application as a DTO
	 */
	@Transactional
	public LoanApplicationDto updateDraft(Long id, UpdateLoanApplicationRequest req) {
		Long userId = SecurityUtils.currentUserId();
		LoanApplication app = loanApplicationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!app.getUserId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		if (app.getStatus() != ApplicationStatus.DRAFT) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Only draft applications can be edited");
		}
		if (req.getFullName() != null) {
			app.setFullName(req.getFullName());
		}
		if (req.getPhone() != null) {
			app.setPhone(req.getPhone());
		}
		if (req.getAddress() != null) {
			app.setAddress(req.getAddress());
		}
		if (req.getDateOfBirth() != null) {
			app.setDateOfBirth(req.getDateOfBirth());
		}
		if (req.getEmployer() != null) {
			app.setEmployer(req.getEmployer());
		}
		if (req.getAnnualIncome() != null) {
			app.setAnnualIncome(req.getAnnualIncome());
		}
		if (req.getEmploymentType() != null) {
			app.setEmploymentType(req.getEmploymentType());
		}
		if (req.getLoanAmount() != null) {
			app.setLoanAmount(req.getLoanAmount());
		}
		if (req.getTenureMonths() != null) {
			app.setTenureMonths(req.getTenureMonths());
		}
		if (req.getLoanPurpose() != null) {
			app.setLoanPurpose(req.getLoanPurpose());
		}
		return toDto(loanApplicationRepository.save(app));
	}

	/**
	 * Submits a DRAFT loan application for processing.
	 * Transitions the status from DRAFT to SUBMITTED and then to DOCS_PENDING.
	 * @param id The ID of the application to submit
	 * @return The submitted loan application as a DTO
	 */
	@Transactional
	public LoanApplicationDto submit(Long id) {
		Long userId = SecurityUtils.currentUserId();
		LoanApplication app = loanApplicationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!app.getUserId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		if (app.getStatus() != ApplicationStatus.DRAFT) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Application already submitted");
		}
		app.setStatus(ApplicationStatus.SUBMITTED);
		loanApplicationRepository.save(app);
		appendEvent(app, ApplicationStatus.SUBMITTED, "Application submitted");
		app.setStatus(ApplicationStatus.DOCS_PENDING);
		loanApplicationRepository.save(app);
		appendEvent(app, ApplicationStatus.DOCS_PENDING, "Awaiting KYC documents");
		return toDto(app);
	}

	/**
	 * Retrieves all loan applications submitted by the current user.
	 * @return A list of loan application DTOs
	 */
	@Transactional(readOnly = true)
	public List<LoanApplicationDto> myApplications() {
		Long userId = SecurityUtils.currentUserId();
		return loanApplicationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ApplicationStatusResponse statusForApplicant(Long id) {
		Long userId = SecurityUtils.currentUserId();
		LoanApplication app = loanApplicationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!app.getUserId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		return buildStatusResponse(app);
	}

	/**
	 * Retrieves all loan applications for administrative processing.
	 * @return A list of all loan application DTOs
	 */
	@Transactional(readOnly = true)
	public List<LoanApplicationDto> adminQueue() {
		return loanApplicationRepository.findAllByOrderByUpdatedAtDesc().stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * Generates statistics on loan applications for administrative use.
	 * @return An AdminStatsDto containing application counts by status
	 */
	@Transactional(readOnly = true)
	public AdminStatsDto adminStats() {
		AdminStatsDto dto = new AdminStatsDto();
		Map<String, Long> map = new HashMap<>();
		long total = 0L;
		for (ApplicationStatus s : ApplicationStatus.values()) {
			long c = loanApplicationRepository.countByStatus(s);
			map.put(s.name(), c);
			total += c;
		}
		dto.setApplicationsByStatus(map);
		dto.setTotalApplications(total);
		return dto;
	}

	@Transactional
	public void notifyDocumentsVerified(Long applicationId, NotifyDocVerifiedRequest req) {
		LoanApplication app = loanApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!req.isAllRequiredVerified()) {
			return;
		}
		if (app.getStatus() == ApplicationStatus.DOCS_PENDING || app.getStatus() == ApplicationStatus.SUBMITTED) {
			app.setStatus(ApplicationStatus.DOCS_VERIFIED);
			loanApplicationRepository.save(app);
			appendEvent(app, ApplicationStatus.DOCS_VERIFIED, "All required documents verified");
		}
	}

	@Transactional
	public void applyAdminDecision(Long applicationId, AdminDecisionRequest req) {
		LoanApplication app = loanApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		ApplicationStatus st = app.getStatus();
		if (st != ApplicationStatus.DOCS_VERIFIED && st != ApplicationStatus.UNDER_REVIEW) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Decision allowed only when DOCS_VERIFIED or UNDER_REVIEW");
		}
		if (Boolean.TRUE.equals(req.getApproved())) {
			app.setStatus(ApplicationStatus.APPROVED);
			loanApplicationRepository.save(app);
			appendEvent(app, ApplicationStatus.APPROVED,
					req.getTerms() != null ? "Approved. Terms: " + req.getTerms() : "Approved");
		}
		else {
			app.setStatus(ApplicationStatus.REJECTED);
			loanApplicationRepository.save(app);
			appendEvent(app, ApplicationStatus.REJECTED,
					req.getRejectionReason() != null ? req.getRejectionReason() : "Rejected");
		}
	}

	@Transactional
	public void moveToUnderReview(Long applicationId) {
		LoanApplication app = loanApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (app.getStatus() != ApplicationStatus.DOCS_VERIFIED) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Application must be in DOCS_VERIFIED state");
		}
		app.setStatus(ApplicationStatus.UNDER_REVIEW);
		loanApplicationRepository.save(app);
		appendEvent(app, ApplicationStatus.UNDER_REVIEW, "Under manual review");
	}

	private ApplicationStatusResponse buildStatusResponse(LoanApplication app) {
		ApplicationStatusResponse res = new ApplicationStatusResponse();
		res.setApplicationId(app.getId());
		res.setCurrentStatus(app.getStatus());
		List<ApplicationStatusResponse.TimelineEntry> timeline = eventRepository
				.findByApplication_IdOrderByOccurredAtAsc(app.getId()).stream()
				.map(e -> new ApplicationStatusResponse.TimelineEntry(e.getStatus(), e.getNote(),
						e.getOccurredAt().toString()))
				.collect(Collectors.toList());
		res.setTimeline(timeline);
		return res;
	}

	private void appendEvent(LoanApplication app, ApplicationStatus status, String note) {
		ApplicationStatusEvent ev = new ApplicationStatusEvent();
		ev.setApplication(app);
		ev.setStatus(status);
		ev.setNote(note);
		eventRepository.save(ev);
		publishStatusEvent(app, status, note);
	}

	private void publishStatusEvent(LoanApplication app, ApplicationStatus newStatus, String note) {
		try {
			ApplicationStatusChangedEvent event = new ApplicationStatusChangedEvent(
					app.getId(), app.getUserId(), app.getStatus(), newStatus, note);
			String routingKey = "application.status." + newStatus.name().toLowerCase();
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, event);
		} catch (Exception e) {
			
			System.err.println("[RabbitMQ] Failed to publish status event for application " + app.getId() + ": " + e.getMessage());
		}
	}

	private LoanApplicationDto toDto(LoanApplication a) {
		LoanApplicationDto d = new LoanApplicationDto();
		d.setId(a.getId());
		d.setUserId(a.getUserId());
		d.setStatus(a.getStatus());
		d.setFullName(a.getFullName());
		d.setPhone(a.getPhone());
		d.setAddress(a.getAddress());
		d.setDateOfBirth(a.getDateOfBirth());
		d.setEmployer(a.getEmployer());
		d.setAnnualIncome(a.getAnnualIncome());
		d.setEmploymentType(a.getEmploymentType());
		d.setLoanAmount(a.getLoanAmount());
		d.setTenureMonths(a.getTenureMonths());
		d.setLoanPurpose(a.getLoanPurpose());
		d.setCreatedAt(a.getCreatedAt());
		d.setUpdatedAt(a.getUpdatedAt());
		return d;
	}
}
