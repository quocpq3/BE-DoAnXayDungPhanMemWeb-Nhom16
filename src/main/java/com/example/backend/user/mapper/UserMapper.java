package com.example.backend.user.mapper;

import com.example.backend.role.entity.Role;
import com.example.backend.user.dto.request.UserCreateRequest;
import com.example.backend.user.dto.request.UserUpdateRequest;
import com.example.backend.user.dto.response.UserResponse;
import com.example.backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true) // Ignore roles khi tạo mới để set thủ công
    User toUser(UserCreateRequest request);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    // Hàm phụ trợ để chuyển đổi Set<Role> sang Set<String>
    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> "ROLE_" + role.getName()) // Thêm "ROLE_" vào trước tên quyền
                .collect(Collectors.toSet());
    }
}