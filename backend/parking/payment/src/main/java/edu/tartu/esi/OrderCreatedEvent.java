package edu.tartu.esi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private UUID payerId;
    private UUID receiverId;
    private UUID bookingId;
    private BigDecimal amount;
}
