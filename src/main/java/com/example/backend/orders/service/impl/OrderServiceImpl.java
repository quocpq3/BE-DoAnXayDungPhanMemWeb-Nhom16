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
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Set<String> ALLOWED_ORDER_STATUSES =
            Set.of("PENDING", "PAID", "COMPLETED", "CANCELLED");

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        Order order = new Order();
        order.setOrderCode(generateOrderCode());
        order.setCreatedAt(LocalDateTime.now());

        applyOrderData(order, request, true);

        Order saved = orderRepository.saveAndFlush(order);
        return toResponse(saved);
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id = " + id));

        String currentStatus = normalizeStatusForComparison(order.getOrderStatus());
        if ("PAID".equals(currentStatus) || "COMPLETED".equals(currentStatus)) {
            throw new IllegalStateException("Không thể sửa toàn bộ đơn hàng đã thanh toán hoặc đã hoàn thành");
        }

        applyOrderData(order, request, false);

        Order saved = orderRepository.saveAndFlush(order);
        return toResponse(saved);
    }

    @Override
    public OrderResponse updateStatus(Long id, String orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id = " + id));

        order.setOrderStatus(resolveOrderStatus(orderStatus, order.getOrderStatus()));
        Order saved = orderRepository.saveAndFlush(order);
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
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id = " + id));

        String currentStatus = normalizeStatusForComparison(order.getOrderStatus());
        if ("PAID".equals(currentStatus) || "COMPLETED".equals(currentStatus)) {
            throw new IllegalStateException("Không thể xóa đơn hàng đã thanh toán hoặc đã hoàn thành");
        }

        try {
            orderRepository.delete(order);
            orderRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Không thể xóa đơn hàng vì đang được liên kết với dữ liệu khác");
        }
    }

    private void applyOrderData(Order order, OrderRequest request, boolean isCreate) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy user với id = " + request.getUserId()
                ));

        order.setUser(user);

        // Giữ tương thích schema DB cũ
        order.setCustomerName(user.getName());
        order.setCustomerPhone(user.getPhone());

        String incomingAddress = normalizeText(request.getDeliveryAddress());
        if (incomingAddress != null) {
            order.setDeliveryAddress(incomingAddress);
        } else if (isCreate) {
            order.setDeliveryAddress(user.getAddress());
        } else if (order.getDeliveryAddress() == null || order.getDeliveryAddress().isBlank()) {
            order.setDeliveryAddress(user.getAddress());
        }

        order.setOrderStatus(resolveOrderStatus(request.getOrderStatus(), isCreate ? null : order.getOrderStatus()));
        order.setPaymentMethod(resolveTextOrDefault(
                request.getPaymentMethod(),
                isCreate ? null : order.getPaymentMethod(),
                "CASH"
        ));
        order.setDeliveryMethod(resolveTextOrDefault(
                request.getDeliveryMethod(),
                isCreate ? null : order.getDeliveryMethod(),
                "PICKUP"
        ));

        if (request.getNote() != null) {
            order.setNote(request.getNote().trim());
        }

        List<OrderItem> orderItems = buildOrderItems(order, request.getItems());
        BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.getItems().clear();
        order.getItems().addAll(orderItems);
        order.setTotalAmount(totalAmount);
    }

    private String resolveOrderStatus(String incomingStatus, String currentStatus) {
        if (incomingStatus == null || incomingStatus.isBlank()) {
            if (currentStatus == null || currentStatus.isBlank()) {
                return "PENDING";
            }
            return currentStatus.trim().toUpperCase();
        }

        String normalized = incomingStatus.trim().toUpperCase();
        if (!ALLOWED_ORDER_STATUSES.contains(normalized)) {
            throw new IllegalArgumentException(
                    "orderStatus không hợp lệ. Chỉ chấp nhận: PENDING, PAID, COMPLETED, CANCELLED"
            );
        }
        return normalized;
    }

    private String resolveTextOrDefault(String incoming, String current, String defaultValue) {
        if (incoming != null && !incoming.isBlank()) {
            return incoming.trim().toUpperCase();
        }
        if (current != null && !current.isBlank()) {
            return current.trim().toUpperCase();
        }
        return defaultValue;
    }

    private String normalizeText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String normalizeStatusForComparison(String status) {
        if (status == null) {
            return "";
        }
        return status.trim().toUpperCase();
    }

    private List<OrderItem> buildOrderItems(Order order, List<OrderItemRequest> itemRequests) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : itemRequests) {
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
        }

        return orderItems;
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
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userName(order.getUser() != null ? order.getUser().getName() : order.getCustomerName())
                .userPhone(order.getUser() != null ? order.getUser().getPhone() : order.getCustomerPhone())
                .userAddress(order.getUser() != null ? order.getUser().getAddress() : null)
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

        long sequence = orderRepository.countByOrderCodeStartingWith(prefix) + 1;
        String candidate;

        do {
            candidate = prefix + String.format("%03d", sequence);
            sequence++;
        } while (orderRepository.existsByOrderCode(candidate));

        return candidate;
    }
}