package com.alfresco.file.models;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class entry {

    private UUID id;
    private String name;
    private boolean isFolder;
    private boolean isFile;
    private String nodeType;
    private Map<String,String> properties;
}
