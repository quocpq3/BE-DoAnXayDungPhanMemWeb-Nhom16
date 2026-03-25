package com.example.backend.user.service;

import com.example.backend.user.dto.request.UserCreateRequest;
import com.example.backend.user.dto.request.UserUpdateRequest;
import com.example.backend.user.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
}
