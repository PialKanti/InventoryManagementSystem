package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private List<CartItem> cartItems;
}
