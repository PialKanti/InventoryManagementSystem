package com.codecrafters.hub.inventorymanagementsystem.model.entity;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.common.NonAuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Permission extends NonAuditableEntity {
    @Column(name = "permission_key", nullable = false, length = 100)
    private String key;
    @Column(nullable = false, length = 100)
    private String name;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;
}
