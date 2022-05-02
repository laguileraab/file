package com.alfresco.file.services;

import com.alfresco.file.models.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    @Autowired
    private AlfrescoRepository alfresco;

    public File getFile(String id){

        File file = new File();

        file.setId(id);

        file = alfresco.searchFile(file);

        return file;
    }

}
