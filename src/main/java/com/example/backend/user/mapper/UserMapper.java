package com.example.backend.user.mapper;

import com.example.backend.user.dto.request.UserCreateRequest;
import com.example.backend.user.dto.request.UserUpdateRequest;
import com.example.backend.user.dto.response.UserResponse;
import com.example.backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}