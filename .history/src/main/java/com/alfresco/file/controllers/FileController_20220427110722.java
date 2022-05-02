package com.alfresco.file.controllers;

import javax.validation.Valid;

import com.alfresco.file.response.MessageResponse;

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

@RequestMapping("/file")
@RestController
public class FileController {
    
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getFile(@Valid @PathVariable String id){
        fileService.getFile(id);
        return ResponseEntity.ok(new MessageResponse(HttpStatus.OK, ));

    }
    @PostMapping
    public void uploadFiletoFolder(@Valid @RequestBody MultipartFile file){

    }

    @PutMapping("/{id}")
    public void updateFile(@Valid @PathVariable String id){

    }

    @DeleteMapping("/{id}")
    public void deleteFile(@Valid @PathVariable String id){

    }
}
