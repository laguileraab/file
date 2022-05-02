package com.alfresco.file.models;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
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

    @JsonIgnore
    private Map<Object, String> aspectNames;
    private String createdAt;
    private String isFolder;
    private String isFile;
    private UserInfoAlfresco createdByUser;
    private String modifiedAt;
    private UserInfoAlfresco modifiedByUser;
    private String name;
    private String id;
    private String nodeType;
    @JsonIgnore
    private Map<Object, String> properties;
    private String parentId;
    
}
