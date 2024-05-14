package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.NonAuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Rating extends NonAuditableEntity {
    private int rating;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    @Column(name = "created_on")
    @JsonIgnore
    private LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
