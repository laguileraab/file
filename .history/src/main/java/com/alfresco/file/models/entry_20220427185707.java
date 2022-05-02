package com.alfresco.file.models;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private UUID parentId;
    @JsonIgnore
    private Map<String, String> aspectNames;
    @JsonIgnore
    private Map<String, String> properties;
}
