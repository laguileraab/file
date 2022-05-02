package com.alfresco.file.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponseNode {
    private HttpStatus status;
    private Node message;
}
