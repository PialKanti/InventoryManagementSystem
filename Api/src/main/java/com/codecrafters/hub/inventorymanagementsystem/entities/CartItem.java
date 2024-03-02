package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.NonAuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem extends NonAuditableEntity{
    @OneToOne
    private Product product;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
