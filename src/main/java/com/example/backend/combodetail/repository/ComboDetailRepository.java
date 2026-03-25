package com.example.backend.combodetail.repository;

import com.example.backend.combodetail.entity.ComboDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComboDetailRepository extends JpaRepository<ComboDetail, ComboDetail.ComboDetailId> {
    List<ComboDetail> findByIdComboItemId(Long comboItemId);
}