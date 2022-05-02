package com.alfresco.file.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.alfresco.file.models.File;
import com.alfresco.file.models.Entry;
import com.alfresco.file.response.MessageResponse;
import com.alfresco.file.services.FileService;
import com.alfresco.file.models.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> download(@Valid @PathVariable String id) throws MalformedURLException, IOException {
        FileController.log.info("Downloading file {}...", id);
        FileUpload fileUpload = fileService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileUpload.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUpload.getFilename() + "\"")
                .body(new ByteArrayResource(fileUpload.getFile()));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<MessageResponse> upload(@RequestParam("file") MultipartFile file){
        FileController.log.info("Uploading file...");
        UUID id = fileService.uploadFile(file);
        return ResponseEntity
                .ok(new MessageResponse(HttpStatus.CREATED, id+""));
    }

    @PutMapping("/{id}")
    public void updateFile(@Valid @PathVariable String id) {

    }

    @DeleteMapping("/{id}")
    public void deleteFile(@Valid @PathVariable String id) {

    }
}
