package edu.tartu.esi;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public String getFullAddress(String partialAddress) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        GeocodingResult[] results = GeocodingApi.geocode(context, partialAddress).await();

        if (results.length > 0) {
            return results[0].formattedAddress;
        } else {
            throw new Exception("No address found for the given input");
        }
    }
}
