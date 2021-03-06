package com.alfresco.file.utils;

import org.springframework.util.Base64Utils;

public class Constants {
    private Constants(){}
    // @Value("${alfresco.host}")
    // public static String alfrescoBase;
    // @Value("${alfresco.u}")
    // public static String alfrescoUser;
    // @Value("${alfresco.p}")
    // public static String alfrescoPass;

    public static final String USERALFRESCO = "admin";
    public static final String PASSALFRESCO = "admin";
    public static final String BASICAUTHHEADER = "basic " + Base64Utils.encodeToString((USERALFRESCO + ":" + PASSALFRESCO).getBytes());
    public static final String BASE = "http://localhost:8080";
    public static final String PATH = "/alfresco";
    public static final String API = "/api";
    public static final String TENANT = "/-default-";
    public static final String VISIBILITY = "/public";
    public static final String REPO = "/alfresco";
    public static final String VERSIONS = "/versions/1";
    public static final String NODE = "/nodes";
    public static final String NODEIDBYDEFAULT = "/-my-";
    // String nodeId = id;
    public static final String CHILDREN = "/children";
    public static final String DOWNLOADS = "/downloads";
    public static final String CONTENT = "/content";

    public static final String URL = BASE + PATH + API + TENANT + VISIBILITY + REPO + VERSIONS;

}
