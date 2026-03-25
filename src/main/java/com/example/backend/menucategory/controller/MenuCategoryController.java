package com.example.backend.menucategory.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.menucategory.dto.MenuCategoryRequest;
import com.example.backend.menucategory.dto.MenuCategoryResponse;
import com.example.backend.menucategory.service.MenuCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService service;

    @PostMapping
    public ApiResponse<MenuCategoryResponse> create(@RequestBody @Valid MenuCategoryRequest request) {
        return ApiResponse.<MenuCategoryResponse>builder()
                .result(service.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<MenuCategoryResponse>> getAll() {
        return ApiResponse.<List<MenuCategoryResponse>>builder()
                .result(service.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuCategoryResponse> getById(@PathVariable Long id) {
        return ApiResponse.<MenuCategoryResponse>builder()
                .result(service.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MenuCategoryResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuCategoryRequest request) {
        return ApiResponse.<MenuCategoryResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<String>builder()
                .result("Danh mục đã được xóa thành công")
                .build();
    }
}