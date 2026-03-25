package com.example.backend.menucategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuCategoryRequest {
    @NotBlank(message = "CATEGORY_NAME_BLANK")
    @Size(min = 3, max = 100, message = "CATEGORY_NAME_INVALID")
    private String categoryName;

    @NotBlank(message = "SLUG_BLANK")
    private String slug;

    private String description;
}