package com.alfresco.file.services;

import com.alfresco.file.models.File;

import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    public File getFile(String id){

        File file = new File();

        file.setId(id);

        return file;
    }

}
