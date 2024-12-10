package com.codecrafters.hub.inventorymanagementsystem.model.entity.common;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class NonAuditableEntity extends BaseEntity{
}
