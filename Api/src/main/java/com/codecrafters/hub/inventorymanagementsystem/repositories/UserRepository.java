package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @EntityGraph(value = "User.roles", type = EntityGraph.EntityGraphType.FETCH)
    <T> Optional<T> findByUsername(String username, Class<T> type);
    boolean existsByUsername(String username);

    @Modifying
    @Query("delete from User u where u.username = :username")
    void deleteByUsername(String username);
}
