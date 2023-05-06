package edu.tartu.esi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String customerId;

    @NotBlank
    private String parkingSlotId;

    @NotBlank
    @Size(max = 50)
    private String price;

    @NotBlank
    private LocalDateTime timeFrom;

    @NotBlank
    private LocalDateTime timeUntil;

    @NotBlank
    private String landlordId;
}
