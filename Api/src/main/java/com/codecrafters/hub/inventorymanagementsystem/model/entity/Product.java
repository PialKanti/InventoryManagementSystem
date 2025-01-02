package com.codecrafters.hub.inventorymanagementsystem.model.entity;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.common.AuditableEntity;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.listener.ProductEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EntityListeners(ProductEntityListener.class)
@Entity
@Table(name = "products")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@NamedEntityGraph(name = "productEntityGraph", attributeNodes = {
        @NamedAttributeNode("category"),
        @NamedAttributeNode("ratings")
})
public class Product extends AuditableEntity {
    @Column(nullable = false, length = 200)
    private String title;
    @Column(length = 1000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
    private int quantity;
    @Column(name = "avg_rating")
    private float averageRating;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
