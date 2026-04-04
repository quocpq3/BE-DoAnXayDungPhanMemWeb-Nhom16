package com.example.backend.user.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token; // Đây là thẻ đi qua các API
    boolean authenticated; // Báo true nếu mật khẩu đúng
}
