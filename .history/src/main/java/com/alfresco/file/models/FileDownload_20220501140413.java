package com.alfresco.file.models;

import org.springframework.core.io.ByteArrayResource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Setter
@NoArgsConstructor
public class FileDownload {
    private String id;
    private String filename;
    private String fileType;
    private int fileSize;
    private byte[] file;
}