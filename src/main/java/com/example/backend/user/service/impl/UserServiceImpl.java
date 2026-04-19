package com.example.backend.user.service.impl;

import com.example.backend.user.dto.request.UserCreateRequest;
import com.example.backend.user.dto.request.UserUpdateRequest;
import com.example.backend.user.dto.response.UserResponse;
import com.example.backend.role.entity.Role;
import com.example.backend.user.entity.User;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.user.mapper.UserMapper;
import com.example.backend.role.repository.RoleRepository;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository; // THÊM RoleRepository VÀO ĐÂY
    UserMapper userMapper;
    PasswordEncoder passwordEncoder; //Gọi hàm băm

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        User user = userMapper.toUser(request);

        // mã hóa pass xún db
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        roles.add(userRole);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 1. Map các thông tin cơ bản
        userMapper.updateUser(user, request);

        // 2. Logic cập nhật Role (Nếu có gửi lên)
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findByNameIn(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        userRepository.deleteById(id);
    }
}