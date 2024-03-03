package com.codecrafters.hub.inventorymanagementsystem.entities.common;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class NonAuditableEntity extends BaseEntity{
}
