package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.NonAuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission extends NonAuditableEntity {
    @Column(name = "permission_key", nullable = false, length = 100)
    private String key;
    @Column(nullable = false, length = 100)
    private String name;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;
}
