package com.salon.backend_salon_management.service;

import com.salon.backend_salon_management.dto.ServiceRequest;
import com.salon.backend_salon_management.dto.ServiceResponse;
import com.salon.backend_salon_management.entity.ServiceItem;
import com.salon.backend_salon_management.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderServiceService {

    private final ServiceRepository serviceRepository;

    private ServiceResponse toDto(ServiceItem s) {
        return new ServiceResponse(
                s.getId(),
                s.getSalonId(),
                s.getName(),
                s.getDescription(),
                s.getPrice(),
                s.getDurationMinutes()
        );
    }

    public List<ServiceResponse> listBySalon(Long salonId) {
        return serviceRepository.findBySalonIdOrderByCreatedAtDesc(salonId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public ServiceResponse create(ServiceRequest req) {
        ServiceItem s = ServiceItem.builder()
                .salonId(req.getSalonId())
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .durationMinutes(req.getDurationMinutes())
                .build();
        ServiceItem saved = serviceRepository.save(s);
        return toDto(saved);
    }

    public ServiceResponse update(Long id, ServiceRequest req) {
        var opt = serviceRepository.findById(id);
        if (opt.isEmpty()) return null;
        ServiceItem s = opt.get();
        if (req.getName() != null) s.setName(req.getName());
        if (req.getDescription() != null) s.setDescription(req.getDescription());
        if (req.getPrice() != null) s.setPrice(req.getPrice());
        if (req.getDurationMinutes() != null) s.setDurationMinutes(req.getDurationMinutes());
        ServiceItem updated = serviceRepository.save(s);
        return toDto(updated);
    }

    public boolean delete(Long id) {
        if (!serviceRepository.existsById(id)) return false;
        serviceRepository.deleteById(id);
        return true;
    }

    public ServiceResponse getById(Long id) {
        return serviceRepository.findById(id).map(this::toDto).orElse(null);
    }
}
