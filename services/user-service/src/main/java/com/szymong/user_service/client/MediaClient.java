package com.szymong.user_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MediaClient {

  private final RestClient restClient;

  public MediaClient(RestClient.Builder builder, @Value("${app.services.media.url}") String mediaServiceUrl) {
    this.restClient = builder.baseUrl(mediaServiceUrl).build();
  }
  public String uploadProfileImage(MultipartFile file) {
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", file.getResource());

    return restClient.post()
                   .uri("/api/media/images/profile")
                   .contentType(MediaType.MULTIPART_FORM_DATA)
                   .body(body)
                   .retrieve()
                   .body(String.class);
  }

  public void deleteImage(String publicId) {
    restClient.delete()
            .uri(uriBuilder -> uriBuilder
                                       .path("/api/media/images")
                                       .queryParam("publicId", publicId)
                                       .build())
            .retrieve()
            .toBodilessEntity();
  }
}