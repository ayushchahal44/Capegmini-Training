/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.capg.ayush.admin.repository.DecisionRepository;
import com.capg.ayush.admin.security.SecurityUtils;
import com.capg.ayush.admin.dto.AdminDecisionRequest;
import com.capg.ayush.admin.dto.AdminStatsDto;
import com.capg.ayush.admin.dto.LoanApplicationDto;
import com.capg.ayush.admin.dto.ReportResponse;
import com.capg.ayush.admin.dto.UpdateUserRequest;
import com.capg.ayush.admin.dto.UserResponse;
import com.capg.ayush.admin.dto.VerifyDocumentRequest;

/**
 * Unit tests for {@link AdminService}.
 * Exercises loan approval, rejection, reporting, user management, and document verification.
 */
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    private static final String BEARER_TOKEN = "Bearer token";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DecisionRepository decisionRepository;

    @InjectMocks
    private AdminService adminService;

    // ==================== applicationQueue ====================

    @Test
    @SuppressWarnings({"unchecked", "null"})
    void applicationQueueSuccess() {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(1L);
        List<LoanApplicationDto> list = List.of(dto);

        ResponseEntity<List<LoanApplicationDto>> response = ResponseEntity.ok(list);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), 
                any(ParameterizedTypeReference.class))).thenReturn(response);

        List<LoanApplicationDto> result = adminService.applicationQueue(BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    // ==================== decide ====================

    @Test
    @SuppressWarnings("null")
    void decideApprovedSuccess() {
        AdminDecisionRequest request = new AdminDecisionRequest();
        request.setApproved(true);
        request.setTerms("Standard Terms");

        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(500L);

            adminService.decide(1L, request, BEARER_TOKEN);

            verify(restTemplate).exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Void.class));
            verify(decisionRepository).save(any());
        }
    }

    @Test
    @SuppressWarnings("null")
    void decideRejectedSuccess() {
        AdminDecisionRequest request = new AdminDecisionRequest();
        request.setApproved(false);
        request.setRejectionReason("Insufficient income");

        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(500L);

            adminService.decide(1L, request, BEARER_TOKEN);

            verify(restTemplate).exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Void.class));
            verify(decisionRepository).save(any());
        }
    }

    // ==================== reports ====================

    @Test
    @SuppressWarnings("null")
    void reportsSuccess() {
        AdminStatsDto stats = new AdminStatsDto();
        stats.setTotalApplications(10L);
        stats.setApplicationsByStatus(java.util.Map.of("DRAFT", 3L, "APPROVED", 7L));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(AdminStatsDto.class)))
                .thenReturn(ResponseEntity.ok(stats));
        when(decisionRepository.count()).thenReturn(5L);
        when(decisionRepository.countByApproved(true)).thenReturn(3L);
        when(decisionRepository.countByApproved(false)).thenReturn(2L);

        ReportResponse result = adminService.reports(BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(10L, result.getTotalApplications());
        assertEquals(5L, result.getTotalRecordedDecisions());
        assertEquals(3L, result.getApprovedDecisions());
        assertEquals(2L, result.getRejectedDecisions());
    }

    @Test
    @SuppressWarnings("null")
    void reportsNullStats() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(AdminStatsDto.class)))
                .thenReturn(ResponseEntity.ok(null));
        when(decisionRepository.count()).thenReturn(0L);
        when(decisionRepository.countByApproved(true)).thenReturn(0L);
        when(decisionRepository.countByApproved(false)).thenReturn(0L);

        ReportResponse result = adminService.reports(BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(0L, result.getTotalRecordedDecisions());
    }

    // ==================== listUsers ====================

    @Test
    @SuppressWarnings({"unchecked", "null"})
    void listUsersSuccess() {
        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(List.of(user)));

        List<UserResponse> result = adminService.listUsers(BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // ==================== updateUser ====================

    @Test
    @SuppressWarnings("null")
    void updateUserSuccess() {
        UpdateUserRequest request = new UpdateUserRequest();

        UserResponse response = new UserResponse();
        response.setId(1L);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(UserResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        UserResponse result = adminService.updateUser(1L, request, BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    // ==================== verifyDocument ====================

    @Test
    @SuppressWarnings("null")
    void verifyDocumentSuccess() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(true);

        adminService.verifyDocument(1L, request, BEARER_TOKEN);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Void.class));
    }
}
