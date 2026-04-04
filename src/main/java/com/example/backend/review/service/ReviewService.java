package com.example.backend.review.service;

import com.example.backend.review.dto.ReviewCreateRequest;
import com.example.backend.review.dto.ReviewResponse;
import com.example.backend.review.dto.AdminReplyRequest;
import com.example.backend.review.dto.ReviewUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
    ReviewResponse createReview(ReviewCreateRequest request, MultipartFile image);
    ReviewResponse replyReview(Long reviewId, AdminReplyRequest request);
    ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request, MultipartFile image);
}