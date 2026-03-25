package com.example.backend.combodetail.controller;

import com.example.backend.combodetail.dto.ComboDetailRequest;
import com.example.backend.combodetail.dto.ComboDetailResponse;
import com.example.backend.combodetail.service.ComboDetailService;
import com.example.backend.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combo-details")
@RequiredArgsConstructor
public class ComboDetailController {
    private final ComboDetailService service;

    @PostMapping
    public ApiResponse<ComboDetailResponse> create(@RequestBody @Valid ComboDetailRequest request) {
        return ApiResponse.<ComboDetailResponse>builder().result(service.create(request)).build();
    }

    @GetMapping
    public ApiResponse<List<ComboDetailResponse>> getAll() {
        return ApiResponse.<List<ComboDetailResponse>>builder().result(service.findAll()).build();
    }

    @PutMapping
    public ApiResponse<ComboDetailResponse> update(@RequestBody @Valid ComboDetailRequest request) {
        return ApiResponse.<ComboDetailResponse>builder().result(service.update(request)).build();
    }

    @DeleteMapping("/{comboId}/{componentId}")
    public ApiResponse<String> delete(@PathVariable Long comboId, @PathVariable Long componentId) {
        service.delete(comboId, componentId);
        return ApiResponse.<String>builder().result("Xóa thành công").build();
    }

    @GetMapping("/combo/{comboId}")
    public ApiResponse<List<ComboDetailResponse>> getByCombo(@PathVariable Long comboId) {
        return ApiResponse.<List<ComboDetailResponse>>builder().result(service.findByComboId(comboId)).build();
    }
}
