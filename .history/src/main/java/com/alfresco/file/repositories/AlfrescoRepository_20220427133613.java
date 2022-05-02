package com.alfresco.file.repositories;

import java.util.Collections;

import com.alfresco.file.models.File;
import com.alfresco.file.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

public class AlfrescoRepository {
    
    public File searchFile(String id){
        
        WebClient client = WebClient.builder()
        .baseUrl(Constants.ALFRESCOBASE)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build();

        UriSpec<RequestBodySpec> uriSpec = client.post();

        RequestBodySpec bodySpec = uriSpec.uri(URI.create("/resource"));

        return file;
    }
}