package com.alfresco.file.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDownload {
    private String filesAdded;
    private Long bytesAdded;
    private Long totalBytes;
    private String id;
    private int totalFiles;
    private String status;
}
