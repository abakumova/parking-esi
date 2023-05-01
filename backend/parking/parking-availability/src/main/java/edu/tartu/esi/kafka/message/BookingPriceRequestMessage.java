package edu.tartu.esi.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPriceRequestMessage {

    private String requestId;
    private String slotId;
}