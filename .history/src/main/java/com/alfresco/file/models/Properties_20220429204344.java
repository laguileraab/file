package com.alfresco.file.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Properties {
    
    @JsonProperty("cm:versionLabel")
    private String versionLabel;
    @JsonProperty("cm:versionType")
    private String versionType;

}