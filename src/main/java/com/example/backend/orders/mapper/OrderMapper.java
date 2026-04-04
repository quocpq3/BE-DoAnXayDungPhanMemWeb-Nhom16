package com.example.backend.orders.mapper;

import com.example.backend.orders.dto.*;
import com.example.backend.orders.entity.Order;
import com.example.backend.orders.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toOrder(OrderRequest request);

    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "lineTotal", ignore = true)
    OrderItem toOrderItem(OrderItemRequest request);

    OrderItemResponse toOrderItemResponse(OrderItem item);

    List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> items);

    OrderResponse toOrderResponse(Order order);
}