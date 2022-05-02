package com.alfresco.file.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private String id;
    private String name;
    private int size;
    private byte[] file;

}
