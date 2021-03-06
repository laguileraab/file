package com.alfresco.file.repositories;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Random;

import com.alfresco.file.exceptions.DownloadException;
import com.alfresco.file.exceptions.FileFailToUploadException;
import com.alfresco.file.models.FileDownload;
import com.alfresco.file.models.Node;
import com.alfresco.file.models.NodeDownload;
import com.alfresco.file.models.NodeIds;
import com.alfresco.file.utils.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class AlfrescoRepository {

  public Node getNode(String id) {

    WebClient webClient = WebClient.builder()
        // .baseUrl(Constants.alfrescoBase)
        .defaultHeaders(header -> {
          header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        .build();

    URI url = URI.create(Constants.URL + Constants.NODE + "/" + id);
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
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return mapper.convertValue(object, Node.class);

  }

  public boolean isDownloadReady(String downloadId) throws DownloadException {
    String status = "PENDING";
    while (status.equals("PENDING") || status.equals("IN_PROGRESS")) {
      WebClient webClient = WebClient.builder()
          .defaultHeaders(header -> {
            header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
            header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
          })
          .build();

      URI url = URI.create(Constants.URL + Constants.DOWNLOADS + "/" + downloadId);

      Mono<Object> responseObject = webClient.get()
          .uri(url)
          .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.CREATED)
                || response.statusCode().equals(HttpStatus.OK)
                || response.statusCode().equals(HttpStatus.ACCEPTED)) {
              return response.bodyToMono(Object.class);
            } else {
              try {
                throw new DownloadException("Error during download information " + response.bodyToMono(Object.class));
              } catch (DownloadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
            return response.bodyToMono(Object.class);
          });

      Object object = responseObject.block();
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

      NodeDownload entry = mapper.convertValue(object, NodeDownload.class);
      status = entry.getEntry().getStatus();
    }

    if (status.equals("DONE")) {
      return true;
    } else {
      AlfrescoRepository.log.info("Download Status " + status + "for id " + downloadId);
      throw new DownloadException("There's been an error while requesting for download");
    }
  }

  public FileDownload downloadFile(String id) throws InterruptedException, DownloadException, FileNotFoundException {

    String downloadId = downloadFileURLId(id); // Request download to alfresco and get download Id

    Node node = getNode(id); // Get info of the file: filename, fileType, etc.

    while (!isDownloadReady(downloadId)) {
      Thread.sleep(300);
    }

    FileDownload file = new FileDownload();
    file.setFilename(node.getEntry().getName());
    file.setId(id);
    // file.setFileType(node.getEntry().getContent().getMimeType());
    file.setFileSize(node.getEntry().getContent().getSizeInBytes());

    WebClient webClient = WebClient.builder()
        .defaultHeaders(header -> {
          header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
        })
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs()
                .maxInMemorySize(2 * 1024))
            .build())
        .build();

    Flux<ByteArrayResource> fileByteArray = webClient.get()
        .uri(Constants.URL
            + Constants.NODE + "/" + downloadId + Constants.CONTENT)
        .accept(MediaType.APPLICATION_OCTET_STREAM)
        .retrieve()
        .bodyToFlux(ByteArrayResource.class);
    fileByteArray.map(ByteArrayResource::getByteArray);

    Flux<DataBuffer> dataBufferFlux = webClient
        .get()
        .uri(Constants.URL
            + Constants.NODE + "/" + downloadId + Constants.CONTENT)
        .retrieve()
        .bodyToFlux(DataBuffer.class);

    Mono<byte[]> mergeDataBuffers = DataBufferUtils.join(dataBufferFlux)
        .map(dataBuffer -> {
          byte[] bytes = new byte[dataBuffer.readableByteCount()];
          dataBuffer.read(bytes);
          DataBufferUtils.release(dataBuffer);
          return bytes;
        });

    file.setFile(mergeDataBuffers.block());

    return file;
  }

  public String downloadFileURLId(String id) {
    WebClient webClient = WebClient.builder()
        // .baseUrl(Constants.alfrescoBase)
        .defaultHeaders(header -> {
          header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        .build();

    URI url = URI.create(Constants.URL + Constants.DOWNLOADS);

    Mono<Object> responseObject = webClient.post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(new NodeIds(new String[] { id })))
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.CREATED)
              || response.statusCode().equals(HttpStatus.OK)
              || response.statusCode().equals(HttpStatus.ACCEPTED)) {
            return response.bodyToMono(Object.class);
          } else {
            try {
              throw new FileFailToUploadException("Error downloading file" + response.bodyToMono(Object.class));
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

    NodeDownload entry = mapper.convertValue(object, NodeDownload.class);

    return entry.getEntry().getId();
  }

  public Node uploadFile(MultipartFile multipartFile) {

    WebClient webClient = WebClient.builder()
        // .baseUrl(Constants.alfrescoBase)
        .defaultHeaders(header -> {
          header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
        })
        .build();

    URI url = URI.create(Constants.URL + Constants.NODE + Constants.NODEIDBYDEFAULT + Constants.CHILDREN);

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", "Test" + new Random().nextInt()); // Just for testing
    // map.add("name", multipartFile.getOriginalFilename()); // Use this instead
    map.add("nodeType", "cm:content");
    map.add("relativePath", "Clientes/cliente"); // Folder path

    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("filedata", multipartFile.getResource());

    Mono<Object> responseObject = webClient.post()
        .uri(url)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build())
            .with(map))
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.CREATED)
              || response.statusCode().equals(HttpStatus.OK)) {
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

    return mapper.convertValue(object, Node.class);
  }

}
