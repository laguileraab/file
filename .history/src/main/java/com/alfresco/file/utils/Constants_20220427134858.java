package com.alfresco.file.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    private Constants(){}
    @Value("{alfresco.host}")
    public static String alfrescoBase;
    @Value("{alfresco.u}")
    public static String alfrescoUser;
    @Value("{alfresco.p}")
    public static String alfrescoPass;
}
