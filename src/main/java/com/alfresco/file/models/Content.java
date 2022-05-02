package com.alfresco.file.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String mimeType;
    private String mimeTypeName;
    private int sizeInBytes;
    private String encoding;
}
