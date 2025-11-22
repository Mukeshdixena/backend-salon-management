package com.salon.backend_salon_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {
    private Long id;
    private Long salonId;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
}
