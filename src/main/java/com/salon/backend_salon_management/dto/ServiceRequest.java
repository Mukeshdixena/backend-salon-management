package com.salon.backend_salon_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private Long salonId; // optional - in prod derive from JWT
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
}
