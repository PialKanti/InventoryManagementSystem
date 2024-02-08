package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByKey(String key);
}
