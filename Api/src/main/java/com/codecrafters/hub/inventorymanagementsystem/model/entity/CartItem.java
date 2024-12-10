package com.codecrafters.hub.inventorymanagementsystem.model.entity;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.common.NonAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cart_products")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CartItem extends NonAuditableEntity{
    @OneToOne
    private Product product;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
