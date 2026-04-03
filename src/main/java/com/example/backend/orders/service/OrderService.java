package com.example.backend.orders.service;

import com.example.backend.orders.dto.OrderRequest;
import com.example.backend.orders.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    List<OrderResponse> findAll();
    OrderResponse findById(Long id);
    void delete(Long id);
}