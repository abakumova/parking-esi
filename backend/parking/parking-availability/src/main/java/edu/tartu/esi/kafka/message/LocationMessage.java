package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationMessage {

    private Location location;
}
