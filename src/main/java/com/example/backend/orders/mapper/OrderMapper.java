package com.example.backend.orders.mapper;

import com.example.backend.orders.dto.OrderDetailRequest;
import com.example.backend.orders.dto.OrderDetailResponse;
import com.example.backend.orders.dto.OrderRequest;
import com.example.backend.orders.dto.OrderResponse;
import com.example.backend.orders.entity.Order;
import com.example.backend.orders.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "details", ignore = true)
    Order toOrder(OrderRequest request);

    @Mapping(target = "orderDetailId", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "lineTotal", ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderDetail detail);

    List<OrderDetailResponse> toOrderDetailResponseList(List<OrderDetail> details);

    OrderResponse toOrderResponse(Order order);
}