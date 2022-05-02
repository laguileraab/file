package com.alfresco.file.repositories;

import java.net.URI;
import java.util.Collections;

import com.alfresco.file.models.File;
import com.alfresco.file.utils.Constants;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AlfrescoRepository {

    public Mono<File> searchFile(String id) {

        File file = new File();
        file.setId(id);

        String basicAuthHeader = "basic " + Base64Utils.encodeToString((Constants.alfrescoUser + ":" + Constants.alfrescoPass).getBytes());

        WebClient client = WebClient.builder()
                .baseUrl(Constants.alfrescoBase)
                .defaultHeaders((header) -> {
                    header.add(HttpHeaders.AUTHORIZATION, basicAuthHeader);
                    header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                // .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // UriSpec<RequestBodySpec> uriSpec = client.post();
        
        UriSpec<?> uriSpec = client.get();

        String path = "/alfresco";
        String api = "/api";
        String tenant = "/-default-";
        String visibility = "/public";
        String repo = "/alfresco";
        String versions = "/versions/1";
        String node = "/nodes/-my-";
        String children = "/children";
        

        RequestHeadersSpec<?> bodySpec = uriSpec
                .uri(URI.create(path + api + tenant + visibility + repo + versions + node + children));
        
        //RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("entries");

                // Flux<File> files = body.exchangeToFlux(new ResponseBodyResultHandler(null, null));

        // Flux<File> files = headersSpec.retrieve()
        //         .bodyToFlux(File.class);


        Mono<Object> response = bodySpec.exchangeToMono(responseL -> {
            if (responseL.statusCode()
              .equals(HttpStatus.OK)) {
                return responseL.bodyToMono(File.class);
            } else if (responseL.statusCode()
              .is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return responseL.createException()
                  .flatMap(Mono::error);
            }
          });


        return files.filter(p -> p.getId().equals(id)).next();

    }
}
