package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
}
