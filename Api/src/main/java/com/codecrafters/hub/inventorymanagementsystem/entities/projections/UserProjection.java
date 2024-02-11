package com.codecrafters.hub.inventorymanagementsystem.entities.projections;

import java.util.List;

public interface UserProjection {
    String getFirstName();

    String getLastName();

    String getUsername();

    String getEmail();
    List<Role> getRoles();

    interface Role{
        String getId();
        String getKey();
        String getName();
    }
}
