package com.szymong.media_service.controller;

import com.szymong.media_service.service.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
public class MediaController {

  private final MediaService mediaService;

  public MediaController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @PostMapping(value = "/images/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.status(HttpStatus.CREATED).body(mediaService.uploadProfileImage(file));
  }

  @PostMapping(value = "/images/trip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadTripImage(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.status(HttpStatus.CREATED).body(mediaService.uploadTripImage(file));
  }

  @DeleteMapping("/images")
  public ResponseEntity<Void> deleteImage(@RequestParam("publicId") String publicId) {
    mediaService.deleteImage(publicId);
    return ResponseEntity.noContent().build();
  }
}