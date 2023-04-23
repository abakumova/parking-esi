package edu.tartu.esi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @OneToOne
    @JoinColumn(name = "")
    private Time from;

    @NotNull
    @OneToOne
    @JoinColumn(name = "")
    private Time until;

    @NotNull
    @OneToOne
    @JoinColumn(name = "")
    private CarCategory category;

    @NotBlank
    @Size(max = 50)
    private String code;
}
