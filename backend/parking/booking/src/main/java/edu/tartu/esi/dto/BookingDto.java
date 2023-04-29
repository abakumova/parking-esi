package edu.tartu.esi.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    @Id
    private String id;
    private String customerId;
    private String parkingSlotId;
    private String price;
    private LocalDateTime timeFrom;
    private LocalDateTime timeUntil;
}
