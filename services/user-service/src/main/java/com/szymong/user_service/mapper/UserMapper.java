package com.szymong.user_service.mapper;

import com.szymong.user_service.dto.CreateUserResponse;
import com.szymong.user_service.dto.UpdateCurrentUserResponse;
import com.szymong.user_service.dto.UserResponse;
import com.szymong.user_service.entity.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse mapToResponse(User user);

  CreateUserResponse mapToCreateUserResponse(User user);

  UpdateCurrentUserResponse mapToUpdateCurrentUserResponse(User user);
}
