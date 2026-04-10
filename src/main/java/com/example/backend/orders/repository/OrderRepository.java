package com.example.backend.orders.repository;

import com.example.backend.orders.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @EntityGraph(attributePaths = {"items", "items.menuItem"})
    List<Order> findAll();

    @Override
    @EntityGraph(attributePaths = {"items", "items.menuItem"})
    Optional<Order> findById(Long id);

    long countByOrderCodeStartingWith(String prefix);
}