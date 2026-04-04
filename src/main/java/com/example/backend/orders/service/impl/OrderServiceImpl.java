package com.example.backend.orders.service.impl;

import com.example.backend.orders.dto.OrderItemRequest;
import com.example.backend.orders.dto.OrderRequest;
import com.example.backend.orders.dto.OrderResponse;
import com.example.backend.orders.entity.Order;
import com.example.backend.orders.entity.OrderItem;
import com.example.backend.orders.mapper.OrderMapper;
import com.example.backend.orders.repository.OrderRepository;
import com.example.backend.orders.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse create(OrderRequest request) {

        validateOrderRequest(request);

        Order order = orderMapper.toOrder(request);

        order.setOrderCode(generateOrderCode());

        buildOrderItems(order, request.getItems());

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ORDER_NOT_FOUND"));

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void delete(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ORDER_NOT_FOUND"));

        orderRepository.delete(order);
    }

    private void validateOrderRequest(OrderRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("ORDER_ITEMS_REQUIRED");
        }

    }

    private void buildOrderItems(Order order, List<OrderItemRequest> itemRequests) {

        List<OrderItem> items = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : itemRequests) {

            OrderItem item = orderMapper.toOrderItem(itemRequest);

            item.setOrder(order);

            BigDecimal lineTotal = itemRequest.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            item.setLineTotal(lineTotal);

            totalAmount = totalAmount.add(lineTotal);

            items.add(item);
        }

        order.setItems(items);

        order.setTotalAmount(totalAmount);
    }

    private String generateOrderCode() {

        String prefix = "ORD";

        String timePart = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int randomPart = ThreadLocalRandom.current().nextInt(100, 1000);

        String orderCode = prefix + timePart + randomPart;

        while (orderRepository.existsByOrderCode(orderCode)) {

            randomPart = ThreadLocalRandom.current().nextInt(100, 1000);

            orderCode = prefix + timePart + randomPart;
        }

        return orderCode;
    }

}