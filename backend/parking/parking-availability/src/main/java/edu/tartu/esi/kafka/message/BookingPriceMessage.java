package edu.tartu.esi.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPriceMessage {

    private String requestId;
    private String id;
    private double price;
}
