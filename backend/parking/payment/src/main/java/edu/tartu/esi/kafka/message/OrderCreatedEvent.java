package edu.tartu.esi.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private String payerId;
    private String receiverId;
    private String bookingId;
    private BigDecimal amount;
}
