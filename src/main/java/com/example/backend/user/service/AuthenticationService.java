package com.example.backend.user.service;

import com.example.backend.user.dto.request.AuthenticationRequest;
import com.example.backend.user.dto.response.AuthenticationResponse;
import com.example.backend.user.entity.User;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.user.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    // Bạn hãy copy nguyên chuỗi dưới đây (đã đủ 64 ký tự)
    private static final String SIGNER_KEY = "1234567890qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasdfghjklzxcvbnm";


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findByName(request.getName())
                .orElseThrow(() -> {
                    return new AppException(ErrorCode.USER_NOT_EXISTED);
                });

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {

            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        var token = generateToken(user); // Đúng pass thì tạo thẻ bài

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // Hàm tạo JWT Token
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Tạo thông tin thẻ bài (hạn dùng 1 tiếng)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getName())
                .issuer("com.example.backend")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user)) // Gắn quyền vào thẻ bài
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Cannot create token", e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                // Đảm bảo có ROLE_ ở đây
                stringJoiner.add("ROLE_" + role.getName());
            });
        }
        return stringJoiner.toString();
    }
}