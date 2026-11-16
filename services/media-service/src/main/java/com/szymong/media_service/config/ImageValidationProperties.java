package com.szymong.media_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.Set;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.images")
public class ImageValidationProperties {

  private DataSize maxFileSize;
  private Set<String> allowedContentTypes;
}
