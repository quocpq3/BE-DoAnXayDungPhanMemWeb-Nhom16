package com.example.backend.review.repository;

import com.example.backend.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMenuItemId(Long menuItemId);

    // Tính trung bình sao của món ăn
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.menuItemId = :itemId")
    Double getAverageRating(Long itemId);

    // Kiểm tra xem SĐT này đã có đơn hàng nào chứa món này và đã thanh toán chưa
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.details d " +
            "WHERE o.customerPhone = :phone AND d.menuItemId = :itemId AND o.status = 'PAID'")
    boolean existsByPurchased(@Param("phone") String phone, @Param("itemId") Long itemId);
}