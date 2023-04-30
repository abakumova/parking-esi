package edu.tartu.esi.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    private String formattedAddress;
    private String city;
    private String postalCode;
    private String country;
    private double latitude;
    private double longitude;

}