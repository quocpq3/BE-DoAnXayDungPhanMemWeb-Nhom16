package com.example.backend.menuitem.repository;

import com.example.backend.menuitem.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    boolean existsBySlug(String slug);
    MenuItem findBySlug(String slug);
    List<MenuItem> findByItemNameContainingIgnoreCase(String name);
}
