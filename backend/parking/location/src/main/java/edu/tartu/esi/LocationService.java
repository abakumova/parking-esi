package edu.tartu.esi;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class LocationService {

    private final GeoApiContext geoApiContext;

    // Implement the method to process the address and publish the location event
    public LocationMessage processAddress(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
            if (results.length > 0) {
                GeocodingResult result = results[0];
                String city = null;
                String postalCode = null;
                String country = null;

                for (AddressComponent component : result.addressComponents) {
                    for (AddressComponentType type : component.types) {
                        if (type == AddressComponentType.LOCALITY) {
                            city = component.shortName;
                        } else if (type == AddressComponentType.POSTAL_CODE) {
                            postalCode = component.shortName;
                        } else if (type == AddressComponentType.COUNTRY) {
                            country = component.shortName;
                        }
                    }
                }

                if (city != null && postalCode != null && country != null) {
                    LocationMessage locationMessage = new LocationMessage(
                            result.formattedAddress,
                            city,
                            postalCode,
                            country,
                            result.geometry.location.lat,
                            result.geometry.location.lng
                    );
                    log.warn("-- Message {}", locationMessage);
                    return locationMessage;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
