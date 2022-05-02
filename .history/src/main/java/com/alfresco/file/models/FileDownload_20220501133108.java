package com.alfresco.file.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;

@Getter
@Setter
@NoArgsConstructor
public class FileDownload {
    private String id;
    private String filename;
    private String fileType;
    private int fileSize;
    private Flux<byte[]> file;
}
