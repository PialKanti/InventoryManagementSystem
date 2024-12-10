package com.codecrafters.hub.inventorymanagementsystem.repository;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByKey(String key);
}
