package edu.tartu.esi.service;

import edu.tartu.esi.dto.BookingDto;
import edu.tartu.esi.exception.BookingNotFoundException;
import edu.tartu.esi.mapper.BookingMapper;
import edu.tartu.esi.model.Booking;
import edu.tartu.esi.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public void createBooking(BookingDto bookingDto) {
        assertBookingDto(bookingDto, "Can't create a booking info when booking is null");
        Booking booking = Booking.builder()
                .id(bookingDto.getId())
                .customerId(bookingDto.getCustomerId())
                .parkingSlotId(bookingDto.getParkingSlotId())
                .price(bookingDto.getPrice())
                .timeFrom(bookingDto.getTimeFrom())
                .timeUntil(bookingDto.getTimeUntil())
                .build();
        bookingRepository.save(booking);
        log.info("Booking {} is added to the Database", booking.getId());
    }

    @Transactional
    public BookingDto getBookingById(String id) {
        log.info("-- fetch bookings");
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(format("Booking with id = %s wasn't found", id)));
        return bookingMapper.toDto(booking);
    }

    public void updateBooking(String id, BookingDto bookingDto) {
        Booking booking = Booking.builder()
                .id(id)
                .customerId(bookingDto.getCustomerId())
                .parkingSlotId(bookingDto.getParkingSlotId())
                .price(bookingDto.getPrice())
                .timeFrom(bookingDto.getTimeFrom())
                .timeUntil(bookingDto.getTimeUntil())
                .build();
        bookingRepository.save(booking);
        log.info("-- Booking {} has been updated", booking.getId());
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
        log.info("-- Booking {} has been deleted", id);
    }

    public List<BookingDto> getAllBookingsByUserId(String id) {
        List<Booking> list = bookingRepository.findAllByCustomerId(id);
        return list
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    private void assertBookingDto(BookingDto booking, String msg) {
        if (booking == null) {
            log.info("The body is missing");
            throw new IllegalArgumentException(msg);
        }
    }
}
