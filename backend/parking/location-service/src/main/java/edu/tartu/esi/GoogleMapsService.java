package edu.tartu.esi;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

    @Autowired
    private GeoApiContext geoApiContext;

    public String getFullAddress(String partialAddress) throws Exception {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, partialAddress).await();

        if (results.length > 0) {
            return results[0].formattedAddress;
        } else {
            throw new Exception("No address found for the given input");
        }
    }
}
