package com.capg.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


 //Entity class for storing file details in Oracle DB

@Entity
@Table(name = "FILE_STORE")
public class FileEntity {

    @Id
    @SequenceGenerator(
            name = "file_seq_gen",
            sequenceName = "FILE_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
    private Long id;

    // Original file name
    @Column(name = "FILE_NAME")
    private String fileName;

    // MIME type (pdf, image/jpeg, etc.)
    @Column(name = "FILE_TYPE")
    private String fileType;

    // File content stored as BLOB
    @Lob
    @Column(name = "DATA", columnDefinition = "BLOB")
    private byte[] data;

    // ===== Getters & Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}