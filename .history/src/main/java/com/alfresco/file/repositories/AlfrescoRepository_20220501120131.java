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
import java.util.zip.ZipOutputStream;

import com.alfresco.file.models.FileDownload;
import com.alfresco.file.models.Node;
import com.alfresco.file.models.NodeDownload;
import com.alfresco.file.models.NodeIds;
import com.alfresco.file.utils.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class AlfrescoRepository {

  public Node getNode(String id) {

    WebClient webClient = WebClient.builder()
        // .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
          header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        })
        .build();

    URI url = URI.create(Constants.URL + Constants.NODE + id);
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

  public boolean isDownloadReady(String downloadId) throws Exception {
    String status = "PENDING";
    while (status.equals("PENDING")) {
      WebClient webClient = WebClient.builder()
          .defaultHeaders((header) -> {
            header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
            header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
          })
          .build();

      URI url = URI.create(Constants.URL + Constants.DOWNLOADS + downloadId);

      Mono<Object> responseObject = webClient.get()
          .uri(url)
          .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.CREATED)
                || response.statusCode().equals(HttpStatus.OK)
                || response.statusCode().equals(HttpStatus.ACCEPTED)) {
              return response.bodyToMono(Object.class);
            } else {
              try {
                throw new FileFailToUploadException("Error during download information " + response.bodyToMono(Object.class));
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
      status = entry.getEntry().getStatus();
    }

    if (status.equals("DONE")) {
      return true;
    } else {
      throw new Exception();
    }
  }

  public FileDownload downloadFile(String id) throws IOException, MalformedURLException {

    String downloadId = downloadFileURL(id); //Send the download to alfresco, and get download Id
    
    Node node = getNode(id); //Get info of the file: filename, fileType, etc.

    While()

    try (BufferedInputStream in = new BufferedInputStream(new
    URL(Constants.URL).openStream());
    FileOutputStream fileOutputStream = new FileOutputStream(entry.getEntry().getId())) {
    byte dataBuffer[] = new byte[1024];
    int bytesRead;
    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
    fileOutputStream.write(dataBuffer, 0, bytesRead);
    }
    } catch (IOException e) {
    // handle exception
    }

    ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(Constants.URL).openStream());
    FileOutputStream fileOutputStream = new FileOutputStream(entry.getEntry().getId());
    FileChannel fileChannel = fileOutputStream.getChannel();

    fileOutputStream.getChannel()
        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);


        /*
        response.setContentType("application/zip");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=sample.zip");

        StreamingResponseBody stream = out -> {

            ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            if(directory.exists() && directory.isDirectory()) {
                try {
                    for (final File file : directory.listFiles()) {
                        final InputStream inputStream=new FileInputStream(file);
                        final ZipEntry zipEntry=new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes=new byte[1024];
                        int length;
                        while ((length=inputStream.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                        inputStream.close();
                    }
                    zipOut.close();
                } catch (final IOException e) {
                    logger.error("Exception while reading and streaming data {} ", e);
                }
            }
        };
        AlfrescoRepository.log.info("steaming response {} ", stream);
        // return new ResponseEntity(stream, HttpStatus.OK);
        */
        return null;
  }

  public String downloadFileURL(String id) {
    WebClient webClient = WebClient.builder()
        // .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
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

    NodeDownload entry = mapper.convertValue(object, NodeDownload.class);

    return Constants.URL + entry.getEntry().getId() + Constants.CONTENT;
  }

  public Node uploadFile(MultipartFile multipartFile) {

    WebClient webClient = WebClient.builder()
        // .baseUrl(Constants.alfrescoBase)
        .defaultHeaders((header) -> {
          header.add(HttpHeaders.AUTHORIZATION, Constants.BASICAUTHHEADER);
        })
        .build();

    URI url = URI.create(Constants.URL + Constants.NODE + Constants.NODEIDBYDEFAULT + Constants.CHILDREN);

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", "Test" + new Random().nextInt()); // Just for testing
    // map.add("name", multipartFile.getOriginalFilename()); // Use this instead
    map.add("nodeType", "cm:content");
    map.add("relativePath", "Cliente"); // Folder path

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
