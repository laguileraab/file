package com.alfresco.file.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/file")
@RestController
public class FileController {
    @GetMapping("/{id}")
    public void getFile(@Valid @PathVariable String id){

    }
    @PostMapping
    public void uploadFiletoFolder(@Valid @RequestBody MultipartFile file){

    }
}
