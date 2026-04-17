package com.example.backend.orders.repository;

import com.example.backend.orders.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByOrderCode(String orderCode);
    @Query("""
SELECT COUNT(o) > 0 
FROM Order o 
JOIN o.items i 
WHERE o.customerPhone = :phone 
AND i.menuItem.itemId = :itemId 
AND o.orderStatus = 'PAID'
""")
    boolean existsByPurchased(String phone, Long itemId);
    @Override
    @EntityGraph(attributePaths = {"items", "items.menuItem"})
    List<Order> findAll();

    @Override
    @EntityGraph(attributePaths = {"items", "items.menuItem"})
    Optional<Order> findById(Long id);

    long countByOrderCodeStartingWith(String prefix);
}
//a