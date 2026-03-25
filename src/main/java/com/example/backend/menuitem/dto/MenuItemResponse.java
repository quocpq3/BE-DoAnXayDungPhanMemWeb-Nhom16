package com.example.backend.menuitem.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItemResponse {
    private Long itemId;
    private String itemName;
    private String slug;
    private String description;
    private String imageUrl;
    private Double basePrice;
    private Integer discountPercent;
    private Double salePrice;
    private Boolean isAvailable;
    private Boolean isCombo;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
}