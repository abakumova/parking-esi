package edu.tartu.esi.service;

import edu.tartu.esi.dto.BookingDto;
import edu.tartu.esi.exception.BookingNotFoundException;
import edu.tartu.esi.kafka.message.BookingMessage;
import edu.tartu.esi.mapper.BookingMapper;
import edu.tartu.esi.model.Booking;
import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.model.SlotStatusEnum;
import edu.tartu.esi.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    @Autowired
    private WebClient.Builder webClientBuilder;

//    private final KafkaTemplate<String, BookingMessage> kafkaTemplate;


    public String createBooking(BookingDto bookingDto) {
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

//        BookingMessage message = new BookingMessage(
//                UUID.randomUUID().toString(),
//                booking.getId(),
//                booking.getParkingSlotId(),
//                booking.getTimeFrom(),
//                booking.getTimeUntil(),
//                null,
//                null
//        );
//
//        kafkaTemplate.send("booking-topic", message);

        log.info("Booking {} is added to the Database", booking.getId());

        PaymentStatusEnum status = requestPayment(booking.getId());
        if (status.equals(PaymentStatusEnum.COMPLETED)) {
            updateParkingSlotStatus(booking.getParkingSlotId(), SlotStatusEnum.CLOSED);
            return "Booking completed.";
        } else {
            return "Payment rejected.";
        }
    }

    @Transactional
    public BookingDto getBookingById(String id) {
        log.info("-- fetch bookings");
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(format("Booking with id = %s wasn't found", id)));
        return bookingMapper.toDto(booking);
    }

    public void updateParkingSlotStatus(String parkingSlotId, SlotStatusEnum status) {
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8084/api/v1/parking-slots/status", parkingSlotId)
                .bodyValue(status)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public PaymentStatusEnum requestPayment(String bookingId) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8087/api/v1/make-payment")
                .bodyValue(bookingId)
                .retrieve()
                .bodyToMono(PaymentStatusEnum.class)
                .block();
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
