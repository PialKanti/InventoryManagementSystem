package com.codecrafters.hub.inventorymanagementsystem.entities.projections;

public interface UserProjection extends BaseProjection {
    String getFirstName();

    String getLastName();

    String getUsername();

    String getEmail();
}
