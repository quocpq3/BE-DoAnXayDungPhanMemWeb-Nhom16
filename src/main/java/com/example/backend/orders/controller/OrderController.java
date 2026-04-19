package com.example.backend.orders.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.orders.dto.OrderRequest;
import com.example.backend.orders.dto.OrderResponse;
import com.example.backend.orders.dto.OrderStatusRequest;
import com.example.backend.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ApiResponse<OrderResponse> create(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderResponse> update(@PathVariable Long id,
                                             @RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateStatus(@PathVariable Long id,
                                                   @RequestBody @Valid OrderStatusRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(service.updateStatus(id, request.getOrderStatus()))
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAll() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(service.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getById(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(service.findById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<String>builder()
                .result("Đơn hàng đã được xóa thành công")
                .build();
    }
}