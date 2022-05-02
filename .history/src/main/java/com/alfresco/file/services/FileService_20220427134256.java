package com.alfresco.file.services;

import com.alfresco.file.models.File;
import com.alfresco.file.repositories.AlfrescoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class FileService {
    
    @Autowired
    private AlfrescoRepository alfresco;

    public Mono<File> getFile(String id){

        return alfresco.searchFile(id);
        
    }

}
