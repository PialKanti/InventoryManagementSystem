package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Category extends AuditableEntity {
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
