package com.example.backend.combodetail.mapper;

import com.example.backend.combodetail.dto.ComboDetailRequest;
import com.example.backend.combodetail.dto.ComboDetailResponse;
import com.example.backend.combodetail.entity.ComboDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComboDetailMapper {

    @Mapping(target = "id.comboItemId", source = "comboItemId")
    @Mapping(target = "id.componentItemId", source = "componentItemId")
    @Mapping(target = "comboItem", ignore = true)
    @Mapping(target = "componentItem", ignore = true)
    ComboDetail toEntity(ComboDetailRequest request);

    @Mapping(target = "comboItemId", source = "id.comboItemId")
    @Mapping(target = "componentItemId", source = "id.componentItemId")
    @Mapping(target = "componentName", source = "componentItem.itemName")
    @Mapping(target = "unitPrice", source = "componentItem.salePrice")
    @Mapping(target = "subTotal", expression = "java(detail.getQuantity() * detail.getComponentItem().getSalePrice())")
    ComboDetailResponse toResponse(ComboDetail detail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comboItem", ignore = true)
    @Mapping(target = "componentItem", ignore = true)
    void updateEntity(@MappingTarget ComboDetail detail, ComboDetailRequest request);
}