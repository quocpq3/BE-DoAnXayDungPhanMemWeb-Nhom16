package com.example.backend.orders.service.impl;

import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.menuitem.entity.MenuItem;
import com.example.backend.menuitem.repository.MenuItemRepository;
<<<<<<< HEAD
import com.example.backend.orders.dto.OrderItemRequest;
=======
import com.example.backend.orders.dto.OrderDetailRequest;
import com.example.backend.orders.dto.OrderResponse;
>>>>>>> origin
import com.example.backend.orders.dto.OrderRequest;
import com.example.backend.orders.dto.OrderResponse;
import com.example.backend.orders.entity.Order;
import com.example.backend.orders.entity.OrderItem;
import com.example.backend.orders.mapper.OrderMapper;
import com.example.backend.orders.repository.OrderRepository;
import com.example.backend.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD
=======
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> origin
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MenuItemRepository menuItemRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        validateOrderRequest(request);

        Order order = orderMapper.toOrder(request);
        order.setOrderCode(generateOrderCode());

<<<<<<< HEAD
        buildOrderItems(order, request.getItems());
=======
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            // Ở đây nếu DB bảng Order có cột user_id, bạn có thể set vào:
            // order.setUserId(auth.getName()); // hoặc tìm user theo username
            log.info("User {} đang đặt hàng", auth.getName());
        } else {
            log.info("Khách vãng lai đang đặt hàng");
        }

        // 3. Xây dựng chi tiết đơn hàng (Lấy giá từ DB)
        List<OrderDetail> details = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderDetailRequest itemReq : request.getDetails()) {
            // Lấy thông tin MenuItem từ Repository của bạn
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));

            // Lấy giá bán thực tế trong DB
            BigDecimal priceInDb = BigDecimal.valueOf(menuItem.getSalePrice());

            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .menuItemId(menuItem.getItemId())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(priceInDb) // Gán giá chuẩn từ DB
                    .build();

            // Tính tiền từng món
            BigDecimal lineTotal = priceInDb.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            detail.setLineTotal(lineTotal);

            totalAmount = totalAmount.add(lineTotal);
            details.add(detail);
        }

        order.setDetails(details);
        order.setTotalAmount(totalAmount);

        buildOrderDetails(order, request.getDetails());
>>>>>>> origin

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
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

        for (OrderItemRequest itemReq : itemRequests) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getItemId())
                    .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));

            BigDecimal priceInDb = BigDecimal.valueOf(menuItem.getSalePrice());

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .itemId(menuItem.getItemId())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(priceInDb)
                    .build();

            BigDecimal lineTotal = priceInDb.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
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