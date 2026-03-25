package com.example.backend.menucategory.service.impl;

import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.menucategory.dto.MenuCategoryRequest;
import com.example.backend.menucategory.dto.MenuCategoryResponse;
import com.example.backend.menucategory.entity.MenuCategory;
import com.example.backend.menucategory.mapper.MenuCategoryMapper;
import com.example.backend.menucategory.repository.MenuCategoryRepository;
import com.example.backend.menucategory.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository repository;
    private final MenuCategoryMapper mapper;

    @Override
    public List<MenuCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toMenuCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuCategoryResponse findById(Long id) {
        MenuCategory category = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        return mapper.toMenuCategoryResponse(category);
    }

    @Override
    @Transactional
    public MenuCategoryResponse create(MenuCategoryRequest request) {
        MenuCategory category = mapper.toMenuCategory(request);
        return mapper.toMenuCategoryResponse(repository.save(category));
    }

    @Override
    @Transactional
    public MenuCategoryResponse update(Long id, MenuCategoryRequest request) {
        MenuCategory category = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        mapper.updateMenuCategory(category, request);
        return mapper.toMenuCategoryResponse(repository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        repository.deleteById(id);
    }
}