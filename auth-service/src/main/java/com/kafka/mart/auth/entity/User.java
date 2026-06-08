package com.kafka.mart.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private static final ZoneId INDIA_ZONE =
            ZoneId.of("Asia/Kolkata");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean emailVerified = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastOtpSentAt;

    @Builder.Default
    @Column(nullable = false)
    private Integer failedLoginAttempts = 0;

    private LocalDateTime lockTime;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now(
                INDIA_ZONE
        );

        updatedAt = LocalDateTime.now(
                INDIA_ZONE
        );
    }

    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now(
                INDIA_ZONE
        );
    }


}
