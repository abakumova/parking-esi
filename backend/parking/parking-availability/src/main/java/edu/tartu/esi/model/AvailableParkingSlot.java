package edu.tartu.esi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "available_slots")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String slotId;

    @NotNull
    private SlotStatusEnum managementStatus;

    @NotNull
    private SlotStatusEnum bookingStatus;

    @NotNull
    private CarCategoryEnum category;

    @NotNull
    @Embedded
    private Location location;

    @NotNull
    private String price;
}