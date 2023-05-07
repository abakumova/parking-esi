package edu.tartu.esi;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "analytics")
@Entity
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String parkingSlotId;

    @NotNull
    @Column(nullable = false)
    private double occupancy;

    @NotNull
    @Column(nullable = false)
    private double revenue;

    @NotNull
    @Column(nullable = false)
    private long totalBookingCount;

    @NotNull
    @Column(nullable = false)
    private long totalBookingDuration;
}
