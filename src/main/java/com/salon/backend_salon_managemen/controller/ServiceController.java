// src/main/java/com/salon/backend_salon_managemen/controller/ServiceController.java
package com.salon.backend_salon_managemen.controller;

import com.salon.backend_salon_managemen.model.*;
import com.salon.backend_salon_managemen.repository.SalonRepository;
import com.salon.backend_salon_managemen.repository.ServiceRepository;
import com.salon.backend_salon_managemen.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;
    @Autowired
    private SalonRepository salonRepo;

    // Get all services for provider's salons
    @GetMapping("/services")
    public List<ServiceEntity> getServices(Authentication auth) {
        // In real app: get provider → their salons → services
        return serviceRepo.findAll();
    }

    @PostMapping("/services")
    public ServiceEntity createService(@RequestBody ServiceEntity service) {
        return serviceRepo.save(service);
    }

    @PutMapping("/services/{id}")
    public ServiceEntity updateService(@PathVariable Long id, @RequestBody ServiceEntity service) {
        service.setId(id);
        return serviceRepo.save(service);
    }

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceRepo.deleteById(id);
    }

    // Queue Management
    @GetMapping("/queue")
    public List<Appointment> getQueue() {
        return appointmentRepo.findByStatusInOrderByCreatedAtAsc(
                List.of(Appointment.AppointmentStatus.WAITING, Appointment.AppointmentStatus.IN_PROGRESS));
    }

    @PostMapping("/queue")
    public Appointment addToQueue(@RequestBody Appointment appointment) {
        appointment.setStatus(Appointment.AppointmentStatus.WAITING);
        return appointmentRepo.save(appointment);
    }

    @PutMapping("/queue/{id}/start")
    public Appointment startService(@PathVariable Long id) {
        Appointment appt = appointmentRepo.findById(id).orElseThrow();
        appt.setStatus(Appointment.AppointmentStatus.IN_PROGRESS);
        appt.setStartedAt(LocalDateTime.now());
        return appointmentRepo.save(appt);
    }

    @PutMapping("/queue/{id}/complete")
    public Appointment completeService(@PathVariable Long id) {
        Appointment appt = appointmentRepo.findById(id).orElseThrow();
        appt.setStatus(Appointment.AppointmentStatus.COMPLETED);
        appt.setCompletedAt(LocalDateTime.now());
        return appointmentRepo.save(appt);
    }

    @DeleteMapping("/queue/{id}")
    public void removeFromQueue(@PathVariable Long id) {
        appointmentRepo.deleteById(id);
    }
}