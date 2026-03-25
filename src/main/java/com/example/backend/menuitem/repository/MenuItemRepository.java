package com.example.backend.menuitem.repository;

import com.example.backend.menuitem.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
}
