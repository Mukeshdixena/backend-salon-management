// src/main/java/com/salon/backend_salon_management/entity/Salon.java
package com.salon.backend_salon_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String address;
    @Column
    private String phone;
    @Column
    private String image;
    @Column
    private Double rating = 4.8;

    @Enumerated(EnumType.STRING)
    @Column
    private SalonStatus status = SalonStatus.PENDING;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // MUST BE PUBLIC AND INSIDE THIS FILE
    public enum SalonStatus {
        PENDING, APPROVED, REJECTED, BANNED
    }
}