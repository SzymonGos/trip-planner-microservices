package com.szymong.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserResponse {

  private Long id;

  private String clerkId;

  private String username;

  private String email;
}
