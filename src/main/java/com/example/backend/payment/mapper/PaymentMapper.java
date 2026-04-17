package com.example.backend.payment.mapper;

import com.example.backend.payment.dto.PaymentResponse;
import com.example.backend.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "orderId", source = "orderDetail.orderId")
    PaymentResponse toPaymentResponse(Payment payment);
}