package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long>{
    boolean existsByUsername(String username);
    <T> T findByUsername(String username, Class<T> type);
}
