package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.entities.common.NonAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "blacklist_tokens")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BlackListedToken extends NonAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "expired_at")
    private LocalDateTime expiryDateTime;
}
