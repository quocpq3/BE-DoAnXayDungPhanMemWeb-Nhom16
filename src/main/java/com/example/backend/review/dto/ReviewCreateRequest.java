package com.example.backend.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateRequest {
    @NotBlank(message = "CUSTOMER_PHONE_REQUIRED")
    private String customerPhone;

    @NotNull(message = "MENU_ITEM_ID_REQUIRED")
    private Long menuItemId;

    @Min(1) @Max(5)
    private int rating;

    @NotBlank(message = "REVIEW_COMMENT_REQUIRED")
    private String comment;
}