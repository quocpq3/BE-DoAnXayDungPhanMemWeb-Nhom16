package com.example.backend.user.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.role.entity.Role; // Giả sử ông có Entity này
import com.example.backend.role.repository.RoleRepository; // Giả sử ông có Repo này
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleRepository roleRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Role>> getAllRoles() {
        return ApiResponse.<List<Role>>builder()
                .result(roleRepository.findAll())
                .build();
    }
}