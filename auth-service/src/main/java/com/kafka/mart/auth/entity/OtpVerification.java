package com.kafka.mart.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verified = false;

    @Builder.Default
    @Column(nullable = false)
    private Integer attemptCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}