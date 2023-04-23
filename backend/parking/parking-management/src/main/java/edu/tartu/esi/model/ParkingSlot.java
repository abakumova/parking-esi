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

    private ParkingSlotStatusEnum parkingSlotStatus;

    @NotBlank
    @Size(max = 50)
    private String price;

    @NotBlank
    @OneToMany
    @JoinColumn(name = "")
    private List<ParkingRestriction> parkingRestrictions;



}
