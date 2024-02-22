package com.codecrafters.hub.inventorymanagementsystem.configs;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbValueInitializer implements ApplicationRunner {
    private final RoleRepository repository;

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
