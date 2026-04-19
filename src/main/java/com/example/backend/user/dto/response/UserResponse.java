package com.example.backend.user.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String name;
    String phone;
    String address;
    String email;
    // Thêm dòng này để trả về danh sách tên quyền  ["ROLE_USER", "ROLE_ADMIN"]
    Set<String> roles;
}
