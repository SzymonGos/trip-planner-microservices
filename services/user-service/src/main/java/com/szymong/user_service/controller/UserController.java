package com.szymong.user_service.controller;

import com.szymong.user_service.dto.*;
import com.szymong.user_service.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public UserResponse getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @GetMapping("/me")
  public CurrentUserResponse getCurrentUser() {
    return userService.getCurrentUser();
  }

  @GetMapping("/username/{username}")
  public UserResponse getUserByUsername(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  @PostMapping
  public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
    return userService.createUser(request);
  }

  @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public UpdateCurrentUserResponse updateUser(@RequestPart("request") UpdateCurrentUserRequest request, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
    return userService.updateUser(request, profileImage);
  }
}