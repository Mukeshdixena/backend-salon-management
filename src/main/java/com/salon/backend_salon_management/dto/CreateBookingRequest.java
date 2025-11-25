package com.salon.backend_salon_management.dto;

public record CreateBookingRequest(
        Long customerId,
        Long salonId,
        Long serviceId,
        String date,
        String time) {
}
