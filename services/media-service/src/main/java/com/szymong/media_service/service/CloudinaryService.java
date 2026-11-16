package com.szymong.media_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
  String uploadProfileImage(MultipartFile file);

  String uploadTripImage(MultipartFile file);

  void deleteImage(String publicId);
}
