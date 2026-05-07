package com.example.backend.orders.service.impl;

import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
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

    @Override
    public OrderResponse create(OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new AppException(ErrorCode.ORDER_DETAILS_REQUIRED);
        }

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderCode(generateOrderCode());

        applyOrderData(order, request, true);

        try {
            Order saved = orderRepository.saveAndFlush(order);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        String currentStatus = normalizeStatusForComparison(order.getOrderStatus());
        if ("PAID".equals(currentStatus) || "COMPLETED".equals(currentStatus)) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new AppException(ErrorCode.ORDER_DETAILS_REQUIRED);
        }

        applyOrderData(order, request, false);

        try {
            Order saved = orderRepository.saveAndFlush(order);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public OrderResponse updateStatus(Long id, String orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        String currentStatus = normalizeStatusForComparison(order.getOrderStatus());
        String newStatus = resolveOrderStatus(orderStatus, order.getOrderStatus());

        // nếu trạng thái mới giống trạng thái hiện tại thì giữ nguyên
        if (currentStatus.equals(newStatus)) {
            return toResponse(order);
        }

        boolean isValidTransition =
                ("PENDING".equals(currentStatus) && ("PAID".equals(newStatus) || "CANCELLED".equals(newStatus)))
                        || ("PAID".equals(currentStatus) && ("COMPLETED".equals(newStatus) || "CANCELLED".equals(newStatus)))
                        || ("COMPLETED".equals(currentStatus) && "COMPLETED".equals(newStatus))
                        || ("CANCELLED".equals(currentStatus) && "CANCELLED".equals(newStatus));

        if (!isValidTransition) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        order.setOrderStatus(newStatus);

        try {
            Order saved = orderRepository.saveAndFlush(order);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
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
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return toResponse(order);
    }

    @Override
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        String currentStatus = normalizeStatusForComparison(order.getOrderStatus());
        if ("PAID".equals(currentStatus) || "COMPLETED".equals(currentStatus)) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }

        try {
            orderRepository.delete(order);
            orderRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }
    }

    private void applyOrderData(Order order, OrderRequest request, boolean isCreate) {
        String customerName = normalizeText(request.getCustomerName());
        if (customerName == null) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        order.setCustomerName(customerName);
        order.setCustomerPhone(normalizeText(request.getCustomerPhone()));
        order.setDeliveryAddress(normalizeText(request.getDeliveryAddress()));
        order.setOrderStatus(resolveOrderStatus(
                request.getOrderStatus(),
                isCreate ? null : order.getOrderStatus()
        ));
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
            throw new AppException(ErrorCode.INVALID_KEY);
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
                    .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));

            Double priceValue = menuItem.getSalePrice() != null
                    ? menuItem.getSalePrice()
                    : menuItem.getBasePrice();

            if (priceValue == null || priceValue < 0) {
                throw new AppException(ErrorCode.PRICE_INVALID);
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

        long sequence = orderRepository.countByOrderCodeStartingWith(prefix) + 1;
        String candidate;

        do {
            candidate = prefix + String.format("%03d", sequence);
            sequence++;
        } while (orderRepository.existsByOrderCode(candidate));

        return candidate;
    }
}