package com.alfresco.file.services;

import com.alfresco.file.models.FileDownload;
import com.alfresco.file.models.Node;

import java.io.IOException;
import java.net.MalformedURLException;

import com.alfresco.file.repositories.AlfrescoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private AlfrescoRepository alfresco;

    public Node getNode() {
        return alfresco.getNode(String id);
    }

    public FileDownload downloadFile(String id) throws MalformedURLException, IOException {
        return alfresco.downloadFile(id);
    }

    public String downloadFileURL(String id) {
        return alfresco.downloadFileURL(id);
    }

    public Node uploadFile(MultipartFile multipartFile) {
        return alfresco.uploadFile(multipartFile);
    }

}
