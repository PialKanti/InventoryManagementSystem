package com.codecrafters.hub.inventorymanagementsystem.entities.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class AuditableEntity extends BaseEntity {
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @LastModifiedDate
    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;
}
