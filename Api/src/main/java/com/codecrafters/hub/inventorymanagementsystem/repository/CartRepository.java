package com.codecrafters.hub.inventorymanagementsystem.repository;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long>{
    <T> Optional<T> findByUsernameAndDeletedFalse(String username, Class<T> type);
}