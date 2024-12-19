package com.codecrafters.hub.inventorymanagementsystem.model.entity;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Cart extends AuditableEntity {
    @Column(name = "username", unique = true)
    private String username;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }
}