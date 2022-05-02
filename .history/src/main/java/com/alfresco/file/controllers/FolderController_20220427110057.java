package com.alfresco.file.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/folder")
@RestController
public class FolderController {
    
    @GetMapping("/folder/{id}")
    public void getFilesFromFolder(@Valid @PathVariable String id){

    }

}
