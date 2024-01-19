package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends AuditableEntity {
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
