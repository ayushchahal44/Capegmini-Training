/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.application.entity.ApplicationStatus;
import com.capg.ayush.application.entity.ApplicationStatusEvent;
import com.capg.ayush.application.entity.LoanApplication;
import com.capg.ayush.application.repository.ApplicationStatusEventRepository;
import com.capg.ayush.application.repository.LoanApplicationRepository;
import com.capg.ayush.application.security.SecurityUtils;
import com.capg.ayush.application.dto.AdminDecisionRequest;
import com.capg.ayush.application.dto.ApplicationStatusResponse;
import com.capg.ayush.application.dto.LoanApplicationDto;
import com.capg.ayush.application.dto.NotifyDocVerifiedRequest;
import com.capg.ayush.application.dto.UpdateLoanApplicationRequest;

/**
 * Unit tests for {@link ApplicationService}.
 * Covers core application logic including creation, validation, and status transitions.
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class ApplicationServiceTest {
    private static final String ROLE_ADMIN = "ADMIN";

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private ApplicationStatusEventRepository eventRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ApplicationService applicationService;

    private LoanApplication loanApplication;

    @BeforeEach
    void setUp() {
        loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setUserId(100L);
        loanApplication.setStatus(ApplicationStatus.DRAFT);
    }

    // ==================== createDraft ====================

    @Test
    void createDraftSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(loanApplication);

            LoanApplicationDto result = applicationService.createDraft();

            assertNotNull(result);
            assertEquals(ApplicationStatus.DRAFT, result.getStatus());
            verify(loanApplicationRepository).save(any(LoanApplication.class));
            verify(eventRepository).save(any(ApplicationStatusEvent.class));
        }
    }

    // ==================== updateDraft ====================

    @Test
    void updateDraftSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
            when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(loanApplication);

            UpdateLoanApplicationRequest req = new UpdateLoanApplicationRequest();
            req.setFullName("John Doe");
            req.setPhone("9876543210");
            req.setAddress("123 Main St");
            req.setDateOfBirth(LocalDate.of(1990, 1, 1));
            req.setEmployer("Acme Corp");
            req.setAnnualIncome(BigDecimal.valueOf(1200000));
            req.setEmploymentType("SALARIED");
            req.setLoanAmount(BigDecimal.valueOf(500000));
            req.setTenureMonths(24);
            req.setLoanPurpose("Home improvement");

            LoanApplicationDto result = applicationService.updateDraft(1L, req);

            assertNotNull(result);
            verify(loanApplicationRepository).save(any(LoanApplication.class));
        }
    }

    @Test
    void updateDraftNotFound() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findById(999L)).thenReturn(Optional.empty());

            UpdateLoanApplicationRequest req = new UpdateLoanApplicationRequest();
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> applicationService.updateDraft(999L, req));
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }
    }

    @Test
    void updateDraftForbidden() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(200L);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            UpdateLoanApplicationRequest req = new UpdateLoanApplicationRequest();
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> applicationService.updateDraft(1L, req));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }
    }

    @Test
    void updateDraftNotDraftConflict() {
        loanApplication.setStatus(ApplicationStatus.SUBMITTED);
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            UpdateLoanApplicationRequest req = new UpdateLoanApplicationRequest();
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> applicationService.updateDraft(1L, req));
            assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        }
    }

    // ==================== submit ====================

    @Test
    void submitSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            LoanApplicationDto result = applicationService.submit(1L);

            assertNotNull(result);
            assertEquals(ApplicationStatus.DOCS_PENDING, loanApplication.getStatus());
            verify(loanApplicationRepository, atLeastOnce()).save(loanApplication);
        }
    }

    @Test
    void submitForbidden() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(200L); 
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> applicationService.submit(1L));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }
    }

    @Test
    void submitConflictAlreadySubmitted() {
        loanApplication.setStatus(ApplicationStatus.SUBMITTED);
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> applicationService.submit(1L));
            assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        }
    }

    // ==================== myApplications ====================

    @Test
    void myApplicationsSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findByUserIdOrderByCreatedAtDesc(100L))
                    .thenReturn(List.of(loanApplication));

            List<LoanApplicationDto> result = applicationService.myApplications();

            assertNotNull(result);
            assertEquals(1, result.size());
        }
    }

    // ==================== getApplicationById ====================

    @Test
    void getApplicationByIdOwnerAccessSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            mockedSecurity.when(() -> SecurityUtils.hasRole(ROLE_ADMIN)).thenReturn(false);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            LoanApplicationDto result = applicationService.getApplicationById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
        }
    }

    @Test
    void getApplicationByIdAdminBypassSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(999L);
            mockedSecurity.when(() -> SecurityUtils.hasRole(ROLE_ADMIN)).thenReturn(true);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            LoanApplicationDto result = applicationService.getApplicationById(1L);

            assertNotNull(result);
        }
    }

    @Test
    void getApplicationByIdForbidden() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(999L);
            mockedSecurity.when(() -> SecurityUtils.hasRole(ROLE_ADMIN)).thenReturn(false);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> applicationService.getApplicationById(1L));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }
    }

    // ==================== statusForApplicant ====================

    @Test
    void statusForApplicantSuccess() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            mockedSecurity.when(() -> SecurityUtils.hasRole(ROLE_ADMIN)).thenReturn(false);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
            when(eventRepository.findByApplication_IdOrderByOccurredAtAsc(1L)).thenReturn(List.of());

            ApplicationStatusResponse result = applicationService.statusForApplicant(1L);

            assertNotNull(result);
            assertEquals(1L, result.getApplicationId());
            assertEquals(ApplicationStatus.DRAFT, result.getCurrentStatus());
        }
    }

    // ==================== adminQueue ====================

    @Test
    void adminQueueSuccess() {
        when(loanApplicationRepository.findAllByOrderByUpdatedAtDesc()).thenReturn(List.of(loanApplication));

        List<LoanApplicationDto> result = applicationService.adminQueue();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // ==================== adminStats ====================

    @Test
    void adminStatsSuccess() {
        for (ApplicationStatus s : ApplicationStatus.values()) {
            when(loanApplicationRepository.countByStatus(s)).thenReturn(5L);
        }

        var result = applicationService.adminStats();

        assertNotNull(result);
        assertTrue(result.getTotalApplications() > 0);
        assertNotNull(result.getApplicationsByStatus());
    }

    // ==================== notifyDocumentsVerified ====================

    @Test
    void notifyDocumentsVerifiedSuccess() {
        loanApplication.setStatus(ApplicationStatus.DOCS_PENDING);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        
        NotifyDocVerifiedRequest req = new NotifyDocVerifiedRequest();
        req.setAllRequiredVerified(true);
        
        applicationService.notifyDocumentsVerified(1L, req);
        
        assertEquals(ApplicationStatus.DOCS_VERIFIED, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
        verify(eventRepository).save(any(ApplicationStatusEvent.class));
    }

    @Test
    void notifyDocumentsVerifiedNotAllVerifiedSkips() {
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

        NotifyDocVerifiedRequest req = new NotifyDocVerifiedRequest();
        req.setAllRequiredVerified(false);

        applicationService.notifyDocumentsVerified(1L, req);

        verify(loanApplicationRepository, never()).save(any());
    }

    // ==================== applyAdminDecision ====================

    @Test
    void applyAdminDecisionApprovedSuccess() {
        loanApplication.setStatus(ApplicationStatus.DOCS_VERIFIED);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        
        AdminDecisionRequest req = new AdminDecisionRequest();
        req.setApproved(true);
        req.setTerms("Standard Interest");
        
        applicationService.applyAdminDecision(1L, req);
        
        assertEquals(ApplicationStatus.APPROVED, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void applyAdminDecisionRejectedSuccess() {
        loanApplication.setStatus(ApplicationStatus.UNDER_REVIEW);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        
        AdminDecisionRequest req = new AdminDecisionRequest();
        req.setApproved(false);
        req.setRejectionReason("Low credit score");
        
        applicationService.applyAdminDecision(1L, req);
        
        assertEquals(ApplicationStatus.REJECTED, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void applyAdminDecisionConflict() {
        loanApplication.setStatus(ApplicationStatus.APPROVED);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

        AdminDecisionRequest req = new AdminDecisionRequest();
        req.setApproved(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> applicationService.applyAdminDecision(1L, req));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    // ==================== moveToUnderReview ====================

    @Test
    void moveToUnderReviewSuccess() {
        loanApplication.setStatus(ApplicationStatus.DOCS_VERIFIED);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

        applicationService.moveToUnderReview(1L);

        assertEquals(ApplicationStatus.UNDER_REVIEW, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void moveToUnderReviewConflict() {
        loanApplication.setStatus(ApplicationStatus.DRAFT);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> applicationService.moveToUnderReview(1L));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }
}
