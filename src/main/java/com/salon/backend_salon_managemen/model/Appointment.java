// src/main/java/com/salon/backend_salon_managemen/model/Appointment.java
package com.salon.backend_salon_managemen.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerPhone;

    @ManyToOne
    private ServiceEntity service;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.WAITING;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    public enum AppointmentStatus {
        WAITING, IN_PROGRESS, COMPLETED, CANCELLED
    }
}