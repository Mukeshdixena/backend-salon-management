package com.salon.backend_salon_management.controller;

import com.salon.backend_salon_management.dto.CreateBookingRequest;
import com.salon.backend_salon_management.entity.Booking;
import com.salon.backend_salon_management.repository.BookingRepository;
import com.salon.backend_salon_management.repository.SalonRepository;
import com.salon.backend_salon_management.repository.ServiceItemRepository;
import com.salon.backend_salon_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final SalonRepository salonRepo;
    private final ServiceItemRepository serviceRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest req) {

        var customer = userRepo.findById(req.customerId()).orElse(null);
        var salon = salonRepo.findById(req.salonId()).orElse(null);
        var service = serviceRepo.findById(req.serviceId()).orElse(null);

        if (customer == null || salon == null || service == null) {
            return ResponseEntity.badRequest().body("Invalid booking data");
        }

        Booking booking = Booking.builder()
                .customer(customer)
                .salon(salon)
                .service(service)
                .date(LocalDate.parse(req.date()))
                .time(req.time())
                .price(service.getPrice())
                .status(Booking.BookingStatus.PENDING)
                .build();

        bookingRepo.save(booking);

        return ResponseEntity.ok(booking.getId());
    }

    @GetMapping("/slots")
    public ResponseEntity<List<String>> getAvailableSlots(
            @RequestParam Long salonId,
            @RequestParam Long serviceId,
            @RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);

        List<Booking> bookings = bookingRepo.findBySalonIdAndDateOrderByTimeAsc(salonId, parsedDate);
        List<String> booked = bookings.stream().map(Booking::getTime).toList();

        List<String> slots = List.of(
                "10:00 AM", "11:00 AM", "12:00 PM",
                "01:00 PM", "02:00 PM", "03:00 PM",
                "04:00 PM", "05:00 PM", "06:00 PM");

        List<String> available = slots.stream()
                .filter(s -> !booked.contains(s))
                .toList();

        return ResponseEntity.ok(available);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return bookingRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Booking>> customerBookings(@RequestParam Long customerId) {
        List<Booking> list = bookingRepo.findByCustomerIdOrderByDateDescTimeDesc(customerId);
        return ResponseEntity.ok(list);
    }
}
