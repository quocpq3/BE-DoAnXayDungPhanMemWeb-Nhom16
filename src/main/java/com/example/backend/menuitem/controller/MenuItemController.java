package com.example.backend.menuitem.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.menuitem.dto.MenuItemRequest;
import com.example.backend.menuitem.dto.MenuItemResponse;
import com.example.backend.menuitem.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService service;

    @PostMapping
    public ApiResponse<MenuItemResponse> create(@RequestBody @Valid MenuItemRequest request) {
        return ApiResponse.<MenuItemResponse>builder()
                .result(service.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<MenuItemResponse>> getAll() {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .result(service.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuItemResponse> getById(@PathVariable Long id) {
        return ApiResponse.<MenuItemResponse>builder()
                .result(service.findById(id))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<MenuItemResponse>> findByName(@RequestParam String name) {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .result(service.findByName(name))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MenuItemResponse> update(@PathVariable Long id, @RequestBody @Valid MenuItemRequest request) {
        return ApiResponse.<MenuItemResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<String>builder()
                .result("Xóa món ăn thành công")
                .build();
    }
}