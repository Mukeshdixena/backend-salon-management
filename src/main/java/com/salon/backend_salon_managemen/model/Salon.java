package com.salon.backend_salon_managemen.model;

import jakarta.persistence.*;
import lombok.*;

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

    private String address;
    private String phone;

    private Double latitude;
    private Double longitude;

    private String ownerName;
}