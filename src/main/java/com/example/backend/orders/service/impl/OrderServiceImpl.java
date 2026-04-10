package com.example.backend.orders.service.impl;

import com.example.backend.menuitem.entity.MenuItem;
import com.example.backend.menuitem.repository.MenuItemRepository;
import com.example.backend.orders.dto.OrderItemRequest;
import com.example.backend.orders.dto.OrderItemResponse;
import com.example.backend.orders.dto.OrderRequest;
import com.example.backend.orders.dto.OrderResponse;
import com.example.backend.orders.entity.Order;
import com.example.backend.orders.entity.OrderItem;
import com.example.backend.orders.repository.OrderRepository;
import com.example.backend.orders.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        Order order = new Order();
        order.setOrderCode(generateOrderCode());
        order.setUserId(request.getUserId());
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setOrderStatus("PENDING");
        order.setPaymentMethod(
                request.getPaymentMethod() != null && !request.getPaymentMethod().isBlank()
                        ? request.getPaymentMethod()
                        : "CASH"
        );
        order.setDeliveryMethod(
                request.getDeliveryMethod() != null && !request.getDeliveryMethod().isBlank()
                        ? request.getDeliveryMethod()
                        : "PICKUP"
        );
        order.setNote(request.getNote());
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Không tìm thấy món với id = " + itemRequest.getItemId()
                    ));

            Double priceValue = menuItem.getSalePrice() != null
                    ? menuItem.getSalePrice()
                    : menuItem.getBasePrice();

            if (priceValue == null) {
                throw new IllegalArgumentException("Món " + menuItem.getItemName() + " chưa có giá");
            }

            BigDecimal unitPrice = BigDecimal.valueOf(priceValue);
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .build();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(lineTotal);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id = " + id));
        return toResponse(order);
    }

    @Override
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy đơn hàng với id = " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .orderItemId(item.getOrderItemId())
                        .itemId(item.getMenuItem() != null ? item.getMenuItem().getItemId() : null)
                        .itemName(item.getMenuItem() != null ? item.getMenuItem().getItemName() : null)
                        .imageUrl(item.getMenuItem() != null ? item.getMenuItem().getImageUrl() : null)
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .lineTotal(item.getLineTotal())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .userId(order.getUserId())
                .customerName(order.getCustomerName())
                .customerPhone(order.getCustomerPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .deliveryMethod(order.getDeliveryMethod())
                .totalAmount(order.getTotalAmount())
                .note(order.getNote())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }

    private String generateOrderCode() {
        LocalDate today = LocalDate.now();
        String prefix = "ORD"
                + today.getYear()
                + String.format("%02d", today.getMonthValue())
                + String.format("%02d", today.getDayOfMonth());

        long count = orderRepository.countByOrderCodeStartingWith(prefix);
        return prefix + String.format("%03d", count + 1);
    }
}