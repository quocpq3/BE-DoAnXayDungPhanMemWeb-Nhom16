package com.example.backend.orders.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name="order_code", nullable = false, unique = true, length = 50)
    private String orderCode;

    @Column
    private Long userId;

    @Column(name= "customer_name",nullable = false, length = 100)
    private String customerName;

    @Column(name= "customer_phone",length = 20)
    private String customerPhone;

    @Column(name= "delivery_address",length = 255)
    private String deliveryAddress;

    @Column(name= "order_status",nullable = false, length = 30)
    private String orderStatus;

    @Column(name="payment_method",nullable = false, length = 20)
    private String paymentMethod;

    @Column(name="delivery_method",nullable = false, length = 20)
    private String deliveryMethod;

    @Column(name="total_amount",nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @PrePersist
    protected void onCreate() {
        if (this.orderStatus == null || this.orderStatus.isBlank()) {
            this.orderStatus = "PENDING";
        }

        if (this.paymentMethod == null || this.paymentMethod.isBlank()) {
            this.paymentMethod = "CASH";
        }

        if (this.deliveryMethod == null || this.deliveryMethod.isBlank()) {
            this.deliveryMethod = "PICKUP";
        }

        if (this.totalAmount == null) {
            this.totalAmount = BigDecimal.ZERO;
        }

        this.createdAt = LocalDateTime.now();
    }
}