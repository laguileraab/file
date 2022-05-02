package com.alfresco.file.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDownload {
    private String id;
    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;
}
