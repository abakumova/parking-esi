package edu.tartu.esi.controller;

import edu.tartu.esi.dto.BookingDto;
import edu.tartu.esi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "/bookings/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public BookingDto getBooking(@Valid @PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping(value = "/bookings", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        bookingService.createBooking(bookingDto);
        return ResponseEntity.ok("Parking restriction has been created");
    }

    @PutMapping(value = "/bookings/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateBooking(@Valid @PathVariable String id, @Valid @RequestBody BookingDto bookingDto) {
        bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok("Parking restriction has been updated");
    }

    @DeleteMapping(value = "/bookings/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteParkingStatus(@Valid @PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Parking restriction has been deleted");
    }
}
