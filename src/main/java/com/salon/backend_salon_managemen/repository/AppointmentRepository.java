// src/main/java/com/salon/backend_salon_managemen/repository/AppointmentRepository.java
package com.salon.backend_salon_managemen.repository;

import com.salon.backend_salon_managemen.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatusInOrderByCreatedAtAsc(List<Appointment.AppointmentStatus> statuses);
}