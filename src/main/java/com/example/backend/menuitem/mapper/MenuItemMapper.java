package com.example.backend.menuitem.mapper;

import com.example.backend.common.DataUtils;
import com.example.backend.menuitem.dto.MenuItemRequest;
import com.example.backend.menuitem.dto.MenuItemResponse;
import com.example.backend.menuitem.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {DataUtils.class})
public interface MenuItemMapper {

    @Mapping(target = "slug", expression = "java(DataUtils.toSlug(request.getItemName()))")
    @Mapping(target = "category", ignore = true) // Sẽ set thủ công trong service từ repo
    MenuItem toMenuItem(MenuItemRequest request);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    MenuItemResponse toMenuItemResponse(MenuItem menuItem);

    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "slug", expression = "java(DataUtils.toSlug(request.getItemName()))")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "salePrice", ignore = true)
    void updateMenuItem(@MappingTarget MenuItem menuItem, MenuItemRequest request);
}