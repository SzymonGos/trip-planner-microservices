package com.szymong.user_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
  private Long id;

  private String username;

  private String profileImagePublicId;

  private LocalDateTime createdAt;

}
