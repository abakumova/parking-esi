package edu.tartu.esi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tartu.esi.dto.BookingDto;
import edu.tartu.esi.exception.BookingNotFoundException;
import edu.tartu.esi.mapper.BookingMapper;
import edu.tartu.esi.model.Booking;
import edu.tartu.esi.ParkingSlot;
import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.model.SlotStatusEnum;
import edu.tartu.esi.repository.BookingRepository;
import io.vavr.control.Try;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    private final KafkaTemplate<String, BookingDto> kafkaTemplate;

    @Value("${webclient.email}")
    private String email;
    @Value("${webclient.password}")
    private String password;


    public ResponseEntity createBooking(BookingDto bookingDto) throws JSONException {
//        if (getParkingSlotStatus(bookingDto.getParkingSlotId()).equals(SlotStatusEnum.CLOSED)) {
//            return ResponseEntity.badRequest().body("Parking slot is closed.");
//        }

        assertBookingDto(bookingDto, "Can't create a booking info when booking is null");
        Booking booking = Booking.builder()
                .id(bookingDto.getId())
                .customerId(bookingDto.getCustomerId())
                .parkingSlotId(bookingDto.getParkingSlotId())
                .price(bookingDto.getPrice())
                .timeFrom(bookingDto.getTimeFrom())
                .timeUntil(bookingDto.getTimeUntil())
                .landlordId(bookingDto.getLandlordId())
                .build();
        bookingRepository.save(booking);

        // Create a Circuit Breaker instance
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("payment", config);

        // Wrap the requestPayment method with the Circuit Breaker
        PaymentStatusEnum result = circuitBreaker.executeSupplier(() -> {
            return requestPayment(booking.getId());
        });
        if (result.equals(PaymentStatusEnum.COMPLETED)) {
            updateParkingSlotStatus(booking.getParkingSlotId(), SlotStatusEnum.CLOSED);
            scheduleUpdateParkingSlotStatus(booking.getParkingSlotId(), SlotStatusEnum.OPEN, booking.getTimeUntil());

            BookingDto message = BookingDto.builder()
                    .id(booking.getId())
                    .customerId(booking.getCustomerId())
                    .parkingSlotId(booking.getParkingSlotId())
                    .price(booking.getPrice())
                    .timeFrom(booking.getTimeFrom())
                    .timeUntil(booking.getTimeUntil())
                    .landlordId(booking.getLandlordId())
                    .build();

            kafkaTemplate.send("booking-topic", message);

            return ResponseEntity.ok("Booking completed.");
        } else {
            return ResponseEntity.badRequest().body("Payment rejected.");
        }
    }

    @Transactional
    public BookingDto getBookingById(String id) {
        log.info("-- fetch bookings");
        Booking booking = bookingRepository.findBookingById(id)
                .orElseThrow(() -> new BookingNotFoundException(format("Booking with id = %s wasn't found", id)));
        return bookingMapper.toDto(booking);
    }

    @LoadBalanced
    public void updateParkingSlotStatus(String parkingSlotId, SlotStatusEnum status) throws JSONException {
        String token = getToken(email, password);

        webClientBuilder.build()
                .put()
                .uri("http://localhost:8089/api/v1/parking-slots/" + parkingSlotId + "/status")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(status)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @LoadBalanced
    @SneakyThrows
    public PaymentStatusEnum requestPayment(String bookingId) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("payment", config);
        String token = getToken(email, password);
        return Try.ofSupplier(CircuitBreaker.decorateSupplier(circuitBreaker, () -> webClientBuilder.build()
                .post()
                .uri("http://localhost:8089/api/v1/make-payment")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(bookingId)
                .retrieve()
                .bodyToMono(PaymentStatusEnum.class)
                .block())).recover(throwable -> {
            log.error("Error occurred while making payment: {}", throwable.getMessage());
            return PaymentStatusEnum.DECLINED;
        }).get();
    }

    @LoadBalanced
    public SlotStatusEnum getParkingSlotStatus(String slotId) {
        String token = getToken(email, password);
        log.warn("!@%#^&#&#& TOKEN {}", token);

        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8089/api/v1/parking-slots/by-id/" + slotId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(ParkingSlot.class)
                .block()
                .getParkingSlotStatus();
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

    private void scheduleUpdateParkingSlotStatus(String parkingSlotId, SlotStatusEnum status, LocalDateTime timeUntil) {
        long delayInMillis = Duration.between(LocalDateTime.now(), timeUntil).toMillis();
        scheduledExecutorService.schedule(() -> {
            try {
                updateParkingSlotStatus(parkingSlotId, status);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, delayInMillis, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    private String getToken(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);
        log.warn("!!!!!!!!!!!!!!!!!!!!!! {}", json);

        JsonNode jwtTokenMono = webClientBuilder
                .build()
                .post()
                .uri("http://localhost:8089/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(s -> s.path("access_token"))
                .block();

        log.warn("!!!!!!!!!!!!!!!!!!!!!! {}", jwtTokenMono);
        return jwtTokenMono.asText();
    }
}
