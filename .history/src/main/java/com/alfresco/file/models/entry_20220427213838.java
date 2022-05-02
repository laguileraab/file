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
    private Map<String, String> aspectNames;
    private String createdAt;
    private boolean isFolder;
    private boolean isFile;
    private String id;
    private String name;
    private String modifiedAt;
    private String nodeType;
    private user createdByUser;
    private user modifiedByUser;
    private String parentId;
    private Map<String, String> properties;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    @JsonAnyGetter
    public Map<String, String> getAspectNames() {
        return aspectNames;
    }

    @JsonAnySetter
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @JsonAnySetter
    public void setAspectNames(Map<String, String> aspectNames) {
        this.aspectNames = aspectNames;
    }

}
