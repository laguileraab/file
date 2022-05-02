package com.alfresco.file.services;

import com.alfresco.file.models.File;
import com.alfresco.file.repositories.AlfrescoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    @Autowired
    private AlfrescoRepository alfresco;

    public File getFile(String id){

        File file = new File();

        file = alfresco.searchFile(id);

        return file;
    }

}
