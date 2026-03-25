package com.example.backend.menuitem.entity;

import com.example.backend.menucategory.entity.MenuCategory;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MenuCategory category;

    @Column(nullable = false, length = 200)
    private String itemName;

    @Column(unique = true, length = 150)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    private Double basePrice;

    private Integer discountPercent;

    private Double salePrice;

    private Boolean isAvailable = true;

    private Boolean isCombo = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        calculatePrices();
    }

    @PreUpdate
    protected void onUpdate() {
        calculatePrices();
    }

    private void calculatePrices() {
        if (this.basePrice == null) {
            this.salePrice = 0.0;
            return;
        }
        if (this.discountPercent != null && this.discountPercent > 0) {
            this.salePrice = this.basePrice * (1 - (this.discountPercent / 100.0));
        } else {
            this.salePrice = this.basePrice;
        }
    }
}