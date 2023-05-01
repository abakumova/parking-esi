package edu.tartu.esi.kafka;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingPriceRequestMessage {
    private String requestId;
    private String id;
}
