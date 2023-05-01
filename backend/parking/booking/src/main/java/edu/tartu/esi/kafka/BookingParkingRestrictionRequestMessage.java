package edu.tartu.esi.kafka;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingParkingRestrictionRequestMessage {

    private String requestId;
    private String id;
}
