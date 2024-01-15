package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
