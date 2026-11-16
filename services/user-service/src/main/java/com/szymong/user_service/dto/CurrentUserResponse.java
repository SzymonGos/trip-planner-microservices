package com.szymong.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentUserResponse {
  private Long id;

  private String username;

  private String email;

  private String profileImagePublicId;
}
