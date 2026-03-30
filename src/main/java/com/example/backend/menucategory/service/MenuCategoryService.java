package com.example.backend.menucategory.service;

import com.example.backend.menucategory.dto.MenuCategoryRequest;
import com.example.backend.menucategory.dto.MenuCategoryResponse;

import java.util.List;

public interface MenuCategoryService {
    List<MenuCategoryResponse> findAll();
    MenuCategoryResponse findById(Long id);
    MenuCategoryResponse create(MenuCategoryRequest request);
    MenuCategoryResponse update(Long id, MenuCategoryRequest request);
    void delete(Long id);
    List<MenuCategoryResponse> findByName(String name);
}
