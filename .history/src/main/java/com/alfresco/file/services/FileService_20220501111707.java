package com.alfresco.file.services;

import com.alfresco.file.models.FileUpload;
import com.alfresco.file.models.Node;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import com.alfresco.file.models.Entry;
import com.alfresco.file.models.EntryDownload;
import com.alfresco.file.repositories.AlfrescoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

@Service
public class FileService {
    
    @Autowired
    private AlfrescoRepository alfresco;


    public Node getNode(){
        return alfresco.getNode();
    }

    public FileUpload downloadFile(String id) throws MalformedURLException, IOException{

        return alfresco.downloadFile(id);
        
    }

    public String downloadFileURL(String id) throws MalformedURLException, IOException{

        return alfresco.downloadFileURL(id);
        
    }

    public Node uploadFile(MultipartFile multipartFile){
        //String node = alfresco.createFileNode(multipartFile);
        
        return alfresco.uploadFile(multipartFile);
    }

}
