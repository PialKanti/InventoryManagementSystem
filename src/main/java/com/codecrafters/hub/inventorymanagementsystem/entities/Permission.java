package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.NonAuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "permissions")
public class Permission extends NonAuditableEntity {
    @Column(name = "permission_key",nullable = false, length = 100)
    private String key;
    @Column(nullable = false, length = 100)
    private String name;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Permission(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Permission() {
    }
}
