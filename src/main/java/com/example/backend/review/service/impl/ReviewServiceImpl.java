package com.example.backend.review.service.impl;

import com.example.backend.common.UploadService;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.orders.repository.OrderRepository;
import com.example.backend.review.dto.ReviewCreateRequest;
import com.example.backend.review.dto.ReviewResponse;
import com.example.backend.review.dto.AdminReplyRequest;
import com.example.backend.review.dto.ReviewUpdateRequest;
import com.example.backend.review.entity.Review;
import com.example.backend.review.mapper.ReviewMapper;
import com.example.backend.review.repository.ReviewRepository;
import com.example.backend.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;
    private final UploadService uploadService;

    @Override
    @Transactional // Thêm cái này nhé
    public ReviewResponse createReview(ReviewCreateRequest request, MultipartFile image) {
        // 1. Kiểm tra đã mua hàng chưa (đang để tạm ở ReviewRepo)
        boolean hasPurchased = reviewRepository.existsByPurchased(request.getCustomerPhone(), request.getMenuItemId());

        if (!hasPurchased) {
            throw new AppException(ErrorCode.NOT_PURCHASED);
        }

        // 2. Map sang Entity
        Review review = reviewMapper.toReview(request);

        // 3. Gán User an toàn hơn
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            review.setUserId(auth.getName());
        }

        // 4. Lưu ảnh
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadService.uploadImage(image);
            review.setImageUrl(imageUrl);
        }

        Review savedReview = reviewRepository.save(review);
        log.info("Khách hàng {} đã đánh giá món ăn {}", request.getCustomerPhone(), request.getMenuItemId());

        return reviewMapper.toReviewResponse(savedReview);
    }


    @Override
    @Transactional
    public ReviewResponse replyReview(Long reviewId, AdminReplyRequest request) {
        // 1. Tìm đánh giá khách hàng theo ID
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        // 2. Cập nhật nội dung trả lời của Admin
        review.setAdminReply(request.getAdminReply());
        review.setReplied_at(LocalDateTime.now());

        // 3. Lưu và trả về kết quả
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request, MultipartFile image) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        // Dùng mapper để update các trường rating, comment từ request vào review
        reviewMapper.updateReviewFromRequest(request, review);

        if (image != null && !image.isEmpty()) {
            review.setImageUrl(uploadService.uploadImage(image));
        }

        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }
}