package com.szymong.user_service.service;

import com.szymong.user_service.entity.User;
import com.szymong.user_service.dto.*;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
  UserResponse getUserById(Long id);

  CurrentUserResponse getCurrentUser();

  User getUserByClerkId(String clerkId);

  CreateUserResponse createUser(CreateUserRequest user);

  UserResponse getUserByUsername(String username);

  UpdateCurrentUserResponse updateUser(UpdateCurrentUserRequest user, MultipartFile profileImage);

  User getAuthenticatedUser();
}
