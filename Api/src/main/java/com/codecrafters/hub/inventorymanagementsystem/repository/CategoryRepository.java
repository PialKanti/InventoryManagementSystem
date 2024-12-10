package com.codecrafters.hub.inventorymanagementsystem.repository;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
}
