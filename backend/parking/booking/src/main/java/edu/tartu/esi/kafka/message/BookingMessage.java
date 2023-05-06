package edu.tartu.esi.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingMessage {
    private String requestId;
    private String id;
    private String parkingSlotId;
    private LocalDateTime timeFrom;
    private LocalDateTime timeUntil;
    private String paymentStatus;
    private String parkingSlotStatus;
}
