package com.alfresco.file.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

public class Constants {
    private Constants(){}
    // @Value("${alfresco.host}")
    // public static String alfrescoBase;
    // @Value("${alfresco.u}")
    // public static String alfrescoUser;
    // @Value("${alfresco.p}")
    // public static String alfrescoPass;

    private String basicAuthHeader = "basic " + Base64Utils.encodeToString(("admin" + ":" + "admin").getBytes());

    private String base = "http://localhost:8080";
    private String path = "/alfresco";
    private String api = "/api";
    private String tenant = "/-default-";
    private String visibility = "/public";
    private String repo = "/alfresco";
    private String versions = "/versions/1";
    private String node = "/nodes";
    private String nodeIdByDefault = "/-my-";
    // String nodeId = id;
    private String children = "/children";
    private String downloads = "/downloads";
  
    String URL = base + path + api + tenant + visibility + repo + versions;

}
