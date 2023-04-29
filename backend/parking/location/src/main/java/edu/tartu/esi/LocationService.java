package edu.tartu.esi;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LocationService {


    private final GeoApiContext geoApiContext;
    private final KafkaTemplate<String, LocationEvent> kafkaTemplate;
    private final String kafkaTopic;

    public LocationService(GeoApiContext geoApiContext, KafkaTemplate<String, LocationEvent> kafkaTemplate, String kafkaTopic) {
        this.geoApiContext = geoApiContext;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
    }

    // Implement the method to process the address and publish the location event
    public void processAddress(String address) {
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
                    LocationEvent locationEvent = new LocationEvent(
                            result.formattedAddress,
                            city,
                            postalCode,
                            country,
                            result.geometry.location.lat,
                            result.geometry.location.lng
                    );
                    kafkaTemplate.send(kafkaTopic, locationEvent);
                }
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

}
