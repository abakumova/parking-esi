package edu.tartu.esi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "parking_slots")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String landlordId;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private SlotStatusEnum status;

    @NotBlank
    @Size(max = 50)
    private String price;

    @Embedded
    private Location location;
}
