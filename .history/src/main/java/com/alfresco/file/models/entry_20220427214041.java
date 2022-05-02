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
    private Map<String, String>[] aspectNames;
    private String createdAt;
    private String isFolder;
    private String isFile;
    private user createdByUser;
    private String modifiedAt;
    private user modifiedByUser;
    private String name;
    private String id;
    private String nodeType;
    private Map<String, String>[] properties;
    private String parentId;

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
