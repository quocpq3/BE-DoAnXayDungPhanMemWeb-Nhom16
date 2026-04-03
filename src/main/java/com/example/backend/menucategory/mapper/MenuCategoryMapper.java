package com.example.backend.menucategory.mapper;

import com.example.backend.common.DataUtils;
import com.example.backend.menucategory.dto.MenuCategoryRequest;
import com.example.backend.menucategory.dto.MenuCategoryResponse;
import com.example.backend.menucategory.entity.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {DataUtils.class})
public interface MenuCategoryMapper {

    @Mapping(target = "slug", expression = "java(DataUtils.toSlug(request.getCategoryName()))")
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    MenuCategory toMenuCategory(MenuCategoryRequest request);

    MenuCategoryResponse toMenuCategoryResponse(MenuCategory category);

    @Mapping(target = "slug", expression = "java(DataUtils.toSlug(request.getCategoryName()))")
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateMenuCategory(@MappingTarget MenuCategory category, MenuCategoryRequest request);
}