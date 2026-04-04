package com.example.backend.review.mapper;

import com.example.backend.review.dto.ReviewCreateRequest;
import com.example.backend.review.dto.ReviewResponse;
import com.example.backend.review.dto.ReviewUpdateRequest;
import com.example.backend.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "adminReply", ignore = true)
    @Mapping(target = "replied_at", ignore = true)
    Review toReview(ReviewCreateRequest request);

    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "menuItemId", ignore = true)
    @Mapping(target = "customerPhone", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateReviewFromRequest(ReviewUpdateRequest request, @MappingTarget Review review);
}