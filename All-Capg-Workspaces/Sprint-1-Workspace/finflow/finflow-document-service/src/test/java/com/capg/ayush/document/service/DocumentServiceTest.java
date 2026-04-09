/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.document.client.ApplicationServiceClient;
import com.capg.ayush.document.domain.DocStatus;
import com.capg.ayush.document.domain.DocType;
import com.capg.ayush.document.domain.DocumentEntity;
import com.capg.ayush.document.repo.DocumentRepository;
import com.capg.ayush.document.security.SecurityUtils;
import com.capg.ayush.document.web.dto.DocumentDto;
import com.capg.ayush.document.web.dto.VerifyDocumentRequest;

/**
 * Unit tests for {@link DocumentService}.
 * Validates document upload and verification logic.
 */
@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ApplicationServiceClient applicationServiceClient;

    @InjectMocks
    private DocumentService documentService;

    private DocumentEntity documentEntity;

    @BeforeEach
    void setUp() {
        documentEntity = new DocumentEntity();
        documentEntity.setId(1L);
        documentEntity.setApplicationId(10L);
        documentEntity.setStatus(DocStatus.PENDING);
    }

    @Test
    void upload_EmptyFile_ThrowsException() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            documentService.upload(10L, DocType.ID_PROOF, emptyFile));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void verify_Approved_Success() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(true);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));
        
        when(documentRepository.existsByApplicationIdAndDocTypeAndStatus(anyLong(), any(DocType.class), eq(DocStatus.VERIFIED)))
                .thenReturn(true);

        DocumentDto result = documentService.verify(1L, request, "Bearer token");

        assertNotNull(result);
        assertEquals(DocStatus.VERIFIED, result.getStatus());
        verify(documentRepository).save(documentEntity);
        
        verify(applicationServiceClient).notifyDocumentsVerified(eq(10L), eq(true), anyString());
    }

    @Test
    void verify_Rejected_Success() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(false);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));

        DocumentDto result = documentService.verify(1L, request, "Bearer token");

        assertNotNull(result);
        assertEquals(DocStatus.REJECTED, result.getStatus());
        verify(documentRepository).save(documentEntity);
        
        verify(applicationServiceClient, never()).notifyDocumentsVerified(anyLong(), anyBoolean(), anyString());
    }
}
