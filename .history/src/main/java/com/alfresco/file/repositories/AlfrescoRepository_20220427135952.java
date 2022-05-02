package com.alfresco.file.repositories;

import java.net.URI;
import java.util.Collections;

import com.alfresco.file.models.File;
import com.alfresco.file.utils.Constants;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AlfrescoRepository {

    public Mono<File> searchFile(String id) {

        File file = new File();
        file.setId(id);

        WebClient client = WebClient.builder()
                .baseUrl(Constants.alfrescoBase)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        UriSpec<RequestBodySpec> uriSpec = client.post();

        String path = "/alfresco";
        String api = "api";
        String tenant = "/-default-";
        String visibility = "/public";
        String repo = "/alfresco";
        String versions = "/versions/1";
        String node = "/nodes/-my-";
        String children = "/children";

        RequestBodySpec bodySpec = uriSpec
                .uri(URI.create(path + api + tenant + visibility + repo + versions + node + children));

        RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("entries");

        Flux<File> files = headersSpec.retrieve()
                .bodyToFlux(File.class);

        return files.filter(p -> p.getId().equals(id)).single();

    }
}
