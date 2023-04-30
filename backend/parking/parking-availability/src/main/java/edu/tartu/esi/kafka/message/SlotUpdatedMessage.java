package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.SlotStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotUpdatedMessage {

    private String requestId;
    private String slotId;
    private SlotStatusEnum status;
}
