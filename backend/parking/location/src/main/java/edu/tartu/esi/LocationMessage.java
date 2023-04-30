package edu.tartu.esi;

import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationMessage {

    private String formattedAddress;
    private String city;
    private String postalCode;
    private String country;
    private double latitude;
    private double longitude;
}