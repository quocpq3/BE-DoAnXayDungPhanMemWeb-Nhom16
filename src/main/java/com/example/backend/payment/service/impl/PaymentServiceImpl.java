package com.example.backend.payment.service.impl;

import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.orders.entity.Order;
import com.example.backend.orders.repository.OrderRepository;
import com.example.backend.payment.dto.PaymentRequest;
import com.example.backend.payment.dto.PaymentResponse;
import com.example.backend.payment.entity.Payment;
import com.example.backend.payment.mapper.PaymentMapper;
import com.example.backend.payment.repository.PaymentRepository;
import com.example.backend.payment.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;
    OrderRepository orderDetailRepository;
    PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse completePayment(PaymentRequest request) {
        // 1. Tìm đơn hàng cần thanh toán
        Order order = orderDetailRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // 2. Kiểm tra nếu đơn đã thanh toán (PAID) hoặc đã hủy thì không cho làm tiếp
        if ("PAID".equals(order.getOrderStatus())) {
            throw new AppException(ErrorCode.ALREADY_PAID);
        }

        if ("CANCELLED".equals(order.getOrderStatus())) {
            throw new RuntimeException("Đơn hàng này đã bị hủy, không thể thanh toán!");
        }

        // 3. Chuẩn hóa phương thức thanh toán (MOMO hoặc CASH)
        String paymentMethod = request.getPaymentMethod().toUpperCase();
        String txId = request.getTransactionId();

        // 4. Xử lý mã giao dịch (Transaction ID)
        if ("MOMO".equals(paymentMethod)) {
            // Nếu là MOMO mà Admin không nhập mã GD, mình tự sinh mã dựa trên Order Code
            if (txId == null || txId.trim().isEmpty()) {
                txId = "MM_" + order.getOrderCode() + "_" + System.currentTimeMillis();
            }
        } else if ("CASH".equals(paymentMethod)) {
            // Nếu là Tiền mặt
            txId = "CASH_" + order.getOrderCode();
        } else {
            throw new RuntimeException("Phương thức thanh toán không hợp lệ!");
        }

        // 5. Cập nhật trạng thái Đơn hàng sang PAID
        order.setOrderStatus("PAID");
        orderDetailRepository.save(order);

        // 6. Lưu vào lịch sử bảng Payment
        Payment payment = Payment.builder()
                .orderDetail(order)
                .transactionId(txId)
                .paymentMethod(paymentMethod)
                .amount(order.getTotalAmount()) // Lấy đúng số tiền trong hóa đơn
                .status("SUCCESS")
                .paymentDate(LocalDateTime.now())
                .build();

        // Trả về Response cho Frontend/Postman
        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }
}