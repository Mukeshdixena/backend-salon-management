package com.salon.backend_salon_managemen.controller;

import com.salon.backend_salon_managemen.model.Salon;
import com.salon.backend_salon_managemen.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonRepository salonRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Backend is alive!");
    }

    @PostMapping
    public Salon create(@RequestBody Salon salon) {
        return salonRepository.save(salon);
    }

    @GetMapping
    public Iterable<Salon> getAll() {
        return salonRepository.findAll();
    }
}