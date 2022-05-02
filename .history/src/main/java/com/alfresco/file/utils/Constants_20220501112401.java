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

    public static final  String base = "http://localhost:8080";
    public static final  String path = "/alfresco";
    public static final  String api = "/api";
    public static final  String tenant = "/-default-";
    public static final  String visibility = "/public";
    public static final  String repo = "/alfresco";
    public static final  String versions = "/versions/1";
    public static final  String node = "/nodes";
    public static final  String nodeIdByDefault = "/-my-";
    // String nodeId = id;
    public static final  String children = "/children";
    public static final String downloads = "/downloads";
  
    public static final String URL = base + path + api + tenant + visibility + repo + versions;

}
