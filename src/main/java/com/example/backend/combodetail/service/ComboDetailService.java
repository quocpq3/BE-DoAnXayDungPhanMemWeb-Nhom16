package com.example.backend.combodetail.service;

import com.example.backend.combodetail.dto.ComboDetailRequest;
import com.example.backend.combodetail.dto.ComboDetailResponse;
import java.util.List;

public interface ComboDetailService {
    ComboDetailResponse create(ComboDetailRequest request);
    List<ComboDetailResponse> findAll();
    ComboDetailResponse findById(Long comboId, Long componentId);
    ComboDetailResponse update(ComboDetailRequest request);
    void delete(Long comboId, Long componentId);
    List<ComboDetailResponse> findByComboId(Long comboId);
}