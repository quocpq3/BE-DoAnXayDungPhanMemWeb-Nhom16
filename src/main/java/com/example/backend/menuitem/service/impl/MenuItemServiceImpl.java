package com.example.backend.menuitem.service.impl;

import com.example.backend.common.DataUtils;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.menuitem.dto.MenuItemRequest;
import com.example.backend.menuitem.dto.MenuItemResponse;
import com.example.backend.menuitem.entity.MenuItem;
import com.example.backend.menuitem.mapper.MenuItemMapper;
import com.example.backend.menuitem.repository.MenuItemRepository;
import com.example.backend.menucategory.repository.MenuCategoryRepository;
import com.example.backend.menuitem.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final MenuCategoryRepository categoryRepository;
    private final MenuItemMapper mapper;

    @Override
    @Transactional
    public MenuItemResponse create(MenuItemRequest request) {
        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        String slug = DataUtils.toSlug(request.getItemName());
        if (repository.existsBySlug(slug)) {
            throw new AppException(ErrorCode.SLUG_ALREADY_EXISTS);
        }

        MenuItem menuItem = mapper.toMenuItem(request);
        menuItem.setCategory(category);

        return mapper.toMenuItemResponse(repository.save(menuItem));
    }

    @Override
    @Transactional
    public MenuItemResponse update(Long id, MenuItemRequest request) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));

        String newSlug = DataUtils.toSlug(request.getItemName());
        if (!item.getSlug().equals(newSlug) && repository.existsBySlug(newSlug)) {
            throw new AppException(ErrorCode.SLUG_ALREADY_EXISTS);
        }

        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        mapper.updateMenuItem(item, request);
        item.setCategory(category);

        return mapper.toMenuItemResponse(repository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toMenuItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MenuItemResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toMenuItemResponse)
                .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));
    }
    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED);
        }
        repository.deleteById(id);
    }
}