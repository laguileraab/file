package com.alfresco.file.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/folder")
@RestController
public class FolderController {
    
    @GetMapping("/{id}")
    public void getFilesFromFolder(@Valid @PathVariable String id){

    }

    @PostMapping("/folder")
    public void createFolder(@Valid @RequestBody String name){

    }
}
