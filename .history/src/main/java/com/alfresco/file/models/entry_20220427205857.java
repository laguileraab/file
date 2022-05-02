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
public class Entry {

    public Entry(String id) {
        this.id = id;
    }

    private String id;
    private String name;
    private String createdAt;
    private String modifiedAt;
    private boolean isFolder;
    private boolean isFile;
    private String nodeType;
    private user createdByUser;
    private user modifiedByUser;
    private UUID parentId;
    private String aspectNames;
    private String properties;
}
