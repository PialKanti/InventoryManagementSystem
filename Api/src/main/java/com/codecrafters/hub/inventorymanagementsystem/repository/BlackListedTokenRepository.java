package com.codecrafters.hub.inventorymanagementsystem.repository;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.BlackListedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListedTokenRepository extends CrudRepository<BlackListedToken, Long> {
    Optional<BlackListedToken> findByToken(String token);
}
