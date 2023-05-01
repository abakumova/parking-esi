package edu.tartu.esi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_restrictions")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    //@NotNull
    private String parkingSlotId;

    private LocalDateTime timeFrom;
    private LocalDateTime timeUntil;

    @NotNull
    private CarCategoryEnum category;

    @Size(max = 50)
    private String code;
}
