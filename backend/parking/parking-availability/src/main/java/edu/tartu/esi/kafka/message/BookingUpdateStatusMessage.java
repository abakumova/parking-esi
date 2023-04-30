package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.SlotStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateStatusMessage {

    private String requestId;
    private String slotId;
    private SlotStatusEnum status;
}
