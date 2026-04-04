package com.example.backend.review.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.review.dto.ReviewCreateRequest;
import com.example.backend.review.dto.ReviewResponse;
import com.example.backend.review.dto.AdminReplyRequest;
import com.example.backend.review.dto.ReviewUpdateRequest;
import com.example.backend.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 1. Tạo đánh giá (Dùng Multipart để up ảnh)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ReviewResponse> create(
            @RequestPart("data") @Valid ReviewCreateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.createReview(request, image))
                .build();
    }

    // 2. Cập nhật đánh giá (Tách riêng Create/Update như Thịnh muốn)
    @PutMapping(value = "/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ReviewResponse> update(
            @PathVariable Long id,
            @RequestPart("data") @Valid ReviewUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.updateReview(id, request, image))
                .build();
    }

    // 3. Admin phản hồi đánh giá
    @PutMapping("/{id}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ReviewResponse> reply(
            @PathVariable Long id,
            @RequestBody @Valid AdminReplyRequest request) {

        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.replyReview(id, request))
                .build();
    }
}