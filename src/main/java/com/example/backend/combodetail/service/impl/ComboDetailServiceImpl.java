package com.example.backend.combodetail.service.impl;

import com.example.backend.combodetail.dto.ComboDetailRequest;
import com.example.backend.combodetail.dto.ComboDetailResponse;
import com.example.backend.combodetail.entity.ComboDetail;
import com.example.backend.combodetail.mapper.ComboDetailMapper;
import com.example.backend.combodetail.repository.ComboDetailRepository;
import com.example.backend.combodetail.service.ComboDetailService;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.menuitem.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComboDetailServiceImpl implements ComboDetailService {
    private final ComboDetailRepository repository;
    private final MenuItemRepository menuItemRepository;
    private final ComboDetailMapper mapper;

    @Override
    @Transactional
    public ComboDetailResponse create(ComboDetailRequest request) {
        var combo = menuItemRepository.findById(request.getComboItemId())
                .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));
        var component = menuItemRepository.findById(request.getComponentItemId())
                .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));

        ComboDetail detail = mapper.toEntity(request);
        detail.setComboItem(combo);
        detail.setComponentItem(component);

        return mapper.toResponse(repository.save(detail));
    }

    @Override
    public List<ComboDetailResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ComboDetailResponse findById(Long comboId, Long componentId) {
        return repository.findById(new ComboDetail.ComboDetailId(comboId, componentId))
                .map(mapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));
    }

    @Override
    @Transactional
    public ComboDetailResponse update(ComboDetailRequest request) {
        ComboDetail.ComboDetailId id = new ComboDetail.ComboDetailId(request.getComboItemId(), request.getComponentItemId());
        ComboDetail detail = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED));

        mapper.updateEntity(detail, request);
        return mapper.toResponse(repository.save(detail));
    }

    @Override
    @Transactional
    public void delete(Long comboId, Long componentId) {
        ComboDetail.ComboDetailId id = new ComboDetail.ComboDetailId(comboId, componentId);
        if (!repository.existsById(id)) throw new AppException(ErrorCode.MENU_ITEM_NOT_EXISTED);
        repository.deleteById(id);
    }

    @Override
    public List<ComboDetailResponse> findByComboId(Long comboId) {
        return repository.findAll().stream()
                .filter(d -> d.getId().getComboItemId().equals(comboId))
                .map(mapper::toResponse).toList();
    }
}