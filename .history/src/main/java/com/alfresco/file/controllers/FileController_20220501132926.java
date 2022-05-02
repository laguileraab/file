package com.alfresco.file.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import javax.validation.Valid;

import com.alfresco.file.response.MessageResponseNode;
import com.alfresco.file.services.FileService;
import com.alfresco.file.utils.Constants;
import com.alfresco.file.models.FileDownload;
import com.alfresco.file.models.Node;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/info/{id}")
    public ResponseEntity<Node> getNode(String id) throws MalformedURLException, IOException {
        Node node = fileService.getNode(id);
        return ResponseEntity.ok()
                .body(node);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flux<byte[]>> downloadFileContent(@Valid @PathVariable String id)
            throws MalformedURLException, IOException, InterruptedException {
        FileController.log.info("Downloading file {}...", id);
        Flux<byte[]> file = fileService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFile()));
        // return ResponseEntity.ok()
        //         .contentType(MediaType.parseMediaType("application/octet-stream"))
        //         .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "Test" + "\"")
        //         .body(file);
    }

    @GetMapping("/url/{id}")
    public String downloadURL(@Valid @PathVariable String id)
            throws MalformedURLException, IOException {
        FileController.log.info("Downloading file {}...", id);
        return fileService.downloadFileURL(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<MessageResponseNode> upload(@RequestParam("file") MultipartFile file) {
        FileController.log.info("Uploading file...");
        Node node = fileService.uploadFile(file);
        return ResponseEntity
                .created(URI.create(Constants.URL + "/" + node.getEntry().getId()))
                .body(new MessageResponseNode(HttpStatus.CREATED, node));
    }

    @PutMapping("/{id}")
    public void updateFile(@Valid @PathVariable String id) {

    }

    @DeleteMapping("/{id}")
    public void deleteFile(@Valid @PathVariable String id) {

    }
}
