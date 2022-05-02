package com.alfresco.file.repositories;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import com.alfresco.file.models.File;
import com.alfresco.file.models.FileUpload;
import com.alfresco.file.models.Node;
import com.alfresco.file.models.Entry;
import com.alfresco.file.utils.Constants;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class AlfrescoRepository {

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

  String URL = base + path + api + tenant + visibility + repo + versions + node ;

  public Node getNode() {

    WebClient webClient = WebClient.builder()
        .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        .build();

    URI url = URI.create(URL + nodeIdByDefault);
    UriSpec<?> uriSpec = webClient.get();

    RequestHeadersSpec<?> bodySpec = uriSpec.uri(url);

    Mono<Object> response = bodySpec.exchangeToMono(responseL -> {
      if (responseL.statusCode()
          .equals(HttpStatus.OK)) {
        return responseL.bodyToMono(Object.class);
      } else if (responseL.statusCode()
          .is4xxClientError()) {
        return Mono.just("Error response " + responseL.bodyToMono(Object.class));
      } else {
        return responseL.createException()
            .flatMap(Mono::error);
      }
    });

    Object object = response.block();

    ObjectMapper mapper = new ObjectMapper();
    // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return mapper.convertValue(object, Node.class);

  }

  public String createFileNode(MultipartFile file) {

    WebClient webClient = WebClient.builder()
        .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        .build();

    UriSpec<RequestBodySpec> uriSpec = webClient.post();

    URI url = URI.create(URL + nodeIdByDefault + children);

    RequestBodySpec bodySpec = uriSpec.uri(url);

    // Mono<String> response = headersSpec.exchangeToMono(responseL -> {
    // if (responseL.statusCode()
    // .equals(HttpStatus.OK)) {
    // return responseL.bodyToMono(String.class);
    // } else if (responseL.statusCode()
    // .is4xxClientError()) {
    // return Mono.just("Error response " + responseL.bodyToMono(String.class));
    // } else {
    // return responseL.createException()
    // .flatMap(Mono::error);
    // }
    // });
    // Object object = response.block();

    // ObjectMapper mapper = new ObjectMapper();
    // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    // return mapper.convertValue(object, Node.class);
    // return response.block();
    return "";
  }

  public FileUpload downloadFile(String id) throws IOException, MalformedURLException {

    // String basicAuthHeader = "basic " +
    // Base64Utils.encodeToString((Constants.alfrescoUser + ":" +
    // Constants.alfrescoPass).getBytes());

    WebClient webClient = WebClient.builder()
        .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        // .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    // UriSpec<RequestBodySpec> uriSpec = client.post();

    // UriSpec<?> uriSpec = webClient.get();

    // String base = Constants.alfrescoBase;

    String URL = base + path + api + tenant + visibility + repo + versions + node;

    URI url = URI.create(URL);

    // Arrays.stream(objects)
    // .map(object -> mapper.convertValue(object, Reader.class))
    // .map(Reader::getFavouriteBook)
    // .collect(Collectors.toList());

    // return entry.getEntry();

    ////////// Upload a file

    String filename = "testfile";

    // try (BufferedInputStream in = new BufferedInputStream(new
    // URL(URL).openStream());
    // FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
    // byte dataBuffer[] = new byte[1024];
    // int bytesRead;
    // while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
    // fileOutputStream.write(dataBuffer, 0, bytesRead);
    // }
    // } catch (IOException e) {
    // // handle exception
    // }

    ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(URL).openStream());
    FileOutputStream fileOutputStream = new FileOutputStream(filename);
    FileChannel fileChannel = fileOutputStream.getChannel();

    fileOutputStream.getChannel()
        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

    return null;

  }

  public Node uploadFile(MultipartFile multipartFile) {

    WebClient webClient = WebClient.builder()
        .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
        })
        .build();

    URI url = URI.create(URL + nodeIdByDefault + children);

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", "Test" + new Random().nextInt());
    map.add("nodeType", "cm:content");
    map.add("relativePath", "Cliente");

    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("filedata", multipartFile.getResource());

    Mono<Object> responseObject = webClient.post()
        .uri(url)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build())
            .with(map))
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.CREATED)
              | response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(Object.class);
          } else {
            try {
              throw new FileFailToUploadException("Error uploading file" + response.bodyToMono(Object.class));
            } catch (FileFailToUploadException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          return response.bodyToMono(Object.class);
        });

    Object object = responseObject.block();
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    Node entry = mapper.convertValue(object, Node.class);

    return entry;
  }

}
