package com.alfresco.file.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    private UUID id;
    private String name;
    private boolean isFolder;
    private boolean isFile;
    private String nodeType;
    private Properties properties;
}
