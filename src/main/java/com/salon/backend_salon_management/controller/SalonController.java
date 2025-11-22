package com.salon.backend_salon_management.controller;

import com.salon.backend_salon_management.entity.Salon;
import com.salon.backend_salon_management.entity.ServiceItem;
import com.salon.backend_salon_management.repository.SalonRepository;
import com.salon.backend_salon_management.repository.ServiceItemRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SalonController {

    private final SalonRepository salonRepo;
    private final ServiceItemRepository serviceItemRepo;

    @GetMapping
    public ResponseEntity<List<Salon>> getAllSalons() {
        return ResponseEntity.ok(salonRepo.findAll());
    }

    @GetMapping("/{salonId}/services")
    public ResponseEntity<List<ServiceItem>> getSalonServices(@PathVariable Long salonId) {
        return ResponseEntity.ok(serviceItemRepo.findBySalonId(salonId));
    }
}
