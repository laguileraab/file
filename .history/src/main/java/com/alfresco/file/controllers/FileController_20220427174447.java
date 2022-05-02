package com.alfresco.file.controllers;

import java.util.List;

import javax.validation.Valid;

import com.alfresco.file.models.File;
import com.alfresco.file.response.MessageResponse;
import com.alfresco.file.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Mono<String>> getFile(@Valid @PathVariable String id) {
        FileController.log.info("Show file with id " + id);
        Mono<String> file = fileService.getFile(id);
        return ResponseEntity.ok(file);

    }

    @PostMapping
    public void uploadFiletoFolder(@Valid @RequestBody MultipartFile file) {

    }

    @PutMapping("/{id}")
    public void updateFile(@Valid @PathVariable String id) {

    }

    @DeleteMapping("/{id}")
    public void deleteFile(@Valid @PathVariable String id) {

    }
}
