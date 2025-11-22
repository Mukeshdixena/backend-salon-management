package com.salon.backend_salon_management.controller;

import com.salon.backend_salon_management.dto.ServiceRequest;
import com.salon.backend_salon_management.dto.ServiceResponse;
import com.salon.backend_salon_management.service.ProviderServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderServicesController {

    private final ProviderServiceService providerServiceService;

    // List services for a salon. In production get salonId from JWT / authenticated principal.
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> list(@RequestParam(required = false) Long salonId) {
        // TODO: Replace the fallback below by extracting salonId from JWT/token
        if (salonId == null) salonId = 1L;
        var list = providerServiceService.listBySalon(salonId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> get(@PathVariable Long id) {
        var s = providerServiceService.getById(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s);
    }

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody ServiceRequest req, @RequestParam(required = false) Long salonId) {
        // Prefer explicit salonId param, else fall back to req.salonId
        if (salonId != null) req.setSalonId(salonId);
        if (req.getSalonId() == null) req.setSalonId(1L); // fallback for dev
        var created = providerServiceService.create(req);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @RequestBody ServiceRequest req) {
        var updated = providerServiceService.update(id, req);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean ok = providerServiceService.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
