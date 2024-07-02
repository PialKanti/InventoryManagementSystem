package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListedTokenRepository extends CrudRepository<BlackListedToken, Long> {
    Optional<BlackListedToken> findByToken(String token);
}
