package com.alfresco.file.services;

import com.alfresco.file.models.File;
import com.alfresco.file.models.Entry;
import com.alfresco.file.repositories.AlfrescoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

@Service
public class FileService {
    
    @Autowired
    private AlfrescoRepository alfresco;

    public Entry downloadFile(String id){

        return alfresco.downloadFile(id);
        
    }

    public void uploadFile(MultipartFile multipartFile){
        return alfresco.uploadFile(multipartFile);
    }

}
