package com.salon.backend_salon_management.repository;

import com.salon.backend_salon_management.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    List<ServiceItem> findBySalonId(Long salonId);
}
