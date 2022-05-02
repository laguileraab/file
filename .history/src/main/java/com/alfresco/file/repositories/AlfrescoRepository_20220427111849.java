package com.alfresco.file.repositories;

import com.alfresco.file.models.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AlfrescoRepository {
    
    @Autowired
    private RestTemplate restTemplate;

    public File searchFile(String id){
        
        File file = restTemplate.getForObject(Constants.AlfrescoBase, File.class);
        
        return file;
    }
}
