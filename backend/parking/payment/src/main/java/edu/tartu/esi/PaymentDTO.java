package edu.tartu.esi;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private UUID payerId;
    private UUID receiverId;
    private UUID bookingId;
    private LocalDateTime time;
    private BigDecimal amount;
    private Payment.PaymentStatus status;

    // Getters and setters

    // Additional methods if needed
}
