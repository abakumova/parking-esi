package edu.tartu.esi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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

    private SlotStatusEnum parkingSlotStatus;

    @NotBlank
    @Size(max = 50)
    private String price;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_restrictions_id")
    private List<ParkingRestriction> parkingRestrictions;

    @Embedded
    private Location location;
}
