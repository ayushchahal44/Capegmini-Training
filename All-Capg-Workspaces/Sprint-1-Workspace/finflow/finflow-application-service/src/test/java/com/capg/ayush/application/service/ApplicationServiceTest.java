/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import com.capg.ayush.application.domain.ApplicationStatus;
import com.capg.ayush.application.domain.ApplicationStatusEvent;
import com.capg.ayush.application.domain.LoanApplication;
import com.capg.ayush.application.repo.ApplicationStatusEventRepository;
import com.capg.ayush.application.repo.LoanApplicationRepository;
import com.capg.ayush.application.security.SecurityUtils;
import com.capg.ayush.application.web.dto.LoanApplicationDto;

/**
 * Unit tests for {@link ApplicationService}.
 * Covers core application logic including creation, validation, and status transitions.
 */
@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

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

    @Test
    void createDraft_Success() {
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

    @Test
    void submit_Success() {
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
    void submit_Forbidden() {
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(200L); 
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> applicationService.submit(1L));
            assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        }
    }

    @Test
    void submit_Conflict_AlreadySubmitted() {
        loanApplication.setStatus(ApplicationStatus.SUBMITTED);
        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(100L);
            when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> applicationService.submit(1L));
            assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        }
    }

    @Test
    void notifyDocumentsVerified_Success() {
        loanApplication.setStatus(ApplicationStatus.DOCS_PENDING);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        
        com.capg.ayush.application.web.dto.NotifyDocVerifiedRequest req = new com.capg.ayush.application.web.dto.NotifyDocVerifiedRequest();
        req.setAllRequiredVerified(true);
        
        applicationService.notifyDocumentsVerified(1L, req);
        
        assertEquals(ApplicationStatus.DOCS_VERIFIED, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
        verify(eventRepository).save(any(ApplicationStatusEvent.class));
    }

    @Test
    void applyAdminDecision_Approved_Success() {
        loanApplication.setStatus(ApplicationStatus.DOCS_VERIFIED);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        
        com.capg.ayush.application.web.dto.AdminDecisionRequest req = new com.capg.ayush.application.web.dto.AdminDecisionRequest();
        req.setApproved(true);
        req.setTerms("Standard Interest");
        
        applicationService.applyAdminDecision(1L, req);
        
        assertEquals(ApplicationStatus.APPROVED, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
    }

    @Test
    void applyAdminDecision_Rejected_Success() {
        loanApplication.setStatus(ApplicationStatus.UNDER_REVIEW);
        when(loanApplicationRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        
        com.capg.ayush.application.web.dto.AdminDecisionRequest req = new com.capg.ayush.application.web.dto.AdminDecisionRequest();
        req.setApproved(false);
        req.setRejectionReason("Low credit score");
        
        applicationService.applyAdminDecision(1L, req);
        
        assertEquals(ApplicationStatus.REJECTED, loanApplication.getStatus());
        verify(loanApplicationRepository).save(loanApplication);
    }
}
