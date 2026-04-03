package com.example.backend.menucategory.service.impl;

import com.example.backend.common.DataUtils;
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
        String slugToCheck = DataUtils.toSlug(request.getCategoryName());

        if (repository.findBySlug(slugToCheck) != null) {
            throw new AppException(ErrorCode.SLUG_ALREADY_EXISTS);
        }

        MenuCategory category = mapper.toMenuCategory(request);

        return mapper.toMenuCategoryResponse(repository.save(category));
    }

    @Override
    @Transactional
    public MenuCategoryResponse update(Long id, MenuCategoryRequest request) {
        MenuCategory category = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        String newSlug = DataUtils.toSlug(request.getCategoryName());

        if (!category.getSlug().equals(newSlug)) {
            if (repository.findBySlug(newSlug) != null) {
                throw new AppException(ErrorCode.SLUG_ALREADY_EXISTS);
            }
        }

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

    @Override
    @Transactional(readOnly = true)
    public List<MenuCategoryResponse> findByName(String name) {
        return repository.findByCategoryNameContainingIgnoreCase(name).stream()
                .map(mapper::toMenuCategoryResponse)
                .collect(Collectors.toList());
    }
}