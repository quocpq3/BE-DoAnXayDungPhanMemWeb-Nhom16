package com.example.backend.review.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private String userId;
    private String customerPhone;
    private Long menuItemId;
    private int rating;
    private String comment;
    private String imageUrl;
    private String adminReply;
    private LocalDateTime replied_at;
    private LocalDateTime createdAt;
}