package com.alfresco.file.controllers;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class MainController {
    

    @GetMapping("/folder/{id}")
    public void getFilesFromFolder(@Valid @PathParam){

    }

    @GetMapping("/file/{id}")
    public void getFile(){

    }

    @PostMapping
    public void createFolder(){

    }

    @

    @PostMapping
    public void uploadFiletoFolder(){

    }
}
