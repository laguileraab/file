package com.alfresco.file.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    private Constants(){}
    @Value("{alfresco.host}")
    public static String ALFRESCOBASE;
    @Value("{alfresco.u}")
    public static String ALFRESCOUSER;
    @Value("{alfresco.p}")
    public static String ALFRESCOPASS;
}
