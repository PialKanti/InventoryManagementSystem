package com.codecrafters.hub.inventorymanagementsystem.config;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.Role;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.repository.RoleRepository;
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
