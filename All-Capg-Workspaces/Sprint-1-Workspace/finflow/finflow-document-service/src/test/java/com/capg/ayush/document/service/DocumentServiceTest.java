/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.document.client.ApplicationServiceClient;
import com.capg.ayush.document.entity.DocStatus;
import com.capg.ayush.document.entity.DocType;
import com.capg.ayush.document.entity.DocumentEntity;
import com.capg.ayush.document.repository.DocumentRepository;
import com.capg.ayush.document.dto.DocumentDto;
import com.capg.ayush.document.dto.VerifyDocumentRequest;

/**
 * Unit tests for {@link DocumentService}.
 * Validates document upload, verification, listing, and download logic.
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class DocumentServiceTest {
    private static final String BEARER_TOKEN = "Bearer token";

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ApplicationServiceClient applicationServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DocumentService documentService;

    private DocumentEntity documentEntity;

    @BeforeEach
    void setUp() {
        documentEntity = new DocumentEntity();
        documentEntity.setId(1L);
        documentEntity.setApplicationId(10L);
        documentEntity.setUserId(100L);
        documentEntity.setDocType(DocType.ID_PROOF);
        documentEntity.setOriginalName("id_proof.pdf");
        documentEntity.setContentType("application/pdf");
        documentEntity.setStatus(DocStatus.PENDING);
    }

    // ==================== upload ====================

    @Test
    void uploadEmptyFileThrowsException() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            documentService.upload(10L, DocType.ID_PROOF, emptyFile));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void uploadNullFileThrowsException() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            documentService.upload(10L, DocType.ID_PROOF, null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    // ==================== verify ====================

    @Test
    void verifyApprovedAllVerifiedNotifiesAppService() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(true);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));
        
        when(documentRepository.existsByApplicationIdAndDocTypeAndStatus(anyLong(), any(DocType.class), eq(DocStatus.VERIFIED)))
                .thenReturn(true);

        DocumentDto result = documentService.verify(1L, request, BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(DocStatus.VERIFIED, result.getStatus());
        verify(documentRepository).save(documentEntity);
        
        verify(applicationServiceClient).notifyDocumentsVerified(eq(10L), eq(true), anyString());
    }

    @Test
    void verifyApprovedNotAllVerifiedDoesNotNotify() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(true);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));

        // Not all doc types verified
        when(documentRepository.existsByApplicationIdAndDocTypeAndStatus(anyLong(), any(DocType.class), eq(DocStatus.VERIFIED)))
                .thenReturn(false);

        DocumentDto result = documentService.verify(1L, request, BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(DocStatus.VERIFIED, result.getStatus());
        verify(applicationServiceClient, never()).notifyDocumentsVerified(anyLong(), anyBoolean(), anyString());
    }

    @Test
    void verifyRejectedSuccess() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(false);
        request.setNotes("Blurry image");

        when(documentRepository.findById(1L)).thenReturn(Optional.of(documentEntity));

        DocumentDto result = documentService.verify(1L, request, BEARER_TOKEN);

        assertNotNull(result);
        assertEquals(DocStatus.REJECTED, result.getStatus());
        verify(documentRepository).save(documentEntity);
        
        // Should NOT notify application service for rejected docs
        verify(applicationServiceClient, never()).notifyDocumentsVerified(anyLong(), anyBoolean(), anyString());
    }

    @Test
    void verifyNotFound() {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(true);

        when(documentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
            documentService.verify(999L, request, BEARER_TOKEN));
    }

    // ==================== listForApplication ====================

    @Test
    void listForApplicationSuccess() {
        when(documentRepository.findByApplicationIdOrderByCreatedAtDesc(10L))
                .thenReturn(List.of(documentEntity));

        List<DocumentDto> result = documentService.listForApplication(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getApplicationId());
    }

    @Test
    void listForApplicationEmpty() {
        when(documentRepository.findByApplicationIdOrderByCreatedAtDesc(999L))
                .thenReturn(List.of());

        List<DocumentDto> result = documentService.listForApplication(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
