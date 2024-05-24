package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.AuditableEntity;
import com.codecrafters.hub.inventorymanagementsystem.entities.listeners.ElasticSearchIndexingListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EntityListeners(ElasticSearchIndexingListener.class)
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
