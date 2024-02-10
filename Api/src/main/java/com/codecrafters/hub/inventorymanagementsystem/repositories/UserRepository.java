package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.UserProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @EntityGraph(value = "User.roles", type = EntityGraph.EntityGraphType.FETCH)
    @Transactional(readOnly = true)
    Optional<User> findByUsername(String username);
    Optional<UserProjection> findByUsername(String username, Class<UserProjection> type);
}
