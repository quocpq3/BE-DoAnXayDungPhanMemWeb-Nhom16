package com.example.backend.payment.repository;

import com.example.backend.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Có thể thêm hàm tìm kiếm giao dịch theo orderId nếu cần
    Optional<Payment> findByOrderDetail_OrderId(Long orderId);
}