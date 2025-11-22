// src/main/java/com/salon/backend_salon_management/entity/Booking.java
package com.salon.backend_salon_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceItem service;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // MUST BE PUBLIC
    public enum BookingStatus {
        PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW
    }
}