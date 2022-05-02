package com.alfresco.file.services;

import com.alfresco.file.models.FileDownload;
import com.alfresco.file.models.Node;

import java.io.IOException;
import java.net.MalformedURLException;

import com.alfresco.file.repositories.AlfrescoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Flux;

@Service
public class FileService {

    @Autowired
    private AlfrescoRepository alfresco;

    public Node getNode(String id) {
        return alfresco.getNode(id);
    }

    public Flux<byte[]> downloadFile(String id) throws MalformedURLException, IOException, InterruptedException {
        return alfresco.downloadFile(id);
    }

    public String downloadFileURL(String id) {
        return alfresco.downloadFileURL(id);
    }

    public Node uploadFile(MultipartFile multipartFile) {
        return alfresco.uploadFile(multipartFile);
    }

}
