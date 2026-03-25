package com.example.backend.menucategory.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuCategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String slug;
    private String description;
    private LocalDateTime createdAt;
}