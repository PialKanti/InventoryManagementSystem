package com.codecrafters.hub.inventorymanagementsystem.repository;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long>{
    boolean existsByUsername(String username);
    <T> Optional<T> findByUsername(String username, Class<T> type);
}
