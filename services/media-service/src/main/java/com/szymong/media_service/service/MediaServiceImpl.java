package com.szymong.media_service.service;

import com.szymong.media_service.validation.ImageFileValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceImpl implements MediaService {

  private final CloudinaryService cloudinaryService;
  private final ImageFileValidator imageFileValidator;

  public MediaServiceImpl(
          CloudinaryService cloudinaryService,
          ImageFileValidator imageFileValidator
  ) {
    this.cloudinaryService = cloudinaryService;
    this.imageFileValidator = imageFileValidator;
  }

  @Override
  public String uploadProfileImage(MultipartFile file) {
    imageFileValidator.validateImage(file);
    return cloudinaryService.uploadProfileImage(file);
  }

  @Override
  public String uploadTripImage(MultipartFile file) {
    imageFileValidator.validateImage(file);
    return cloudinaryService.uploadTripImage(file);
  }

  @Override
  public void deleteImage(String publicId) {
    cloudinaryService.deleteImage(publicId);
  }
}