package com.alfresco.file.services;

import com.alfresco.file.models.File;
import com.alfresco.file.models.FileUpload;
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

    public FileUpload downloadFile(String id){

        return alfresco.downloadFile(id);
        
    }

    public String uploadFile(MultipartFile multipartFile){
        return alfresco.uploadFile(multipartFile);
    }

}
