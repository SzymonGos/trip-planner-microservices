package com.szymong.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest {

  private String username;

  private String clerkId;

  private String email;
}
