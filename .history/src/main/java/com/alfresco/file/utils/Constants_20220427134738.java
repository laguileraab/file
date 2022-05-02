package com.alfresco.file.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    private Constants(){}
    @Value("{alfresco.host}")
    public static final String ALFRESCOBASE = "";
}
