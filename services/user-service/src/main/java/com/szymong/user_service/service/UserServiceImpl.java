package com.szymong.user_service.service;


import com.szymong.common.exceptions.ResourceNotFoundException;
import com.szymong.user_service.client.MediaClient;
import com.szymong.user_service.dto.UserResponse;
import com.szymong.user_service.entity.User;
import com.szymong.user_service.dto.*;
import com.szymong.user_service.event.MediaImageDeletionRequestedEvent;
import com.szymong.user_service.mapper.UserMapper;
import com.szymong.user_service.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final ApplicationEventPublisher eventPublisher;
  private final MediaClient mediaClient;

  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ApplicationEventPublisher eventPublisher, MediaClient mediaClient) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.eventPublisher = eventPublisher;
    this.mediaClient = mediaClient;
  }

  public UserResponse getUserById(Long id) {
    Optional<User> result = userRepository.findById(id);

    if (result.isEmpty()) {
      throw new ResourceNotFoundException("User not found with id: " + id);
    }

    return userMapper.mapToResponse(result.get());
  }
  @Override
  public UserResponse getUserByUsername(String username) {
    Optional<User> result = userRepository.findByUsername(username);

    if (result.isEmpty()) {
      throw new ResourceNotFoundException(
              "User not found with username: " + username
      );
    }

    return userMapper.mapToResponse(result.get());
  }

  @Override
  public CurrentUserResponse getCurrentUser() {
    User user = getAuthenticatedUser();

    return new CurrentUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImagePublicId());
  }


  public User getUserByClerkId(String clerkId) {
    Optional<User> result = userRepository.findByClerkId(clerkId);
    if (result.isPresent()) {
      return result.get();
    } else {
      throw new ResourceNotFoundException("User not found with clerkId: " + clerkId);
    }
  }

  @Override
  public CreateUserResponse createUser(CreateUserRequest request) {
    Optional<User> existingUser = userRepository.findByClerkId(request.getClerkId());

    if (existingUser.isPresent()) {
      return userMapper.mapToCreateUserResponse(existingUser.get());
    }

    User newUser = new User();

    newUser.setClerkId(request.getClerkId());
    newUser.setUsername(request.getUsername());
    newUser.setEmail(request.getEmail());


    return userMapper.mapToCreateUserResponse(userRepository.save(newUser));
  }

  @Override
  @Transactional
  public UpdateCurrentUserResponse updateUser(UpdateCurrentUserRequest request, MultipartFile profileImage) {
    User user = getAuthenticatedUser();

    user.setUsername(request.getUsername());

    String oldProfileImagePublicId = null;

    if (profileImage != null && !profileImage.isEmpty()) {
      oldProfileImagePublicId = user.getProfileImagePublicId();

      String newProfileImagePublicId = mediaClient.uploadProfileImage(profileImage);

      user.setProfileImagePublicId(newProfileImagePublicId);
    }

    User updatedUser = userRepository.save(user);

    if (oldProfileImagePublicId != null && !oldProfileImagePublicId.isBlank()) {
      eventPublisher.publishEvent(new MediaImageDeletionRequestedEvent(List.of(oldProfileImagePublicId)));
    }

    return userMapper.mapToUpdateCurrentUserResponse(updatedUser);
  }


  private User findUserById(Long id) {
    return userRepository.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
  }

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder
                                            .getContext()
                                            .getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalStateException("Authenticated user is required");
    }

    String clerkId = authentication.getName();

    return getUserByClerkId(clerkId);
  }
}
