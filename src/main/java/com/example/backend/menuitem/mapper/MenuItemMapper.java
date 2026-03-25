package com.example.backend.menuitem.mapper;

import com.example.backend.menuitem.dto.MenuItemRequest;
import com.example.backend.menuitem.dto.MenuItemResponse;
import com.example.backend.menuitem.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mapping(target = "category.categoryId", source = "categoryId")
    MenuItem toMenuItem(MenuItemRequest request);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    MenuItemResponse toMenuItemResponse(MenuItem menuItem);

    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "category.categoryId", source = "categoryId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "salePrice", ignore = true) // Để Entity tự calculatePrices()
    void updateMenuItem(@MappingTarget MenuItem menuItem, MenuItemRequest request);
}