package com.salon.backend_salon_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The salon this service belongs to (set this from provider's profile / JWT in
    // production)
    private Long salonId;

    private String name;

    private String description;

    // price in smallest unit (e.g., cents) or in regular currency depending on your
    // convention. Use BigDecimal for money in production.
    private Double price;

    // duration in minutes
    private Integer durationMinutes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
