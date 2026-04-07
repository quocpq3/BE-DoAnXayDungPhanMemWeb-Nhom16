package com.example.backend.orders.repository;

import com.example.backend.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByOrderCode(String orderCode);

    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.items i " +
            "WHERE o.customerPhone = :phone " +
            "AND i.itemId = :itemId " +
            "AND o.orderStatus = 'PAID'")
    boolean existsByPurchased(@Param("phone") String phone, @Param("itemId") Long itemId);

}