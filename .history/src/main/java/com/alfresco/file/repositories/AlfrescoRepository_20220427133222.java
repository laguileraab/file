package com.alfresco.file.repositories;

import com.alfresco.file.models.File;
import com.alfresco.file.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

public class AlfrescoRepository {
    
    public File searchFile(String id){
        
        WebClient client = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build();
        
        return file;
    }
}
