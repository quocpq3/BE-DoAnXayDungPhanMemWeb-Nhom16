package com.example.backend.review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String userId; // Lấy từ SecurityContext (username)
    private String customerPhone;
    private Long menuItemId;

    private int rating;
    private String comment;
    private String imageUrl; // Lưu link ảnh (ví dụ: Cloudinary hoặc đường dẫn thư mục)

    private String adminReply;
    private LocalDateTime replied_at;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}