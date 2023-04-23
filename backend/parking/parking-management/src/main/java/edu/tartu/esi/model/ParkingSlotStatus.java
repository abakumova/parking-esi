package edu.tartu.esi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "parking_slot_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ParkingSlotStatus {

    @Id
    private int id;

    @NotBlank
    @Size(max = 20)
    private String status;
}
