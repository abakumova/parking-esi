package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.CarCategoryEnum;
import edu.tartu.esi.model.Location;
import edu.tartu.esi.model.SlotStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotCreatedMessage {

    private String requestId;
    private String slotId;
    private SlotStatusEnum status;
    private Location location;
    private String price;
    private CarCategoryEnum category;
}
