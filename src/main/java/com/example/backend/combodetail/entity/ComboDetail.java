package com.example.backend.combodetail.entity;

import com.example.backend.menuitem.entity.MenuItem;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "combo_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComboDetail {

    @EmbeddedId
    private ComboDetailId id;

    @ManyToOne
    @MapsId("comboItemId")
    @JoinColumn(name = "combo_item_id")
    private MenuItem comboItem;

    @ManyToOne
    @MapsId("componentItemId")
    @JoinColumn(name = "component_item_id")
    private MenuItem componentItem;

    private Integer quantity;

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComboDetailId implements Serializable {
        private Long comboItemId;
        private Long componentItemId;
    }
}