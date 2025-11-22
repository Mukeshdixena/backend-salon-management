// src/main/java/com/salon/backend_salon_managemen/model/ServiceEntity.java
package com.salon.backend_salon_managemen.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int durationMinutes;
    private double price;

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;
}