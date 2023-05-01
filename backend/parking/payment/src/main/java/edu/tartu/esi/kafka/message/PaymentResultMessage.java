package edu.tartu.esi.kafka.message;

import edu.tartu.esi.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultMessage {

    private String requestId;
    private String bookingId;
    private PaymentStatusEnum status;
}
