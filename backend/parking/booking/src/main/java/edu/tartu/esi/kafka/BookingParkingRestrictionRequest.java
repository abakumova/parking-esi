package edu.tartu.esi.kafka;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingParkingRestrictionRequest {
    private String requestId;
    private String id;
}
