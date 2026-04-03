package com.example.backend.menuitem.service;

import com.example.backend.menuitem.dto.MenuItemRequest;
import com.example.backend.menuitem.dto.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    MenuItemResponse create(MenuItemRequest request);
    List<MenuItemResponse> findAll();
    MenuItemResponse findById(Long id);
    MenuItemResponse update(Long id, MenuItemRequest request);
    void delete(Long id);
    List<MenuItemResponse> findByName(String name);
}