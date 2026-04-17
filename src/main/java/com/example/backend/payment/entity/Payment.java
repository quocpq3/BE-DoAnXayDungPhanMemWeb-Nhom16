package com.example.backend.payment.entity;

import com.example.backend.orders.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId;

    @OneToOne // Một đơn hàng thường chỉ có một bản ghi thanh toán thành công
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    Order orderDetail;

    String transactionId; // Mã giao dịch từ VNPay/MoMo
    @Column(name="payment_method")
    String paymentMethod; // VNPAY, MOMO, CASH
    BigDecimal amount;
    String status; // SUCCESS, FAILED, PENDING

    @Column(name = "payment_date")
    LocalDateTime paymentDate;
}