package com.alfresco.file.models;

import java.util.Date;
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
    private Date createdAt;
    private Date modifiedAt;
    private boolean isFolder;
    private boolean isFile;
    private String nodeType;
    private user createdByUser;
    private user modifiedByUser;
    
   // private Map<String,String> properties;
}
