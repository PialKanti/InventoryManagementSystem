package com.codecrafters.hub.inventorymanagementsystem.configs;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DbValueInitializer implements ApplicationRunner {
    private final RoleRepository repository;

    public DbValueInitializer(@Autowired RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        insertUserRolesToDB();
    }

    private void insertUserRolesToDB() {
        for (UserRole userRole : UserRole.values()) {
            repository.save(Role
                    .builder()
                    .key(userRole.toString())
                    .name(userRole.getDisplayValue())
                    .build());
        }
    }
}
