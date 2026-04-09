/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import com.capg.ayush.admin.repo.DecisionRepository;
import com.capg.ayush.admin.security.SecurityUtils;
import com.capg.ayush.admin.web.dto.AdminDecisionRequest;
import com.capg.ayush.admin.web.dto.LoanApplicationDto;

/**
 * Unit tests for {@link AdminService}.
 * Exercises loan approval, rejection, and statistical reporting.
 */
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DecisionRepository decisionRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        
        
        
        
    }

    @Test
    @SuppressWarnings("unchecked")
    void applicationQueue_Success() {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(1L);
        List<LoanApplicationDto> list = List.of(dto);

        ResponseEntity<List<LoanApplicationDto>> response = ResponseEntity.ok(list);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), 
                any(ParameterizedTypeReference.class))).thenReturn(response);

        List<LoanApplicationDto> result = adminService.applicationQueue("Bearer token");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void decide_Success() {
        AdminDecisionRequest request = new AdminDecisionRequest();
        request.setApproved(true);
        request.setTerms("Standard Terms");

        try (MockedStatic<SecurityUtils> mockedSecurity = mockStatic(SecurityUtils.class)) {
            mockedSecurity.when(SecurityUtils::currentUserId).thenReturn(500L);

            adminService.decide(1L, request, "Bearer token");

            verify(restTemplate).exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Void.class));
            verify(decisionRepository).save(any());
        }
    }
}
