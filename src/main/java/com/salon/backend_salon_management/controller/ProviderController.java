// src/main/java/com/salon/backend_salon_management/controller/ProviderController.java
package com.salon.backend_salon_management.controller;

import com.salon.backend_salon_management.entity.Booking;
import com.salon.backend_salon_management.entity.Booking.BookingStatus;
import com.salon.backend_salon_management.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final BookingRepository bookingRepository;

    @GetMapping("/today-queue")
    public ResponseEntity<List<Booking>> getTodayQueue() {
        Long salonId = 1L; // TODO: Later extract from JWT
        List<Booking> queue = bookingRepository.findBySalonIdAndDateOrderByTimeAsc(salonId, LocalDate.now());
        return ResponseEntity.ok(queue);
    }

    @PostMapping("/bookings/{id}/start")
    public ResponseEntity<?> startService(@PathVariable Long id) {
        var booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(BookingStatus.IN_PROGRESS);
            bookingRepository.save(booking);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookings/{id}/complete")
    public ResponseEntity<?> completeService(@PathVariable Long id) {
        var booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(BookingStatus.COMPLETED);
            booking.setCompletedAt(java.time.LocalDateTime.now());
            bookingRepository.save(booking);
        }
        return ResponseEntity.ok().build();
    }
}