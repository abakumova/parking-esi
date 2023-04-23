package edu.tartu.esi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_restriction_times")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String seconds;
    private String year;
    private String month;
    private String day;
    private String hours;
    private String minutes;
}
