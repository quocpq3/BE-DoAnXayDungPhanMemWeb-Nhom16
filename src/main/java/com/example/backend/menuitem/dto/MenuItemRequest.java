package com.example.backend.menuitem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItemRequest {
    @NotBlank(message = "ITEM_NAME_BLANK")
    private String itemName;

    @NotBlank(message = "SLUG_BLANK")
    private String slug;

    @NotNull(message = "CATEGORY_ID_NULL")
    private Long categoryId;

    private String description;
    private String imageUrl;

    @NotNull(message = "BASE_PRICE_NULL")
    @PositiveOrZero(message = "PRICE_INVALID")
    private Double basePrice;

    @Min(value = 0, message = "DISCOUNT_INVALID")
    @Max(value = 100, message = "DISCOUNT_INVALID")
    private Integer discountPercent;

    private Boolean isAvailable;
    private Boolean isCombo;
}