package com.alfresco.file.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private String id;
    private String name;
    private int size;
    private byte[] file;

}
