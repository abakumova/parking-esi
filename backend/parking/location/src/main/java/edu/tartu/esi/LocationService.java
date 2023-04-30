package edu.tartu.esi;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationService {

    private final GeoApiContext geoApiContext;
    private final KafkaTemplate<String, LocationMessage> kafkaTemplate;
    @Value("${location.topic.name}")
    private final String kafkaTopic;

    public LocationService(GeoApiContext geoApiContext, KafkaTemplate<String, LocationMessage> kafkaTemplate, String kafkaTopic) {
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
                    LocationMessage locationMessage = new LocationMessage(
                            result.formattedAddress,
                            city,
                            postalCode,
                            country,
                            result.geometry.location.lat,
                            result.geometry.location.lng
                    );
                    log.warn("-- Message {}", locationMessage);
                    log.warn("-- Topic {}", kafkaTopic);
                    kafkaTemplate.send(kafkaTopic, locationMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
