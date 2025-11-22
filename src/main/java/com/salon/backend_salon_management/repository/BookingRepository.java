package com.salon.backend_salon_management.repository;

import com.salon.backend_salon_management.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBySalonIdAndDateOrderByTimeAsc(Long salonId, LocalDate date);

    List<Booking> findByCustomerIdOrderByDateDescTimeDesc(Long customerId);
}
