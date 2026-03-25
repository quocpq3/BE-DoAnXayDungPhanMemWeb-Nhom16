package com.example.backend.menucategory.mapper;

import com.example.backend.menucategory.dto.MenuCategoryRequest;
import com.example.backend.menucategory.dto.MenuCategoryResponse;
import com.example.backend.menucategory.entity.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {
    MenuCategory toMenuCategory(MenuCategoryRequest request);

    MenuCategoryResponse toMenuCategoryResponse(MenuCategory category);

    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateMenuCategory(@MappingTarget MenuCategory category, MenuCategoryRequest request);
}