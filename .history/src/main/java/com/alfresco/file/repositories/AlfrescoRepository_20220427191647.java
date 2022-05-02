package com.alfresco.file.repositories;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import com.alfresco.file.models.File;
import com.alfresco.file.models.Node;
import com.alfresco.file.models.Entry;
import com.alfresco.file.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AlfrescoRepository {

  public Entry searchFile(String id) {

    File file = new File();
    file.setId(id);

    // String basicAuthHeader = "basic " +
    // Base64Utils.encodeToString((Constants.alfrescoUser + ":" +
    // Constants.alfrescoPass).getBytes());
    String basicAuthHeader = "basic " + Base64Utils.encodeToString(("admin" + ":" + "admin").getBytes());

    WebClient webClient = WebClient.builder()
        .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        // .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    // UriSpec<RequestBodySpec> uriSpec = client.post();

    //UriSpec<?> uriSpec = webClient.get();

    // String base = Constants.alfrescoBase;
    String base = "http://localhost:8080";
    String path = "/alfresco";
    String api = "/api";
    String tenant = "/-default-";
    String visibility = "/public";
    String repo = "/alfresco";
    String versions = "/versions/1";
    String node = "/nodes/-my-";
    String children = "/children";

    URI url = URI.create(base + path + api + tenant + visibility + repo + versions + node);
    
    
    // RequestHeadersSpec<?> bodySpec = uriSpec
    //     // .uri(URI.create(base + path + api + tenant + visibility + repo + versions +
    //     // node + children));
    //     .uri(url);

    // // RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("entries");

    // // Flux<File> files = body.exchangeToFlux(new ResponseBodyResultHandler(null,
    // // null));

    // // Flux<File> files = headersSpec.retrieve()
    // // .bodyToFlux(File.class);

    // Mono<Object> response = bodySpec.exchangeToMono(responseL -> {
    //   if (responseL.statusCode()
    //       .equals(HttpStatus.OK)) {
    //     return responseL.bodyToMono(Object.class);
    //   } else if (responseL.statusCode()
    //       .is4xxClientError()) {
    //     return Mono.just("Error response" + responseL.bodyToMono(String.class));
    //   } else {
    //     return responseL.createException()
    //         .flatMap(Mono::error);
    //   }
    // });

    // Object object = response.block();

    ObjectMapper mapper = new ObjectMapper();

    // Node entry = mapper.convertValue(object, Node.class);
    // Arrays.stream(objects)
    // .map(object -> mapper.convertValue(object, Reader.class))
    // .map(Reader::getFavouriteBook)
    // .collect(Collectors.toList());

    // return entry.getEntry();

  
    //////////   Upload a file

      return null;

  }



  public String uploadFile(MultipartFile multipartFile){

    String basicAuthHeader = "basic " + Base64Utils.encodeToString(("admin" + ":" + "admin").getBytes());

    WebClient webClient = WebClient.builder()
    .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
          //header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        .build();

    String base = "http://localhost:8080";
    String path = "/alfresco";
    String api = "/api";
    String tenant = "/-default-";
    String visibility = "/public";
    String repo = "/alfresco";
    String versions = "/versions/1";
    String node = "/nodes/-my-";
    String children = "/children";

    URI url = URI.create(base + path + api + tenant + visibility + repo + versions + node);


    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("file", multipartFile.getResource());

    Mono<HttpStatus> httpStatusMono = webClient.post()
    .uri(url)
    .contentType(MediaType.MULTIPART_FORM_DATA)
    .body(BodyInserters.fromMultipartData(builder.build()))
    .exchangeToMono(response -> {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
        } else {
            try {
              throw new FileFailToDownloadException("Error uploading file");
            } catch (FileFailToDownloadException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
        }
        return null;
      });
    return "File uploaded successfully";

  }


}
