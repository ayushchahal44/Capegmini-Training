package com.capg.ayush.document.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.capg.ayush.document.dto.DocumentDto;
import com.capg.ayush.document.entity.DocType;
import com.capg.ayush.document.service.DocumentService;
import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;

@WebMvcTest(DocumentController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings("null")
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentService documentService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private com.capg.ayush.finflow.common.jwt.JwtUtil jwtUtil;

    @Test
    void uploadSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());
        DocumentDto dto = new DocumentDto();
        dto.setId(1L);
        dto.setOriginalName("test.pdf");

        when(documentService.upload(anyLong(), any(DocType.class), any(MultipartFile.class))).thenReturn(dto);

        mockMvc.perform(multipart("/api/documents/upload")
                .file(file)
                .param("applicationId", "1")
                .param("docType", "ID_PROOF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.originalName").value("test.pdf"));
    }

    @Test
    void listForApplicationSuccess() throws Exception {
        DocumentDto dto = new DocumentDto();
        dto.setId(1L);

        when(documentService.listForApplication(anyLong())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/documents/application/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
