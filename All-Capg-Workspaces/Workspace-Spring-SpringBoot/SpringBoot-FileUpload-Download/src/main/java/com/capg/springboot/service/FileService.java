package com.capg.springboot.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capg.springboot.entity.FileEntity;
import com.capg.springboot.repository.FileRepository;


 // Service layer – business logic for file upload/download

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // Save file into Oracle DB
    public FileEntity saveFile(MultipartFile file) throws IOException {

        FileEntity entity = new FileEntity();
        entity.setFileName(file.getOriginalFilename());
        entity.setFileType(file.getContentType());
        entity.setData(file.getBytes());

        return fileRepository.save(entity);
    }

    // Fetch file from Oracle DB
    public Optional<FileEntity> getFile(Long id) {
        return fileRepository.findById(id);
    }
}