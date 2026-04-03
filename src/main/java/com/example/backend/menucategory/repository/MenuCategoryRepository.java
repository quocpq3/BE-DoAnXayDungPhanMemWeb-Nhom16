package com.example.backend.menucategory.repository;

import com.example.backend.menucategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    MenuCategory findBySlug(String slug);
    List<MenuCategory> findByCategoryNameContainingIgnoreCase(String name);
}